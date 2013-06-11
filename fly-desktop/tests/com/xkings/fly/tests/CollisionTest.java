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
import com.badlogic.gdx.graphics.g3d.model.SubMesh;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.xkings.fly.collision.Collision;
import com.xkings.fly.collision.TriangleCollision;
import com.xkings.fly.entity.BoundingBoxComponent;
import com.xkings.fly.utils.ApplicationAdapter;
import com.xkings.fly.utils.ObjLoader;

public class CollisionTest {

    private StillModel quadBox;
    private StillModel quadSocketBoxHit;
    private StillModel quadSocketBoxMiss;
    private StillModel quadSocket2BoxHit;
    private StillModel quadSocket2BoxMiss;

    private StillModel triSocketBoxHit;
    private StillModel triSocketBoxMiss;
    private StillModel triSocket2BoxHit;
    private StillModel triSocket2BoxMiss;

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
        quadBox = loadObjModel("quadBox");
        quadSocketBoxHit = loadObjModel("quadSocketBoxHit");
        quadSocketBoxMiss = loadObjModel("quadSocketBoxMiss");
        quadSocket2BoxHit = loadObjModel("quadSocket2BoxHit");
        quadSocket2BoxMiss = loadObjModel("quadSocket2BoxMiss");

        triSocketBoxHit = loadObjModel("triSocketBoxHit");
        triSocketBoxMiss = loadObjModel("triSocketBoxMiss");
        triSocket2BoxHit = loadObjModel("triSocket2BoxHit");
        triSocket2BoxMiss = loadObjModel("triSocket2BoxMiss");
    }

    private StillModel loadObjModel(String name) {
        FileHandle file = new FileHandle(
                new File("test-assets/models/" + name + ".obj").getAbsolutePath());
        return new ObjLoader().loadObj(file);
    }

    private boolean intersectTrianglePolygon(StillModel model) {

        SubMesh a = model.getSubMeshes()[0];
        SubMesh b = model.getSubMeshes()[1];

        BoundingBox bb = new BoundingBox();
        a.getBoundingBox(bb);

        return Collision.intersectPolygon(new BoundingBoxComponent(bb, Vector3.Zero), b.mesh);
    }

    @Test
    public void intersectTrianglePolygonSuccess() {
        Assert.assertTrue(intersectTrianglePolygon(triSocketBoxHit));
        Assert.assertTrue(intersectTrianglePolygon(triSocket2BoxHit));
    }

    @Test
    public void intersectTrianglePolygonFail() {
        Assert.assertFalse(intersectTrianglePolygon(triSocketBoxMiss));
        Assert.assertFalse(intersectTrianglePolygon(triSocket2BoxMiss));
    }

    @Test(enabled = false)
    public void intersectQuadPolygonSuccess() {
        Assert.assertTrue(intersectTrianglePolygon(quadSocketBoxHit));
        Assert.assertTrue(intersectTrianglePolygon(quadSocket2BoxHit));
    }

    @Test(enabled = false)
    public void intersectQuadpolygonFail() {
        Assert.assertFalse(intersectTrianglePolygon(quadSocketBoxMiss));
        Assert.assertFalse(intersectTrianglePolygon(quadSocket2BoxMiss));
    }

    @Test
    public void intersectTrianglesFail() {
        Ray ray = new Ray(new Vector3(0, 5, 0), Vector3.Z);
        Assert.assertFalse(TriangleCollision
                .intersectTriangles(ray, quadBox.subMeshes[0].mesh));
    }

    @Test
    public void intersectTrianglesEdgeSuccess() {
        Ray ray = new Ray(new Vector3(-Float.MAX_VALUE, 1, 1), Vector3.X);
        Assert.assertTrue(TriangleCollision.intersectTriangles(ray, quadBox.subMeshes[0].mesh));
    }

    @Test
    public void intersectTrianglesEdgeFail() {
        Ray ray = new Ray(new Vector3(-Float.MAX_VALUE, 1, 1.0001f), Vector3.X);
        Assert.assertFalse(TriangleCollision
                .intersectTriangles(ray, quadBox.subMeshes[0].mesh));
    }

    @Test
    public void intersectTrianglesSuccess() {
        Ray ray = new Ray(new Vector3(0, 0, 0), Vector3.Z);
        Assert.assertTrue(TriangleCollision.intersectTriangles(ray, quadBox.subMeshes[0].mesh));
    }

    @AfterSuite
    public void teardown() {
    }

}
