package com.xkings.fly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class Input extends InputAdapter {

    private final AbstractServer server;

    public Input(AbstractServer server) {
        this.server = server;
    }

    @Override
    public boolean keyDown(int keycode) {
        return action(keycode, 1);
    }

    @Override
    public boolean keyUp(int keycode) {
        return action(keycode, 0);
    }

    private boolean action(int keycode, int dir) {
        server.send(new ClientCommand((short) keycode, System.currentTimeMillis(), dir));
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    private int invertY(int y) {
        return Gdx.graphics.getHeight() - y;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

}
