package com.xkings.fly.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g3d.model.SubMesh;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.xkings.fly.collision.Collision;
import com.xkings.fly.component.Position;
import com.xkings.fly.component.Size;
import com.xkings.fly.entity.BoundingBoxComponent;

public class FlyerCollisionSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Position> positionMapper;
    @Mapper
    ComponentMapper<Size> sizeMapper;
    @Mapper
    ComponentMapper<BoundingBoxComponent> boundingBoxMapper;
    private final StillModel worldModel;

    public FlyerCollisionSystem(StillModel worldModel) {
        super(Aspect.getAspectForAll(Position.class, Size.class));
        this.worldModel = worldModel;
    }

    @Override
    protected void process(Entity e) {
        Vector3 position = positionMapper.get(e).getPoint();
        Vector3 size = sizeMapper.get(e).getPoint();
        BoundingBoxComponent boundingBox = boundingBoxMapper.get(e);

        SubMesh[] meshes = worldModel.getSubMeshes();

        boolean condition = false;
        for (SubMesh mesh : meshes) {
            BoundingBox bb = new BoundingBox();
            mesh.getBoundingBox(bb);
            if (Collision.intersectBoundingBoxes(new BoundingBoxComponent(bb, Vector3.Zero),
                    boundingBox)) {
                if (Collision.intersectPolygon(boundingBox, mesh.getMesh())) {
                    condition = true;
                }
            }
        }
        if (condition) {
            System.exit(1);
        }
    }
}
