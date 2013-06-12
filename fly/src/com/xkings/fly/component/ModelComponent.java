package com.xkings.fly.component;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g3d.model.SubMesh;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.math.Vector3;

public class ModelComponent extends Component {

    private final List<SubMeshComponent> meshes = new ArrayList<SubMeshComponent>();

    public ModelComponent(StillModel model, Vector3 position) {
        for (SubMesh subMesh : model.subMeshes) {
            meshes.add(new SubMeshComponent(subMesh, position));
        }
    }

    public List<SubMeshComponent> getMeshes() {
        return meshes;
    }

}
