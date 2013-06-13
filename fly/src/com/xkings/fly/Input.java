package com.xkings.fly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.xkings.fly.logic.Updateable;
import com.xkings.fly.server.ClientCommand;

public class Input extends InputAdapter implements Updateable {

    public static final int MOUSE_MOVE_X = 256;
    public static final int MOUSE_MOVE_Y = 257;

    public static final int AZIMUTH = 258;
    public static final int PITCH = 259;
    public static final int ROLL = 260;

    public static final int COEFICIENT = 1000;

    private final AbstractServer server;

    public Input(AbstractServer server) {
        this.server = server;
    }

    @Override
    public boolean keyDown(int keycode) {
        return send(keycode, 1);
    }

    @Override
    public boolean keyUp(int keycode) {
        return send(keycode, 0);
    }

    private boolean send(int action, int value) {
        server.send(new ClientCommand((short) action, System.currentTimeMillis(), value));
        return true;
    }

    /*private int invertY(int y) {
        return Gdx.graphics.getHeight() - y;
    }*/

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        send(MOUSE_MOVE_X, screenX);
        send(MOUSE_MOVE_Y, screenY);
        return false;
    }

    @Override
    public void update(float delta) {
        updateOrientation();
    }

    private float oldAzimuth;
    private float oldPitch;
    private float oldRoll;

    private void updateOrientation() {
        float newAzimuth = Gdx.input.getAzimuth();
        if (oldAzimuth != newAzimuth) {
            send(AZIMUTH, (int) (newAzimuth * COEFICIENT));
            oldAzimuth = newAzimuth;
        }

        float newPitch = Gdx.input.getPitch();
        if (oldPitch != newPitch) {
            send(PITCH, (int) (newPitch * COEFICIENT));
            oldPitch = newPitch;
        }

        float newRoll = Gdx.input.getRoll();
        if (oldRoll != newRoll) {
            send(ROLL, (int) (newRoll * COEFICIENT));
            oldRoll = newRoll;
        }

    }

}
