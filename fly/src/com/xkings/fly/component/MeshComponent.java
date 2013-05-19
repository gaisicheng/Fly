package com.xkings.fly.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Mesh;

public class MeshComponent extends Component {

    private final Mesh mesh;

    public MeshComponent(Mesh mesh) {
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

}
