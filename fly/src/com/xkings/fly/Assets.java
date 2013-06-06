package com.xkings.fly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

public final class Assets {

    private final static StillModel rocks;
    private final static StillModel terrain;
    private final static StillModel flyer;
    private static StillModel ten;

    static {
        rocks = loadModel("land.obj");
        terrain = loadModel("terrain.obj");
        flyer = loadModel("flyer.obj");
        ten = loadModel("10x10.obj");

        // flyer.scale(App.FLYER_SIZE, App.FLYER_SIZE, App.FLYER_SIZE);
    }

    private static StillModel loadModel(String name) {
        FileHandle file = Gdx.files.internal("data/models/" + name);
        return ModelLoaderRegistry.loadStillModel(file);
        //  fixUVs(mesh, Assets.getTexture(name, 0));
        //  move(mesh, 0, App.getTerrain().getHeight(), 0);
    }

    public static StillModel getTerrain() {
        return rocks;
    }

    public static StillModel getFlyer() {
        return flyer;
    }

}
