package com.xkings.fly.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.model.SubMesh;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.xkings.fly.collision.Collision;
import com.xkings.fly.entity.BoundingBoxComponent;
import com.xkings.fly.utils.ObjLoader;

public class CollisionTest {

    private SubMesh box;
    private SubMesh socket;
    private SubMesh box2;
    private SubMesh socket2;

    @BeforeTest
    public void beforeTest() {
        FileHandle file = new FileHandle(
                new File("test-assets/boxInSocket.obj").getAbsolutePath());
        box = new ObjLoader().loadObj(file).getSubMeshes()[0];
        socket = new ObjLoader().loadObj(file).getSubMeshes()[1];

        file = new FileHandle(new File("test-assets/boxInSocketHit.obj").getAbsolutePath());
        box2 = new ObjLoader().loadObj(file).getSubMeshes()[0];
        socket2 = new ObjLoader().loadObj(file).getSubMeshes()[1];
    }

    @Test
    public void intersectPolygonSuccess() {
        BoundingBox bb = new BoundingBox();
        box2.getBoundingBox(bb);
        BoundingBoxComponent bbc = new BoundingBoxComponent(bb, Vector3.Zero);
        Assert.assertTrue(Collision.intersectPolygon(bbc, socket2.mesh));
    }

    @Test
    public void intersectPolygonFail() {
        BoundingBox bb = new BoundingBox();
        box.getBoundingBox(bb);
        BoundingBoxComponent bbc = new BoundingBoxComponent(bb, Vector3.Zero);
        Assert.assertFalse(Collision.intersectPolygon(bbc, socket.mesh));
    }

}
