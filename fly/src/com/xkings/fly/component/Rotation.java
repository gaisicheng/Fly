package com.xkings.fly.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

public class Rotation extends Component {

    private final Vector3 point;

    public Rotation(float x, float y, float z) {
        this.point = new Vector3(x, y, z);
    }

    public Vector3 getPoint() {
        return point;
    }

}
