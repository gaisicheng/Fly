package com.xkings.fly.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderComponent extends Component {

    private final ShaderProgram shader;
    private final Color color;

    public ShaderComponent(ShaderProgram shader, Color color) {
        this.shader = shader;
        this.color = color;
    }

    public ShaderProgram getShader() {
        return shader;
    }

    public Color getColor() {
        return color;
    }

}