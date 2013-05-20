package com.xkings.fly;

import java.util.Random;

import com.artemis.World;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.xkings.fly.GameStateManager.GameStatus;
import com.xkings.fly.systems.FlyCameraSystem;
import com.xkings.fly.systems.FlySystem;
import com.xkings.fly.systems.RenderGeometrySystem;

public class App implements ApplicationListener {
    private static Camera camera;
    private static Camera cornerCamera;
    private static Camera currentCamera;
    public static final float WORLD_SIZE = 10f;
    public static final float FLYER_SIZE = 0.5f;

    // Global parameters
    public static Param DEBUG;
    public static Param NOSLEEP;
    public static Param REPLAY;

    public static Random rand;
    private World world;
    private final ParamHolder params;
    private GameStateManager state;
    private Clock clock;
    private static Flyer flyer;
    private Terrain terrain;
    private BitmapFont font;
    private SpriteBatch onScreenRasterRender;

    // Systems
    private RenderGeometrySystem renderGeometry;
    private FlyCameraSystem flyCamera;
    private FlySystem fly;
    private OfflineServer server;

    public App(String[] args) {
        params = new ParamHolder(args);
        DEBUG = params.getParam("-d", "-debug");
        NOSLEEP = params.getParam("-ns", "-nosleep");
        REPLAY = params.getParam("-r", "-replay");

    }

    @Override
    public void create() {
        font = new BitmapFont();
        onScreenRasterRender = new SpriteBatch();
        ShaderProgram.pedantic = false;
        state = REPLAY != null ? GameStateManager.getInstance(GameStatus.REPLAY) : GameStateManager.getInstance(GameStatus.RECORD);
        rand = new Random(state.getSeed());

        clock = new Clock("Logic", NOSLEEP == null);
        world = new World();
        server = new OfflineServer(this, state);
        clock.addService(server);

        Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL10.GL_LESS);

    }

    private void registerSystems() {
        renderGeometry = world.setSystem(new RenderGeometrySystem(camera), true);
        flyCamera = world.setSystem(new FlyCameraSystem(), true);

        fly = world.setSystem(new FlySystem(), true);
    }

    @Override
    public void dispose() {
    }

    float angle = 0.1f;
    private boolean init = true;

    @Override
    public void render() {
        if (init) {
            registerSystems();
            world.initialize();

            flyer = new Flyer(world, camera, -WORLD_SIZE / 2f, 1, 0);
            terrain = new Terrain(world);

            Gdx.input.setInputProcessor(new Input(server));
            init = false;
        }
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
                | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        cornerCamera.position.set(-WORLD_SIZE, WORLD_SIZE, 0);
        cornerCamera.direction.set(1, -1, 0);
        cornerCamera.update();

        fly.process();
        flyCamera.process();
        renderGeometry.process();

        if (DEBUG != null) {
            onScreenRasterRender.begin();
            font.draw(onScreenRasterRender, "GPU:" + String.valueOf(Gdx.graphics.getFramesPerSecond()), 30, Gdx.graphics.getHeight() - 90);
            font.draw(onScreenRasterRender, "CPU:" + String.valueOf(Math.round(clock.getFPS())), 30, Gdx.graphics.getHeight() - 120);
            font.draw(onScreenRasterRender, "Clocks:" + String.valueOf(clock.getClocks()), 30, Gdx.graphics.getHeight() - 180);
            onScreenRasterRender.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        if (init) {
            camera = new PerspectiveCamera(67, width, height);
            cornerCamera = new PerspectiveCamera(67, width, height);
            currentCamera = cornerCamera;
        }
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.near = 0.01f;

        cornerCamera.viewportWidth = width;
        cornerCamera.viewportHeight = height;
        cornerCamera.position.set(-2, 2, -2);
        cornerCamera.lookAt(0, 0, 0);
        cornerCamera.update();
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

    public static void toggleCamera() {
        currentCamera = currentCamera == cornerCamera ? camera : cornerCamera;
    }

    public static Camera getCamera() {
        return currentCamera;
    }

    public static Flyer getFlyer() {
        return flyer;
    }

}
