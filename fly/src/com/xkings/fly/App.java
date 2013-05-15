package com.xkings.fly;

import java.util.Random;

import com.artemis.World;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xkings.fly.GameStateManager.GameStatus;

public class App implements ApplicationListener {
    private Camera camera;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;

    // Global parameters
    public static Param DEBUG;
    public static Param NOSLEEP;
    public static Param REPLAY;

    public static Random rand;
    private World world;
    private final ParamHolder params;
    private GameStateManager state;
    private Clock clock;

    public App(String[] args) {
        params = new ParamHolder(args);
        DEBUG = params.getParam("-d", "-debug");
        NOSLEEP = params.getParam("-ns", "-nosleep");
        REPLAY = params.getParam("-r", "-replay");

    }

    @Override
    public void create() {
        state = REPLAY != null ? GameStateManager.getInstance(GameStatus.REPLAY) : GameStateManager.getInstance(GameStatus.RECORD);
        rand = new Random(state.getSeed());

        clock = new Clock("Logic", NOSLEEP == null);
        world = new World();
        OfflineServer server = new OfflineServer(this, state);
        Gdx.input.setInputProcessor(new Input(server));

        clock.addService(server);

        Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL10.GL_LESS);
    }

    @Override
    public void dispose() {
    }

    private float ang = 0;

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
                | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        ang += 0.01f;
        int radius = 2;
        camera.position.set((float) (Math.cos(ang) * radius), radius, (float) (Math.sin(ang) * radius));
        camera.lookAt(0, 0, 0);
        camera.update();
        Shader.getShader("sprite").begin();
        Shader.getShader("sprite").setUniformMatrix("u_projTrans", camera.combined);
        Shader.getShader("sprite").setUniformf("u_lightPos", camera.position);
        Assets.getTerrain().render(Shader.getShader("sprite"), GL10.GL_TRIANGLES);
        Shader.getShader("sprite").end();
    }

    @Override
    public void resize(int width, int height) {
        if (camera == null) {
            camera = new PerspectiveCamera(67, width, height);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public World getWorld() {
        return world;
    }

    public Clock getClock() {
        return clock;
    }

}
