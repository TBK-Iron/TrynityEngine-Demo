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
    double focalLength;
    double verticalFOV;
    double horizontalFOV;
    double coordinateHeight;
    double aspectRatio;
    double renderDistance;
    ViewProjectionMatrix viewProjectionMatrix;
    Frustum cameraFrustum;

    public Camera(double coordinateWidth, double coordinateHeight, double renderDistance, double fov, GridPosition pos, RelativeRotation rot){
        updatePos(pos);
        updateRotation(rot);
        System.out.println( " " + fov + " " + Math.tan(fov/2));
        this.aspectRatio = coordinateWidth/coordinateHeight;
        this.renderDistance = renderDistance;
        this.coordinateHeight = coordinateHeight;
        this.focalLength = this.coordinateHeight/(2*Math.tan(fov/2));
        this.verticalFOV = fov;
        this.horizontalFOV = Math.atan((this.aspectRatio) / (2 * focalLength));
        this.pos = pos;
        this.viewProjectionMatrix = new ViewProjectionMatrix(this.verticalFOV, this.aspectRatio, this.focalLength, this.renderDistance, this.rotation.getAbsolute());
        this.cameraFrustum = new Frustum(this.viewProjectionMatrix.getViewProjectionMatrix());


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

    //TODO: add support for rendering faces where all points are outside the viewport, but part of the face is still visible.
    @Override
    public boolean isRendered(Vector rotatedRay, GridPosition castedPos){
        if(rotatedRay.get(2) > this.focalLength){
            return true;
        } else if(castedPos.y() > -this.coordinateHeight/2 && castedPos.y() < this.coordinateHeight/2){
            System.out.println("test1");
            return false;
        } else if(castedPos.x() > -this.coordinateHeight*this.aspectRatio/2 && castedPos.x() < this.coordinateHeight*this.aspectRatio/2){
            System.out.println("test3");
            return false;
        } else {
            return true;
        }
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
    public double getFocalLength(){
        return this.focalLength;
    }

    @Override
    public double getHeight(){
        return this.coordinateHeight;
    }

    @Override
    public double getWidth(){
        return this.coordinateHeight*this.aspectRatio;
    }

    @Override
    public Frustum getFrustum(){
        return this.cameraFrustum;
    }
}

