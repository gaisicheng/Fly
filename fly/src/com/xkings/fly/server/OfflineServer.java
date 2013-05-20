package com.xkings.fly.server;

import com.artemis.World;
import com.badlogic.gdx.Input.Keys;
import com.xkings.fly.AbstractServer;
import com.xkings.fly.App;
import com.xkings.fly.Input;

public class OfflineServer extends AbstractServer {

    private final World world;

    public OfflineServer(App app) {
        super(app);
        this.world = app.getWorld();
        //world.setSystem(new FlySystem());

    }

    @Override
    public void process(ClientCommand c) {
        processInput(c);
    }

    @Override
    public void updateInternal(float delta) {

        world.setDelta(delta);
        world.process();

        // App.getTweenManager().update(delta);
    }

    private void processInput(ClientCommand c) {
        if (c.getAction() == Keys.W) {
            if (c.getValue() == 1) {
                App.toggleCamera();
            }
        } else if (c.getAction() == Input.MOUSE_MOVE_X) {
            App.getFlyer().getOffset().setOffsetFromMouseX(c.getValue());
        } else if (c.getAction() == Input.MOUSE_MOVE_Y) {
            App.getFlyer().getOffset().setOffsetFromMouseY(c.getValue());
        }

    }

}
