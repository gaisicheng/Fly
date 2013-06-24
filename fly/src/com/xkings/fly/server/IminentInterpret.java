package com.xkings.fly.server;

import com.badlogic.gdx.math.Vector3;

public class IminentInterpret extends MobileInputInterpret {

    public IminentInterpret() {
        super(60);
    }

    @Override
    protected void processMoveVector(Vector3 vector) {
        calculateScreenCoordinates(vector.y, vector.z);
    }

}
