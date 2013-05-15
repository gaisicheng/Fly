package com.xkings.fly;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "fly";
        cfg.useGL20 = true;
        cfg.width = 480;
        cfg.height = 320;

        new LwjglApplication(new App(args), cfg);
    }
}
