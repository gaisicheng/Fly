package com.xkings.fly.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.xkings.fly.collision.Collision;
import com.xkings.fly.component.Position;
import com.xkings.fly.component.Size;
import com.xkings.fly.component.SubMeshComponent;
import com.xkings.fly.entity.BoundingBoxComponent;
import com.xkings.fly.entity.Terrain;

public class FlyerCollisionSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Position> positionMapper;
    @Mapper
    ComponentMapper<Size> sizeMapper;
    @Mapper
    ComponentMapper<BoundingBoxComponent> boundingBoxMapper;
    private final Terrain terrain;

    public FlyerCollisionSystem(Terrain terrain) {
        super(Aspect.getAspectForAll(Position.class, Size.class));
        this.terrain = terrain;
    }

    @Override
    protected void process(Entity e) {
        BoundingBoxComponent boundingBox = boundingBoxMapper.get(e);

        boolean condition = false;
        for (SubMeshComponent mesh : terrain.getMesh().getMeshes()) {
            if (Collision.intersectBoundingBoxes(mesh.getBoundingBoxComponent(), boundingBox)) {
                if (Collision.intersectPolygon(boundingBox, mesh.getSubMesh().getMesh())) {
                    condition = true;
                }
            }
        }
        if (condition) {
            //System.exit(1);
        }
    }
}
