package com.xkings.fly;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Camera;

public class FollowCamera extends Component {

    private final Camera camera;
    private final float distance;

    public FollowCamera(Camera camera, float distance) {
        this.camera = camera;
        this.distance = distance;
    }

    public Camera getCamera() {
        return camera;
    }

    public float getDistance() {
        return distance;
    }
}
