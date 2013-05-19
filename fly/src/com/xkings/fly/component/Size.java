package com.xkings.fly.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

public class Size extends Component {

    private final Vector3 dimensions;

    public Size(float x, float y, float z) {
        this.dimensions = new Vector3(x, y, z);
    }

    public Vector3 getPoint() {
        return dimensions;
    }

}
