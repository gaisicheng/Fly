package com.xkings.fly.component;

import com.artemis.Component;

public class Speed extends Component {

    private final float current;

    public Speed(float speed) {
        this.current = speed;
    }

    public float getCurrent() {
        return current;
    }

}
