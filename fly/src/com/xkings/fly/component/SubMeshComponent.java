package com.xkings.fly.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.model.SubMesh;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.xkings.fly.entity.BoundingBoxComponent;

public class SubMeshComponent extends Component {

    private final SubMesh subMesh;
    private final BoundingBoxComponent boundingBoxComponent;

    public SubMeshComponent(SubMesh subMesh, Vector3 position) {
        this.subMesh = subMesh;
        BoundingBox boundingBox = new BoundingBox();
        subMesh.getBoundingBox(boundingBox);
        this.boundingBoxComponent = new BoundingBoxComponent(boundingBox, position);
    }

    public SubMesh getSubMesh() {
        return subMesh;
    }

    public Mesh getMesh() {
        return subMesh.mesh;
    }

    public BoundingBoxComponent getBoundingBoxComponent() {
        return boundingBoxComponent;
    }

}
