package com.xkings.fly;

import java.util.ArrayList;

import com.xkings.fly.logic.Updateable;
import com.xkings.fly.server.ClientCommand;

public abstract class AbstractServer implements Updateable {
    protected App app;
    private final ArrayList<ClientCommand> buffer = new ArrayList<ClientCommand>();

    public AbstractServer(App app) {
        this.app = app;
    }

    public synchronized void send(ClientCommand c) {
        buffer.add(c);
    }

    @Override
    public synchronized void update(float delta) {
        for (ClientCommand c : buffer) {
            process(c);
        }
        buffer.clear();
        updateInternal(delta);
    }

    public abstract void process(ClientCommand c);

    public abstract void updateInternal(float delta);

}
