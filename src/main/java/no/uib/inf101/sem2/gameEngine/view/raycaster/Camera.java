package no.uib.inf101.sem2.gameEngine.view.raycaster;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.text.Position;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;

public class Camera implements ICamera {
    GridPosition centerPos;
    GridPosition pos;
    Rotation absoluteRot;
    RelativeRotation relativeRot;
    double focalLength;
    double verticalFOV;
    double horizontalFOV;
    ViewportCorners cornerRotations;

    public Camera(double width, double height, double fov, GridPosition pos, RelativeRotation rot){
        updatePos(pos);
        updateRotation(rot);
        System.out.println(height/width + " " + fov + " " + Math.tan(fov/2));
        this.focalLength = (height/width)/(2*Math.tan(fov/2));
        this.verticalFOV = fov;
        this.horizontalFOV = Math.atan(width / (2 * focalLength));
        this.pos = calcCenterToPos();
        this.cornerRotations = calcCornerRotations();
        System.out.println(this.focalLength);
        System.out.println(rot.getAbsolute());
        System.out.println(this.pos);
    }

    private GridPosition calcCenterToPos(){
        double rotX = absoluteRot.getxAxis();
        double rotY = absoluteRot.getxAxis();
        double rotZ = absoluteRot.getxAxis();

        double[] rotatedVector = getRotatedVector(rotX, rotY, rotZ);
        System.out.println("Rotvector: "+ rotatedVector[0] + " " + rotatedVector[1] + " " + rotatedVector[2]);
        double[] normalizedVector = normalizeVector(rotatedVector);

        return new Position3D(normalizedVector[0], normalizedVector[1], normalizedVector[2]);

    }

    private static double[] getRotatedVector(double rotX, double rotY, double rotZ){
        double[] vector = {0, 0, -1};

        double[][] matrixX = {
            {1, 0, 0},
            {0, Math.cos(rotX), -Math.sin(rotX)},
            {0, Math.sin(rotX), Math.cos(rotX)}
        };

        double[][] matrixY = {
            {Math.cos(rotY), 0, Math.sin(rotY)},
            {0, 1, 0},
            {-Math.sin(rotY), 0, Math.cos(rotY)}
        };

        double[][] matrixZ = {
            {Math.cos(rotZ), -Math.sin(rotZ), 0},
            {Math.sin(rotZ), Math.cos(rotZ), 0},
            {0, 0, 1}
        };

        double[] v_rot_z = multiplyMatrixByVector(matrixZ, vector);
        double[] v_rot_y = multiplyMatrixByVector(matrixY, v_rot_z);
        double[] v_rot_x = multiplyMatrixByVector(matrixX, v_rot_y);

        return v_rot_x;
    }

    private static double[] multiplyMatrixByVector(double[][] matrix, double[] vector) {
        double[] result = new double[vector.length];

        for (int i = 0; i < matrix.length; i++) {
            result[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }

        return result;
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
    //TODO: fix broken method.
    public static RelativeRotation getVectorRotation(double[] vector){
        vector = normalizeVector(vector);
        double upDown = Math.asin(vector[1]);
        double leftRight = (Math.atan2(vector[2], vector[0]) - Math.PI/2) % (Math.PI/2);
        System.out.println((Math.atan2(-1, 0) - Math.PI/2) % (Math.PI/2));
        return new RelativeRotation(upDown, leftRight);
    }


    private static double[] normalizeVector(double[] vector){
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
