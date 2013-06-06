package com.xkings.fly.entity;

import com.artemis.World;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.xkings.fly.Assets;
import com.xkings.fly.component.FollowCamera;
import com.xkings.fly.component.ModelComponent;
import com.xkings.fly.component.Move;
import com.xkings.fly.component.PathPosition;
import com.xkings.fly.component.Position;
import com.xkings.fly.component.Rotation;
import com.xkings.fly.component.ScreenCoordinates;
import com.xkings.fly.component.ShaderComponent;
import com.xkings.fly.component.Size;
import com.xkings.fly.component.Speed;
import com.xkings.fly.graphics.Shader;

public class Flyer extends ConcreteEntity {

    private final Position position;
    private final PathPosition pathPosition;
    private final ScreenCoordinates offset;
    private final Rotation rotaion;
    private final Size size;
    private final Move move;
    private final Speed speed;
    private final ModelComponent model;
    private final ShaderComponent shader;
    private final FollowCamera followCamera;
    private final BoundingBoxComponent boundingBox;

    public Flyer(World world, Camera camera, float x, float y, float z) {
        super(world);

        position = new Position();
        pathPosition = new PathPosition(x, y, z);
        offset = new ScreenCoordinates();
        rotaion = new Rotation(0, 0f, 0);
        size = new Size(1, 1, 1);
        move = new Move(1, 0, 0);
        move.getPoint().y = 0;
        speed = new Speed(0.05f);
        model = new ModelComponent(Assets.getFlyer());

        BoundingBox bb = new BoundingBox();
        model.getModel().getBoundingBox(bb);

        boundingBox = new BoundingBoxComponent(bb, position.getPoint());
        shader = new ShaderComponent(Shader.getShader("normal"), new Color(0, 0, 1, 1));
        followCamera = new FollowCamera(camera, 3f);

        bag.add(position);
        bag.add(pathPosition);
        bag.add(offset);
        bag.add(rotaion);
        bag.add(size);
        bag.add(move);
        bag.add(speed);
        bag.add(model);
        bag.add(shader);
        bag.add(followCamera);
        bag.add(boundingBox);

        register();
    }

    public Position getPosition() {
        return position;
    }

    public PathPosition getPathPosition() {
        return pathPosition;
    }

    public Size getSize() {
        return size;
    }

    public Move getMove() {
        return move;
    }

    public Speed getSpeed() {
        return speed;
    }

    public ModelComponent getModel() {
        return model;
    }

    public ShaderComponent getShader() {
        return shader;
    }

    public ScreenCoordinates getScreenCoordinates() {
        return offset;
    }

}
