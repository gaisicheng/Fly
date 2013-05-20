package com.xkings.fly;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.xkings.fly.component.MeshComponent;
import com.xkings.fly.component.ShaderComponent;

public class Terrain extends ConcreteEntity {

    private final MeshComponent mesh;
    private final ShaderComponent shader;

    public Terrain(World world) {
        super(world);

        mesh = new MeshComponent(Assets.getTerrain());
        shader = new ShaderComponent(Shader.getShader("terrain"), new Color(0, 0.4f, 0.2f, 1));

        bag.add(mesh);
        bag.add(shader);

        register();
    }

    public MeshComponent getMesh() {
        return mesh;
    }

    public ShaderComponent getShader() {
        return shader;
    }

}
