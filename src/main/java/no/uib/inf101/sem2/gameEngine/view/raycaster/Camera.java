package no.uib.inf101.sem2.gameEngine.view.raycaster;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.text.Position;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;

public class Camera implements ICamera {
    GridPosition centerPos;
    GridPosition pos;
    Rotation absoluteRot;
    RelativeRotation relativeRot;
    double aspectRatio;
    double focalLength;
    double verticalFOV;
    double horizontalFOV;
    Double[] cosVals;
    Double[] sinVals;
    ViewportCorners cornerRotations;

    public Camera(int width, int height, double fov, GridPosition pos, RelativeRotation rot){
        updatePos(pos);
        updateRotation(rot);
        this.aspectRatio = (double) width/height;
        this.focalLength = height/(2*Math.tan(fov));
        this.verticalFOV = fov;
        this.horizontalFOV = Math.atan(width / (2 * focalLength));
        this.pos = calcCenterToPos();
        this.cornerRotations = calcCornerRotations();
    }

    private GridPosition calcCenterToPos(){
        GridPosition negFacingVector = new GridPosition(
            -cosVals[2] - sinVals[1],
            -cosVals[0] - sinVals[2],
            -cosVals[1] - sinVals[0]
        );
        Double scalor = this.focalLength / (Math.sqrt(Math.pow(negFacingVector.x(), 2) + 
        Math.pow(negFacingVector.y(), 2) + Math.pow(negFacingVector.z(), 2)));

        double x = scalor * negFacingVector.x() + this.centerPos.x();
        double y = scalor * negFacingVector.y() + this.centerPos.y();
        double z = scalor * negFacingVector.z() + this.centerPos.z();

        return new GridPosition(x, y, z);

    }

    private ViewportCorners calcCornerRotations(){
        RelativeRotation upperLeft = relativeRot.add(new RelativeRotation(verticalFOV/2, -horizontalFOV/2));
        RelativeRotation lowerRight = relativeRot.add(new RelativeRotation(-verticalFOV/2, horizontalFOV/2));

        return new ViewportCorners(upperLeft, lowerRight);
    
    }

    @Override
    public void updatePos(GridPosition pos){
        this.centerPos = pos;
    }

    @Override
    public void updateRotation(RelativeRotation rotation){
        this.relativeRot = rotation;
        this.absoluteRot = rotation.getAbsolute();
        cosVals = new Double[] {Math.cos(this.absoluteRot.getxAxis()), Math.cos(this.absoluteRot.getyAxis()), Math.cos(this.absoluteRot.getzAxis())};
        sinVals = new Double[] {Math.sin(this.absoluteRot.getxAxis()), Math.sin(this.absoluteRot.getyAxis()), Math.sin(this.absoluteRot.getzAxis())};

    }

    @Override
    public boolean isRendered(GridPosition vertex){
        double dx = vertex.x() - this.pos.x();
        double dy = vertex.y() - this.pos.y();
        double dz = vertex.z() - this.pos.z();
        
        RelativeRotation vectorRot = getVectorRotation(new double[] {dx, dy, dz});

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

        return true;
    }

    public static RelativeRotation getVectorRotation(double[] vector){
        vector = normalize(vector);
        double upDown = Math.asin(vector[1]);
        double leftRight = Math.atan2(vector[2], vector[0]) + Math.PI;

        return new RelativeRotation(upDown, leftRight);
    }

    private static double[] normalize(double[] vector){
        double vectorMagnitude = magnitude(vector);
        double[] normalizedVector = {vector[0] / vectorMagnitude, vector[1] / vectorMagnitude, vector[2] / vectorMagnitude};
        return normalizedVector;
    }

    private static double magnitude(double[] vector){
        return Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2) + Math.pow(vector[2], 2));
    }

    @Override
    public GridPosition getCastPos(){
        return this.pos;
    }

    @Override
    public ViewportCorners getCornerRotations(){
        return this.cornerRotations;
    }
}
