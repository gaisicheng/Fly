package com.xkings.fly;

import java.util.Random;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.artemis.World;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.xkings.fly.entity.Flyer;
import com.xkings.fly.entity.Terrain;
import com.xkings.fly.logic.Clock;
import com.xkings.fly.server.OfflineServer;
import com.xkings.fly.system.FlyerCameraSystem;
import com.xkings.fly.system.FlyerCollisionSystem;
import com.xkings.fly.system.FlyerMovementSystem;
import com.xkings.fly.system.RenderGeometrySystem;
import com.xkings.fly.tween.Vector3Accessor;
import com.xkings.fly.utils.Param;
import com.xkings.fly.utils.ParamHolder;

public class App implements ApplicationListener {
    private static Camera camera;
    private static Camera cornerCamera;
    private static Camera currentCamera;

    public static final float WORLD_SIZE = 10f;
    public static final float FLYER_SIZE = 0.5f;
    public static final Color BACKGROUND = new Color(1f, 1f, 1f, 1f);

    // Global parameters
    private final ParamHolder params;
    public static Param DEBUG;
    public static Param NOSLEEP;
    public static Param REPLAY;

    private boolean initialize = true;
    private SystemInfo systemInfo;
    private SpriteBatch onScreenRasterRender;
    public static Random random = new Random();
    private World world;
    private Clock clock;

    private static Flyer flyer;
    private Terrain terrain;

    private static TweenManager tweenManager = new TweenManager();
    static {
        Tween.registerAccessor(Vector3.class, new Vector3Accessor());
    }

    // Systems
    private RenderGeometrySystem renderGeometry;
    private FlyerCameraSystem flyCamera;
    private FlyerMovementSystem flyMovement;
    private OfflineServer server;

    public App(String[] args) {
        params = new ParamHolder(args);
        DEBUG = params.getParam("-d", "-debug");
        NOSLEEP = params.getParam("-ns", "-nosleep");
        REPLAY = params.getParam("-r", "-replay");
    }

    @Override
    public void create() {
        ShaderProgram.pedantic = false;

        clock = new Clock("Logic", NOSLEEP == null);
        world = new World();
        server = new OfflineServer(this);
        clock.addService(server);

        Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL10.GL_LESS);

    }

    private void registerSystems() {
        renderGeometry = world.setSystem(new RenderGeometrySystem(camera), true);
        flyCamera = world.setSystem(new FlyerCameraSystem(), true);
        flyMovement = world.setSystem(new FlyerMovementSystem(), true);

        server.addSystem(new FlyerCollisionSystem(terrain));
    }

    @Override
    public void dispose() {
    }

    float ang = 0f;
    private StillModel worldModel;

    @Override
    public void render() {
        Gdx.gl.glClearColor(BACKGROUND.r, BACKGROUND.g, BACKGROUND.b, BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT
                | GL20.GL_DEPTH_BUFFER_BIT
                | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV
                        : 0));

        ang += 0.01f;
        int offset = 10;
        float x = (float) (Math.cos(ang) * offset);
        float y = (float) (Math.sin(ang) * offset);

        cornerCamera.position.set(x, offset, y);
        cornerCamera.lookAt(0, 0, 0);
        cornerCamera.update();

        flyMovement.process();
        flyCamera.process();
        renderGeometry.process();

        if (DEBUG != null) {
            if (systemInfo == null) {
                systemInfo = new SystemInfo();
            }
            systemInfo.addInfo("GPU: " + String.valueOf(Gdx.graphics.getFramesPerSecond()));
            systemInfo.addInfo(clock.getName() + ": "
                    + String.valueOf(Math.round(clock.getFPS())));
            systemInfo.addInfo("Clocks: " + String.valueOf(clock.getClocks()));
            systemInfo.addInfo("Camera.x: " + currentCamera.position.x);
            systemInfo.addInfo("Camera.y: " + currentCamera.position.y);
            systemInfo.addInfo("Camera.z: " + currentCamera.position.z);
            systemInfo.render(onScreenRasterRender);
        }
    }

    private void initialize(int width, int height) {
        camera = new PerspectiveCamera(50, width, height);
        cornerCamera = new PerspectiveCamera(67, width, height);

        currentCamera = camera;
        onScreenRasterRender = new SpriteBatch();

        worldModel = Assets.getTerrain();
        flyer = new Flyer(world, camera, -WORLD_SIZE / 2f, 1, 0);
        terrain = new Terrain(world, worldModel);

        registerSystems();
        world.initialize();

        flyer.register();
        terrain.register();

        Gdx.input.setInputProcessor(new Input(server));
        initialize = false;
    }

    @Override
    public void resize(int width, int height) {
        if (initialize) {
            initialize(width, height);
        }

        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.near = 0.01f;

        cornerCamera.viewportWidth = width;
        cornerCamera.viewportHeight = height;
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

    public static TweenManager getTweenManager() {
        return tweenManager;
    }

}
