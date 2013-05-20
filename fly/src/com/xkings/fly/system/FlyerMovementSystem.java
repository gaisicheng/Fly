package com.xkings.fly.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.xkings.fly.component.FollowCamera;
import com.xkings.fly.component.Move;
import com.xkings.fly.component.OffsetPosition;
import com.xkings.fly.component.PathPosition;
import com.xkings.fly.component.Position;
import com.xkings.fly.component.Rotation;
import com.xkings.fly.component.Size;
import com.xkings.fly.component.Speed;

public class FlyerMovementSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Position> positionMapper;
    @Mapper
    ComponentMapper<OffsetPosition> positionOffsetMapper;
    @Mapper
    ComponentMapper<PathPosition> pathPositionMapper;
    @Mapper
    ComponentMapper<Rotation> rotationMapper;
    @Mapper
    ComponentMapper<Size> sizeMapper;
    @Mapper
    ComponentMapper<Move> moveMapper;
    @Mapper
    ComponentMapper<Speed> speedMapper;
    @Mapper
    ComponentMapper<FollowCamera> followCameraMapper;

    public FlyerMovementSystem() {
        super(Aspect.getAspectForAll(Position.class, Size.class, Move.class, Speed.class));
    }

    private final float angle = 0.8f;

    @Override
    protected void process(Entity e) {
        Vector3 move = moveMapper.get(e).getPoint();
        Vector3 rotation = rotationMapper.get(e).getPoint();
        Vector3 pathPosition = pathPositionMapper.get(e).getPoint();
        Vector3 position = positionMapper.get(e).getPoint();

        Vector3 step = new Vector3();
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            step.y = angle;
        }

        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            step.y = -angle;
        }

        rotation.y += step.y;
        move.rotate(Vector3.Y, step.y);

        Speed speed = speedMapper.get(e);

        pathPosition.add(move.cpy().scl(speed.getCurrent()));

        Vector2 offset = positionOffsetMapper.get(e).getPoint();
        position.set(pathPosition).add(0, offset.y, offset.x);

    }
}
