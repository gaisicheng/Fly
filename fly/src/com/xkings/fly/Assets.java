package com.xkings.fly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;

public final class Assets {

    private final static Mesh terrain;
    private final static Mesh flyer;

    static {
        terrain = loadModel("terrain.obj");
        flyer = loadModel("flyer.obj");
        flyer.scale(App.FLYER_SIZE, App.FLYER_SIZE, App.FLYER_SIZE);
    }

    private static Mesh loadModel(String name) {
        FileHandle file = Gdx.files.internal("data/models/" + name);
        return ModelLoaderRegistry.loadStillModel(file).getSubMeshes()[0].mesh;
        //  fixUVs(mesh, Assets.getTexture(name, 0));
        //  move(mesh, 0, App.getTerrain().getHeight(), 0);
    }

    public static Mesh getTerrain() {
        return terrain;
    }

    public static Mesh getFlyer() {
        return flyer;
    }

}
