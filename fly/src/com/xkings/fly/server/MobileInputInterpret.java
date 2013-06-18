package com.xkings.fly.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.xkings.fly.App;
import com.xkings.fly.Input;

public class MobileInputInterpret implements InputInterpret {

    private final int RANGE = 10;
    private final Vector3 min = new Vector3(-RANGE, -RANGE, -RANGE);
    private final Vector3 max = new Vector3(RANGE, RANGE, RANGE);
    private final Vector3 current = new Vector3();
    private float azimuth;
    private float pitch;
    private float roll;

    @Override
    public void processInput(ClientCommand c) {
        if (c.getAction() == Input.AZIMUTH) {
            azimuth = (float) c.getValue() / Input.COEFICIENT * -1f;
            current.x = azimuth;
        }

        if (c.getAction() == Input.PITCH) {

            pitch = (float) c.getValue() / Input.COEFICIENT * -1f;
            current.y = pitch;
        }

        if (c.getAction() == Input.ROLL) {
            roll = (float) c.getValue() / Input.COEFICIENT * -1f;
            current.z = roll;
        }

        calculate();
    }

    private void calculate() {
        current.x = MathUtils.clamp(azimuth, min.x, max.x) / RANGE;
        current.y = MathUtils.clamp(pitch, min.y, max.y) / RANGE;
        current.z = MathUtils.clamp(roll, min.z, max.z) / RANGE;
        if (Gdx.graphics != null && App.getFlyer() != null) {
            App.getFlyer().getScreenCoordinates()
                    .setX((int) ((Gdx.graphics.getWidth() * (current.y + 1)) / 2f));
            App.getFlyer().getScreenCoordinates()
                    .setY((int) ((Gdx.graphics.getHeight() * (current.z + 1)) / 2f));
        }
    }
}
