package com.xkings.fly.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

public class ModelComponent extends Component {

    private final StillModel model;

    public ModelComponent(StillModel model) {
        this.model = model;
    }

    public StillModel getModel() {
        return model;
    }

}
