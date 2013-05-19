package com.xkings.fly.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

public class Move extends Component {

    private final Vector3 direction;

    public Move(float x, float y, float z) {
        this(new Vector3(x, y, z));
    }

    public Move(Vector3 direction) {
        this.direction = direction;
    }

    public Vector3 getPoint() {
        return direction;
    }

}
