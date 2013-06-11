package com.xkings.fly.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.model.SubMesh;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.xkings.fly.collision.TriangleCollision;
import com.xkings.fly.utils.ApplicationAdapter;
import com.xkings.fly.utils.ObjLoader;

public class TriangleCollisionTest {

    private Mesh mesh;
    private SubMesh box;
    private SubMesh socket;

    @BeforeSuite
    public void bootstrap() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Tests";
        cfg.useGL20 = true;
        cfg.width = 200;
        cfg.height = 200;

        new LwjglApplication(new ApplicationAdapter(), cfg);
    }

    @BeforeTest
    public void beforeTest() {
        FileHandle file = new FileHandle(new File("test-assets/box.obj").getAbsolutePath());
        mesh = new ObjLoader().loadObj(file).getSubMeshes()[0].mesh;

        file = new FileHandle(new File("test-assets/boxInSocketHit.obj").getAbsolutePath());
        box = new ObjLoader().loadObj(file).getSubMeshes()[0];
        socket = new ObjLoader().loadObj(file).getSubMeshes()[1];
    }

    @Test
    public void intersectTrianglesFail() {
        Ray ray = new Ray(new Vector3(0, 5, 0), Vector3.Z);
        Assert.assertFalse(TriangleCollision.intersectTriangles(ray, mesh));
    }

    @Test
    public void intersectTrianglesEdgeSuccess() {
        Ray ray = new Ray(new Vector3(-Float.MAX_VALUE, 1, 1), Vector3.X);
        Assert.assertTrue(TriangleCollision.intersectTriangles(ray, mesh));
    }

    @Test
    public void intersectTrianglesEdgeFail() {
        Ray ray = new Ray(new Vector3(-Float.MAX_VALUE, 1, 1.0001f), Vector3.X);
        Assert.assertFalse(TriangleCollision.intersectTriangles(ray, mesh));
    }

    @Test
    public void intersectTrianglesSuccess() {
        Ray ray = new Ray(new Vector3(0, 0, 0), Vector3.Z);
        Assert.assertTrue(TriangleCollision.intersectTriangles(ray, mesh));
    }

    @Test
    public void intersectTrianglesSocketSuccess() {
        Ray ray = new Ray(new Vector3(0, 0, 0), Vector3.Z);
        Assert.assertTrue(TriangleCollision.intersectTriangles(ray, socket.mesh));
    }

    @AfterSuite
    public void teardown() {
    }

}
