package com.xkings.fly.entity;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class BoundingBoxComponent extends Component {

    private final BoundingBox boundingBox;
    private final Vector3 position;

    public BoundingBoxComponent(BoundingBox boundingBox, Vector3 position) {
        this.boundingBox = boundingBox;
        this.position = position;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public Vector3 getPosition() {
        return position;
    }

}
