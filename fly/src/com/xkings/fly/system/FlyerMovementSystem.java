package com.xkings.fly.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Intersector;
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

        position.set(screenToWorld(camera, screenCoordinates));
    }

    /**
     * Transforms screen coordinates to world position.
     * 
     * @param camera
     *            which world is projected from
     * @param screenCoordinates
     *            viewport coordinates
     * @return position in the world
     */
    private Vector3 screenToWorld(FollowCamera camera, Vector2 screenCoordinates) {
        Vector3 planeNormal = camera.getCamera().direction.cpy();
        Plane plane = new Plane(planeNormal, -camera.getCamera().position.x - camera.getDistance());
        Ray pickRay = App.getCamera().getPickRay(screenCoordinates.x, screenCoordinates.y);
        Vector3 worldPosition = new Vector3();
        Intersector.intersectRayPlane(pickRay, plane, worldPosition);
        return worldPosition;
    }
}
