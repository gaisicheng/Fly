package com.xkings.fly;

import java.util.List;

import com.artemis.World;
import com.xkings.fly.GameStateManager.GameStatus;

public class OfflineServer extends AbstractServer {

    private final GameStateManager gsm;
    private final World world;

    public OfflineServer(App app, GameStateManager gsm) {
        super(app);
        this.world = app.getWorld();
        this.gsm = gsm;
        //  world.setSystem(new MovementSystem());
    }

    @Override
    public void process(ClientCommand c) {
        if (gsm.getStatus() == GameStatus.RECORD) {
            processInput(c);

            // replace timestamp with number of clocks game did to this point.
            ClockCommand clockCommand = new ClockCommand(c, app.getClock().getClocks());
            gsm.register(clockCommand);
        }
    }

    @Override
    public void updateInternal(float delta) {
        if (gsm.getStatus() == GameStatus.REPLAY) {
            List<ClientCommand> commands = gsm.getCommands(app.getClock().getClocks());
            for (ClientCommand command : commands) {
                processInput(command);
            }
        }

        world.setDelta(delta);
        world.process();

        // App.getTweenManager().update(delta);
    }

    private void processInput(ClientCommand c) {

    }

}
