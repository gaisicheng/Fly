package com.xkings.fly.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.xkings.fly.App;
import com.xkings.fly.component.FollowCamera;
import com.xkings.fly.component.Move;
import com.xkings.fly.component.PathPosition;
import com.xkings.fly.component.Position;
import com.xkings.fly.component.Rotation;
import com.xkings.fly.component.ScreenCoordinates;
import com.xkings.fly.component.Size;
import com.xkings.fly.component.Speed;

public class FlyerMovementSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Position> positionMapper;
    @Mapper
    ComponentMapper<ScreenCoordinates> screenCoordinatesMapper;
    @Mapper
    ComponentMapper<PathPosition> pathPositionMapper;
    @Mapper
    ComponentMapper<Rotation> rotationMapper;
    @Mapper
    ComponentMapper<Move> moveMapper;
    @Mapper
    ComponentMapper<Speed> speedMapper;
    @Mapper
    ComponentMapper<FollowCamera> followCameraMapper;

    public FlyerMovementSystem() {
        super(Aspect.getAspectForAll(Position.class, Size.class, Move.class, Speed.class));
    }

    @Override
    protected void process(Entity e) {
        Vector3 move = moveMapper.get(e).getPoint();
        Vector3 pathPosition = pathPositionMapper.get(e).getPoint();
        Vector3 position = positionMapper.get(e).getPoint();

        FollowCamera camera = followCameraMapper.get(e);
        Speed speed = speedMapper.get(e);
        pathPosition.add(move.cpy().scl(speed.getCurrent()));

        Vector2 screenCoordinates = screenCoordinatesMapper.get(e).getPoint().cpy();

        position.set(screenToWorld(camera, screenCoordinates, -pathPosition.x));
    }

    /**
     * Transforms screen coordinates to world position.
     * 
     * @param camera
     *            which world is projected from
     * @param screenCoordinates
     *            viewport coordinates
     * @param distance
     *            distance of projection plane
     * @return position in the world
     */
    private Vector3 screenToWorld(FollowCamera camera, Vector2 screenCoordinates,
            float distance) {
        screenCoordinates = clampScreenCoordinates(screenCoordinates, 0.8f);
        Vector3 planeNormal = camera.getCamera().direction.cpy();
        Plane plane = new Plane(planeNormal, distance);
        Ray pickRay = App.getCamera().getPickRay(screenCoordinates.x, screenCoordinates.y);

        Vector3 worldPosition = new Vector3();
        Intersector.intersectRayPlane(pickRay, plane, worldPosition);
        return worldPosition;
    }

    /**
     * Clamps screen coordinates into frame with given ratio
     * 
     * @param screenCoordinates
     *            screen coordinated from mouse input
     * @param ratio
     *            size of clamped frame, from 0 to 1
     * @return clamped screen coordinates
     */
    private Vector2 clampScreenCoordinates(Vector2 screenCoordinates, float ratio) {
        if (ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException(
                    "Ratio must be greater than 0 and lesser than 1.");
        }

        float xOffset = Gdx.graphics.getWidth() * ((1 - ratio) / 2f);
        float yOffset = Gdx.graphics.getHeight() * ((1 - ratio) / 2f);

        Vector2 min = new Vector2(xOffset, yOffset);
        Vector2 max = new Vector2(Gdx.graphics.getWidth() - xOffset, Gdx.graphics.getHeight()
                - yOffset);

        return new Vector2(MathUtils.clamp(screenCoordinates.x, min.x, max.x),
                MathUtils.clamp(screenCoordinates.y, min.y, max.y));
    }
}
