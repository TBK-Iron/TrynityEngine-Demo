package no.uib.inf101.sem2.gameEngine.view.raycaster;

import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.text.Position;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position2D;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Vector;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.ViewProjectionMatrix;

public class Camera implements ICamera {
    GridPosition pos;
    RelativeRotation rotation;
    float focalLength;
    float verticalFOV;
    float horizontalFOV;
    float coordinateHeight;
    float aspectRatio;
    float renderDistance;
    ViewProjectionMatrix viewProjectionMatrix;
    Frustum cameraFrustum;

    public Camera(int screenWidth, int screenHeight, float nearPlane, float farPlane, float verticalFOV){
        updatePos(new Position3D(0, 0, 0));
        updateRotation(new RelativeRotation(0, 0));
        this.aspectRatio = screenWidth/screenHeight;
        this.renderDistance = farPlane;
        this.focalLength = nearPlane;
        this.verticalFOV = verticalFOV;
        this.coordinateHeight = 2 * ((float) Math.tan(verticalFOV/2) * nearPlane);
        this.horizontalFOV = (float) Math.atan((this.coordinateHeight * this.aspectRatio) / (2 * nearPlane));

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
        this.viewProjectionMatrix = new ViewProjectionMatrix(this.verticalFOV, this.aspectRatio, this.focalLength, this.renderDistance, this.rotation.getAbsolute());
        this.cameraFrustum = new Frustum(this.viewProjectionMatrix.getViewProjectionMatrix());
    }

    @Override
    public ViewProjectionMatrix getViewProjectionMatrix(){
        return this.viewProjectionMatrix;
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

