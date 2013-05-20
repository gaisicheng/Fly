package com.xkings.fly.component;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class OffsetPosition extends Component {

    private final Vector2 point;
    private final static float multiplier = 1f;

    public OffsetPosition() {
        this.point = new Vector2(0, 0);
    }

    public Vector2 getPoint() {
        return point;
    }

    public void setOffsetFromMouseX(int value) {
        float width = Gdx.graphics.getWidth();
        point.x = (value - width / 2f) / width * multiplier;
    }

    public void setOffsetFromMouseY(int value) {
        float height = Gdx.graphics.getHeight();
        point.y = (height / 2f - value) / height * multiplier;
    }

}
