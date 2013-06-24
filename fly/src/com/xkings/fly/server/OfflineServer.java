package com.xkings.fly.server;

import com.artemis.EntitySystem;
import com.artemis.World;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.xkings.fly.AbstractServer;
import com.xkings.fly.App;

public class OfflineServer extends AbstractServer {

    private final World world;
    private final InputInterpret inputInterpret;

    public OfflineServer(App app) {
        super(app);
        this.world = app.getWorld();
        this.inputInterpret = Gdx.app.getType() == ApplicationType.Desktop ? new DesktopInputInterpret()
                : new ProgressiveInterpret();
    }

    public void addSystem(EntitySystem system) {
        world.setSystem(system);
    }

    @Override
    public void process(ClientCommand c) {
        inputInterpret.processInput(c);
    }

    @Override
    public void updateInternal(float delta) {

        world.setDelta(delta);
        world.process();

        App.getTweenManager().update(delta);
    }

}
