package com.xkings.fly.server;

import java.util.ArrayList;

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
    private final SmoothBuffer buffer;

    MobileInputInterpret(int bufferSize) {
        buffer = new SmoothBuffer(bufferSize > 0 ? bufferSize : 1);
    }

    public MobileInputInterpret() {
        this(1);
    }

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

        buffer.put(current);

        Vector3 average = buffer.get();

        if (Gdx.graphics != null && App.getFlyer() != null) {
            App.getFlyer().getScreenCoordinates()
                    .setX((int) ((Gdx.graphics.getWidth() * (average.y + 1)) / 2f));
            App.getFlyer().getScreenCoordinates()
                    .setY((int) ((Gdx.graphics.getHeight() * (average.z + 1)) / 2f));
        }
    }

    private static class SmoothBuffer {
        private final ArrayList<Vector3> buffer;
        private int index = 0;
        private final Vector3 current = new Vector3();

        SmoothBuffer(int bufferSize) {
            buffer = new ArrayList<Vector3>(bufferSize);
            for (int i = 0; i < bufferSize; i++) {
                buffer.add(new Vector3());
            }
        }

        public void put(Vector3 vector) {
            buffer.get(index++ % buffer.size()).set(vector);
            calculate(buffer);
        }

        private void calculate(ArrayList<Vector3> buffer) {
            current.set(Vector3.Zero);
            for (Vector3 vector : buffer) {
                current.add(vector);
            }

            current.scl((float) 1 / buffer.size());

        }

        public Vector3 get() {
            return current;
        }
    }
}
