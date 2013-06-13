package com.xkings.fly.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.xkings.fly.App;
import com.xkings.fly.component.ModelComponent;
import com.xkings.fly.component.Position;
import com.xkings.fly.component.Rotation;
import com.xkings.fly.component.ShaderComponent;
import com.xkings.fly.component.SubMeshComponent;

public class RenderGeometrySystem extends EntityProcessingSystem {
    private static final int SIGHT = 15;
    @Mapper
    ComponentMapper<ModelComponent> modelMapper;
    @Mapper
    ComponentMapper<ShaderComponent> shaderMapper;
    @Mapper
    ComponentMapper<Position> positionMapper;
    @Mapper
    ComponentMapper<Rotation> rotationMapper;

    public RenderGeometrySystem(Camera light) {
        super(Aspect.getAspectForAll(ModelComponent.class, ShaderComponent.class));
    }

    @Override
    protected void process(Entity e) {

        ShaderProgram shader = shaderMapper.get(e).getShader();

        Camera camera = App.getCamera();
        Vector3 flyerPosition = App.getFlyer().getPosition().getPoint();

        Vector3 position = null;
        if (positionMapper.has(e)) {
            position = positionMapper.get(e).getPoint().cpy();
        }

        Vector3 rotation = null;
        if (rotationMapper.has(e)) {
            rotation = rotationMapper.get(e).getPoint().cpy();
        }

        adjustCameraPosition(camera, position, -1f);
        //  adjustCameraRotation(camera, rotation, -1f);
        camera.update();

        shader.begin();
        shader.setUniformMatrix("u_MVPMatrix", camera.combined);
        shader.setUniformMatrix("u_MVMatrix", camera.view);
        shader.setUniformf("u_lightPos", flyerPosition);
        shader.setUniformf("u_lightPos", flyerPosition);
        shader.setUniformf("sight", SIGHT);
        shader.setUniformf("backgroundColor", App.BACKGROUND);

        for (SubMeshComponent submesh : modelMapper.get(e).getMeshes()) {
            Material material = submesh.getSubMesh().material;
            for (int i = 0; i < material.getNumberOfAttributes(); i++) {
                material.getAttribute(i).bind(shader);
            }

            Vector3 meshPosition = submesh.getBoundingBoxComponent().getCenter();

            if (flyerPosition.x <= meshPosition.x && flyerPosition.x - SIGHT < meshPosition.x) {
                submesh.getMesh().render(shader, submesh.getSubMesh().primitiveType);
            }
        }
        shader.end();

        //  adjustCameraRotation(camera, rotation, 1f);
        adjustCameraPosition(camera, position, 1f);
        //   camera.update();
    }

    private void adjustCameraPosition(Camera camera, Vector3 position, float k) {
        if (position != null) {
            camera.translate(position.x * k, position.y * k, position.z * k);
        }
    }

    private void adjustCameraRotation(Camera camera, Vector3 rotation, float k) {
        if (rotation != null) {
            // camera.rotate(Vector3.X, rotation.x * k);
            camera.rotateAround(Vector3.Zero, Vector3.X, rotation.x * k);
            camera.rotateAround(Vector3.Zero, Vector3.Y, rotation.y * k);
            camera.rotateAround(Vector3.Zero, Vector3.Z, rotation.z * k);
            // camera.rotate(Vector3.Z, rotation.z * k);
        }
    }
}
