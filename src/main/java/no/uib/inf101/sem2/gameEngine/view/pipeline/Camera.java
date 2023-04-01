package no.uib.inf101.sem2.gameEngine.view.pipeline;

import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Projection;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Transformation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.View;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.ViewProjection;

public class Camera implements ICamera {
    GridPosition pos;
    RelativeRotation rotation;
    float focalLength;
    float verticalFOV;
    float horizontalFOV;
    float coordinateHeight;
    float aspectRatio;
    float renderDistance;
    Transformation viewTransformation;
    Transformation projectionTransformation;
    Transformation viewProjectionTransformation;
    Frustum cameraFrustum;

    public Camera(int screenWidth, int screenHeight, float nearPlane, float farPlane, float verticalFOV){
        this.aspectRatio = screenWidth/screenHeight;
        this.renderDistance = farPlane;
        this.focalLength = nearPlane;
        this.verticalFOV = verticalFOV;
        this.coordinateHeight = 2 * ((float) Math.tan(verticalFOV/2) * nearPlane);
        this.horizontalFOV = (float) Math.atan((this.coordinateHeight * this.aspectRatio) / (2 * nearPlane));
        this.projectionTransformation = new Projection(verticalFOV, verticalFOV, nearPlane, farPlane);

        updatePos(new Position3D(0, 0, 0));
        updateRotation(new RelativeRotation(0, 0));

        System.out.println("focal:" + this.focalLength);
        System.out.println(this.pos);
        System.out.println(this.rotation);
    }


    @Override
    public void updatePos(GridPosition pos){
        this.pos = pos;
    }

    @Override
    public void updateRotation(RelativeRotation rotation){
        this.rotation = rotation;
        this.viewTransformation = new View(rotation, pos);
        this.viewProjectionTransformation = new ViewProjection(this.viewTransformation.getMatrix(), this.projectionTransformation.getMatrix());
        this.cameraFrustum = new Frustum(this.viewProjectionTransformation.getMatrix());
    }

    @Override
    public Transformation getViewProjectionTransform(){
        return this.viewProjectionTransformation;
    }

    @Override
    public Transformation getViewTranform(){
        return this.viewTransformation;
    }

    @Override
    public Transformation getProjectionTransform(){
        return this.projectionTransformation;
    }

    @Override
    public GridPosition getCastPos(){
        return this.pos;
    }


    @Override
    public RelativeRotation getRotation() {
        return this.rotation;
    }

    @Override
    public float getFocalLength(){
        return this.focalLength;
    }

    @Override
    public float getHeight(){
        return this.coordinateHeight;
    }

    @Override
    public float getWidth(){
        return this.coordinateHeight*this.aspectRatio;
    }

    @Override
    public Frustum getFrustum(){
        return this.cameraFrustum;
    }
}

