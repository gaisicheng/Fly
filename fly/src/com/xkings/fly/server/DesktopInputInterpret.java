package com.xkings.fly.server;

import com.badlogic.gdx.Input.Keys;
import com.xkings.fly.App;
import com.xkings.fly.Input;

public class DesktopInputInterpret implements InputInterpret {

    @Override
    public void processInput(ClientCommand c) {
        if (c.getAction() == Keys.W) {
            if (c.getValue() == 1) {
                App.toggleCamera();
            }
        } else if (c.getAction() == Input.MOUSE_MOVE_X) {
            App.getFlyer().getScreenCoordinates().setX(c.getValue());
        } else if (c.getAction() == Input.MOUSE_MOVE_Y) {
            App.getFlyer().getScreenCoordinates().setY(c.getValue());
        }
    }

}
