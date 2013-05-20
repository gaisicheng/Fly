package com.xkings.fly;

import com.artemis.World;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.xkings.fly.component.MeshComponent;
import com.xkings.fly.component.Move;
import com.xkings.fly.component.OffsetPosition;
import com.xkings.fly.component.PathPosition;
import com.xkings.fly.component.Position;
import com.xkings.fly.component.Rotation;
import com.xkings.fly.component.ShaderComponent;
import com.xkings.fly.component.Size;
import com.xkings.fly.component.Speed;

public class Flyer extends ConcreteEntity {

    private final Position position;
    private final PathPosition pathPosition;
    private final OffsetPosition offset;
    private final Rotation rotaion;
    private final Size size;
    private final Move move;
    private final Speed speed;
    private final MeshComponent mesh;
    private final ShaderComponent shader;
    private final FollowCamera followCamera;

    public Flyer(World world, Camera camera, float x, float y, float z) {
        super(world);

        position = new Position();
        pathPosition = new PathPosition(x, y, z);
        offset = new OffsetPosition();
        rotaion = new Rotation(0, 0f, 0);
        size = new Size(1, 1, 1);
        move = new Move(1, 0, 0);
        move.getPoint().y = 0;
        speed = new Speed(0.005f);
        mesh = new MeshComponent(Assets.getFlyer());
        shader = new ShaderComponent(Shader.getShader("normal"), new Color(0, 0, 1, 1));
        followCamera = new FollowCamera(camera, 1f);

        bag.add(position);
        bag.add(pathPosition);
        bag.add(offset);
        bag.add(rotaion);
        bag.add(size);
        bag.add(move);
        bag.add(speed);
        bag.add(mesh);
        bag.add(shader);
        bag.add(followCamera);

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

    public MeshComponent getMesh() {
        return mesh;
    }

    public ShaderComponent getShader() {
        return shader;
    }

    public OffsetPosition getOffset() {
        return offset;
    }

}
