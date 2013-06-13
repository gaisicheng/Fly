package com.xkings.fly.server;

import com.xkings.fly.Input;

public class MobileInputInterpret implements InputInterpret {

    @Override
    public void processInput(ClientCommand c) {
        if (c.getAction() == Input.AZIMUTH) {
            float azimuth = (float) c.getValue() / Input.COEFICIENT;
            System.out.println("Azimuth: " + azimuth);
        }
    }

}
