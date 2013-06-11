package com.xkings.fly.collision;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.xkings.fly.entity.BoundingBoxComponent;

public class Collision {

    public static boolean intersectBoundingBoxes(BoundingBoxComponent a, BoundingBoxComponent b) {
        return intersectBoundingBox(a, b) || intersectBoundingBox(b, a);
    }

    private static boolean intersectBoundingBox(BoundingBoxComponent a, BoundingBoxComponent b) {
        Vector3[] corners = a.getBoundingBox().getCorners();
        Vector3 movedCorner = new Vector3();
        for (Vector3 corner : corners) {
            movedCorner.set(corner).add(a.getPosition());
            if (contains(b, movedCorner))
                return true;
        }
        return false;
    }

    private static boolean contains(BoundingBoxComponent bbc, Vector3 v) {
        BoundingBox bb = bbc.getBoundingBox();
        Vector3 p = bbc.getPosition();
        Vector3 min = bb.min.cpy().add(p);
        Vector3 max = bb.max.cpy().add(p);
        return inBetween(min, max, p);
    }

    /**
     * Checks if {@code p} is in a cube delimited by minimum
     * {@code min} and maximum {@code max}
     * 
     * @param min
     *            point min
     * @param max
     *            point max
     * @param p
     *            point p
     * @return {@code true} if point {@code p} is between
     *         {@code min} and {@code max}, {@code false}
     *         otherwise
     */
    public static boolean inBetween(Vector3 min, Vector3 max, Vector3 p) {
        // @formatter:off
        return min.x <= p.x &&
               max.x >= p.x &&
               min.y <= p.y &&
               max.y >= p.y &&
               min.z <= p.z &&
               max.z >= p.z;
        // @formatter:on
    }

    public static boolean intersectPolygon(BoundingBoxComponent boundingBox, Mesh mesh) {
        Vector3 center = boundingBox.getBoundingBox().getCenter().cpy()
                .add(boundingBox.getPosition());
        Ray xRay = new Ray(center, Vector3.X);
        Ray yRay = new Ray(center, Vector3.Y);
        Ray zRay = new Ray(center, Vector3.Z);

        return intersectPolygon(xRay, mesh, boundingBox)
                || intersectPolygon(yRay, mesh, boundingBox)
                || intersectPolygon(zRay, mesh, boundingBox);
    }

    public static boolean intersectPolygon(Ray ray, Mesh mesh, BoundingBoxComponent bbc) {
        BoundingBox bb = bbc.getBoundingBox();
        Vector3 p = bbc.getPosition();
        Vector3 min = bb.min.cpy().add(p);
        Vector3 max = bb.max.cpy().add(p);
        System.out.println(min + " " + max);
        return TriangleCollision.intersectTriangles(ray, mesh, min, max);
    }
}
