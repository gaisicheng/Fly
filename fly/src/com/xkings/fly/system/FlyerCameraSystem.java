package com.xkings.fly.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector3;
import com.xkings.fly.component.FollowCamera;
import com.xkings.fly.component.Move;
import com.xkings.fly.component.PathPosition;
import com.xkings.fly.component.Position;

public class FlyerCameraSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<PathPosition> pathPositionMapper;
    @Mapper
    ComponentMapper<Move> moveMapper;
    @Mapper
    ComponentMapper<FollowCamera> followCameraMapper;

    public FlyerCameraSystem() {
        super(Aspect.getAspectForAll(Position.class, Move.class, FollowCamera.class));
    }

    @Override
    protected void process(Entity e) {
        Vector3 position = pathPositionMapper.get(e).getPoint();
        Vector3 move = moveMapper.get(e).getPoint();

        FollowCamera followCamera = followCameraMapper.get(e);
        followCamera.getCamera().direction.set(move);

        float distance = followCamera.getDistance();
        Vector3 oppositeDirection = followCamera.getCamera().direction.cpy().scl(-distance);

        followCamera.getCamera().position.set(position).add(oppositeDirection);

    }
}
