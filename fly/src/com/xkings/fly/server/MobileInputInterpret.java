package com.xkings.fly.server;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.xkings.fly.App;
import com.xkings.fly.Input;

public class MobileInputInterpret implements InputInterpret {

    private final float RANGE = 30;
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
        current.x = clamp(current.x, azimuth, min.x, max.x);
        current.y = clamp(current.y, pitch, min.y, max.y);
        current.z = clamp(current.z, roll, min.z, max.z);

        buffer.put(current);
        System.out.println(current);

        Vector3 average = buffer.get(0.005f);

        if (Gdx.graphics != null && App.getFlyer() != null) {
            App.getFlyer().getScreenCoordinates()
                    .setX((int) ((Gdx.graphics.getWidth() * (average.y + 1)) / 2f));
            App.getFlyer().getScreenCoordinates()
                    .setY((int) ((Gdx.graphics.getHeight() * (average.z + 1)) / 2f));
        }
    }

    private float clamp(float last, float current, float min, float max) {

        current = MathUtils.clamp(current, min, max) / RANGE;
        //if (Math.abs(last - current) > 0.4f) {
        return current;
        //  } else {
        //      return last;
        //   }
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

        public Vector3 get(float granuality) {
            Vector3 granualVector = current.cpy();
            granualVector.x = (int) (granualVector.x / granuality) * granuality;
            granualVector.y = (int) (granualVector.y / granuality) * granuality;
            granualVector.z = (int) (granualVector.z / granuality) * granuality;
            return granualVector;
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
