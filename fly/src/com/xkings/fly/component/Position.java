package com.xkings.fly.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

public class Position extends Component {

    private final Vector3 point;

    public Position(float x, float y, float z) {
        this.point = new Vector3(x, y, z);
    }

    public Position() {
        this(0, 0, 0);
    }

    public Vector3 getPoint() {
        return point;
    }

}
