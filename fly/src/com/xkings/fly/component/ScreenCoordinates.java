package com.xkings.fly.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class ScreenCoordinates extends Component {

    private final Vector2 point;

    public ScreenCoordinates() {
        this.point = new Vector2(0, 0);
    }

    public Vector2 getPoint() {
        return point;
    }

    public void setX(int value) {
        point.x = value;
    }

    public void setY(int value) {
        point.y = value;
    }

}
