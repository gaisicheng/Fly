package com.xkings.fly.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ProgressiveInterpret extends MobileInputInterpret {

    private Vector2 vector2D;
    private final Vector2 speed;

    public ProgressiveInterpret() {
        super(60);
        speed = new Vector2();
    }

    private static float friction = 0.95f;

    @Override
    protected void processMoveVector(Vector3 vector) {

        vector2D = new Vector2(vector.y, vector.z).scl(0.2f);
        speed.add(vector2D);

        speed.scl(friction);

        calculateScreenCoordinates(speed.x, speed.y);

    }
}
