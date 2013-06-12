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
import com.xkings.fly.component.ScreenCoordinates;

public class FlyerCameraSystem extends EntityProcessingSystem {

    private static final float CAMERA_OFFSET = 0.5f;
    @Mapper
    ComponentMapper<Position> positionMapper;
    @Mapper
    ComponentMapper<PathPosition> pathPositionMapper;
    @Mapper
    ComponentMapper<ScreenCoordinates> positionOffsetMapper;
    @Mapper
    ComponentMapper<Move> moveMapper;
    @Mapper
    ComponentMapper<FollowCamera> followCameraMapper;

    public FlyerCameraSystem() {
        super(Aspect.getAspectForAll(Position.class, Move.class, FollowCamera.class));
    }

    @Override
    protected void process(Entity e) {
        Vector3 flyerPosition = positionMapper.get(e).getPoint();
        Vector3 pathPoint = pathPositionMapper.get(e).getPoint();
        FollowCamera followCamera = followCameraMapper.get(e);
        Vector3 cameraPosition = followCamera.getCamera().position;

        Vector3 direction = followCamera.getCamera().direction;
        direction.set(moveMapper.get(e).getPoint());

        Vector3 cameraOffset = flyerPosition.cpy().sub(pathPoint).scl(CAMERA_OFFSET);

        float distance = followCamera.getDistance();
        Vector3 oppositeVector = direction.cpy().scl(-distance);
        cameraPosition.set(pathPoint).add(oppositeVector).add(cameraOffset);
    }
}
