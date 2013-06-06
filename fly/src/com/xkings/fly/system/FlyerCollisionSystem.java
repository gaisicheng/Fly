package com.xkings.fly.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g3d.model.SubMesh;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.xkings.fly.App;
import com.xkings.fly.component.FollowCamera;
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
        for (SubMesh mesh : meshes) {
            BoundingBox bb = new BoundingBox();
            mesh.getBoundingBox(bb);

            if (intersectBoundingBoxes(new BoundingBoxComponent(bb, Vector3.Zero), boundingBox)) {

            }
        }
    }

    private boolean intersectBoundingBoxes(BoundingBoxComponent a, BoundingBoxComponent b) {
        return intersectBoundingBox(a, b) || intersectBoundingBox(b, a);
    }

    private boolean intersectBoundingBox(BoundingBoxComponent a, BoundingBoxComponent b) {
        Vector3[] corners = a.getBoundingBox().getCorners();
        Vector3 movedCorner = new Vector3();
        for (Vector3 corner : corners) {
            movedCorner.set(corner).add(a.getPosition());
            if (b.getBoundingBox().contains(movedCorner))
                return true;
        }
        return false;
    }

    /**
     * Returns whether the given vector is contained in given bounding box.
     * 
     * @param boundingBox
     *            The bounding box
     * @param v
     *            The vector
     * @return Whether the vector is contained or not.
     */
    private boolean contains(Vector3 v) {
        return min.x <= v.x && max.x >= v.x && min.y <= v.y && max.y >= v.y && min.z <= v.z && max.z >= v.z;
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
