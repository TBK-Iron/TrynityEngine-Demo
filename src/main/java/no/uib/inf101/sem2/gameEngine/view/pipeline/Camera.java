package no.uib.inf101.sem2.gameEngine.view.pipeline;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Projection;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Transformation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.View;

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
    Frustum cameraFrustum;

    public Camera(int screenWidth, int screenHeight, float nearPlane, float farPlane, float verticalFOV){
        this.aspectRatio = (float) screenWidth/ (float)screenHeight;
        this.renderDistance = farPlane;
        this.focalLength = nearPlane;
        this.verticalFOV = verticalFOV;
        this.coordinateHeight = 2 * ((float) Math.tan(verticalFOV/2) * nearPlane);
        this.horizontalFOV = (float) Math.atan((this.coordinateHeight * this.aspectRatio) / (2 * nearPlane));

        /* System.out.println("coordinateHeight:" + this.coordinateHeight/2);
        System.out.println("coordinateWidth:" + this.coordinateHeight*this.aspectRatio/2);
        System.out.println("aspectRatio:" + this.aspectRatio);
        System.out.println("height:" + screenHeight);
        System.out.println("width:" + screenWidth); */

        this.projectionTransformation = new Projection(verticalFOV, aspectRatio, nearPlane, farPlane);
        this.cameraFrustum = new Frustum(this.projectionTransformation.getMatrix(), nearPlane, farPlane);

        updatePose(new Position3D(0, 0, 0), new RelativeRotation(0, 0));

        /* System.out.println("focal:" + this.focalLength);
        System.out.println(this.pos);
        System.out.println(this.rotation); */
    }

    @Override
    public void updatePose(GridPosition pos, RelativeRotation rotation){
        this.pos = pos;
        this.rotation = rotation;
        this.viewTransformation = new View(this.rotation, this.pos);
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

