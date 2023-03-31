package no.uib.inf101.sem2.gameEngine.view.raycaster;

import java.util.ArrayList;

import javax.swing.text.Position;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Vector;

public class Camera implements ICamera {
    GridPosition centerPos;
    GridPosition pos;
    RelativeRotation rotation;
    double focalLength;
    double verticalFOV;
    double horizontalFOV;
    double coordinateHeight = 0.3;

    public Camera(double width, double height, double fov, GridPosition pos, RelativeRotation rot){
        updatePos(pos);
        updateRotation(rot);
        System.out.println(height/width + " " + fov + " " + Math.tan(fov/2));
        this.focalLength = 1/(2*Math.tan(fov/2));
        this.verticalFOV = fov;
        this.horizontalFOV = Math.atan((width/height) / (2 * focalLength));
        this.pos = calcCenterToPos();

        System.out.println(this.pos);
        System.out.println(this.rotation);
    }

    private GridPosition calcCenterToPos(){
        double rotX = rotation.getAbsolute().getxAxis();
        double rotY = rotation.getAbsolute().getyAxis();
        double rotZ = rotation.getAbsolute().getzAxis();


        Matrix rotationMatrix = Matrix.getRotationMatrix(new Rotation(rotX, rotY, rotZ));
        System.out.println(rotationMatrix);
        Vector rotatedVector = rotationMatrix.multiply(new Vector(new double[] {0, 0, -1}));
        System.out.println(rotatedVector);
        Vector scaledVector = rotatedVector.scaledBy(this.focalLength);

        return scaledVector.getPoint();

    }


    @Override
    public void updatePos(GridPosition pos){
        this.centerPos = pos;
    }

    @Override
    public void updateRotation(RelativeRotation rotation){
        this.rotation = rotation;
    }

    //TODO: add support for rendering faces where all points are outside the viewport, but part of the face is still visible.
    @Override
    public boolean isRendered(GridPosition vertex){
        return true;
        /*double dx = vertex.x() - this.pos.x();
        double dy = vertex.y() - this.pos.y();
        double dz = vertex.z() - this.pos.z();

        Vector pointToCamera = new Vector(new double[] {dx, dy, dz});
        
        RelativeRotation vectorRot = Vector.getVectorRotation(pointToCamera);
        System.out.println(vertex + " " + vectorRot);
        if(vectorRot.getUpDown() > this.cornerRotations.upperLeft().getUpDown()){
            return false;
        } else if(vectorRot.getUpDown() < this.cornerRotations.lowerRight().getUpDown()){
            return false;
        //Check if rotation crosses 0 radian spot
        } else if(this.cornerRotations.upperLeft().getLeftRight() > this.cornerRotations.lowerRight().getLeftRight()){
            if(vectorRot.getLeftRight() > this.cornerRotations.upperLeft().getLeftRight()){
                return true;
            } else if(vectorRot.getLeftRight() < this.cornerRotations.lowerRight().getLeftRight()){
                return true;
            } else {
                return false;
            }
        } else {
            if(vectorRot.getLeftRight() < this.cornerRotations.upperLeft().getLeftRight()){
                return false;
            } else if(vectorRot.getLeftRight() > this.cornerRotations.lowerRight().getLeftRight()){
                return false;
            }
        }

        return true;*/
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
}

