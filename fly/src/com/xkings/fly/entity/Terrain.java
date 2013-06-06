package com.xkings.fly.entity;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.xkings.fly.component.ModelComponent;
import com.xkings.fly.component.ShaderComponent;
import com.xkings.fly.graphics.Shader;

public class Terrain extends ConcreteEntity {

    private final ModelComponent model;
    private final ShaderComponent shader;

    public Terrain(World world, StillModel stillModel) {
        super(world);

        model = new ModelComponent(stillModel);
        shader = new ShaderComponent(Shader.getShader("light"), new Color(0, 0.4f, 0.2f, 1));

        bag.add(model);
        bag.add(shader);

        register();
    }

    public ModelComponent getMesh() {
        return model;
    }

    public ShaderComponent getShader() {
        return shader;
    }

}
