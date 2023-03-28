package no.uib.inf101.sem2.gameEngine.view.raycaster;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.text.Position;

import no.uib.inf101.sem2.gameEngine.grid3D.GridPosision;
import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;

public class Camera implements ICamera {
    GridPosision centerPos;
    GridPosision pos;
    Rotation rotation;
    double aspectRatio;
    double focalLength;
    double verticalFOV;
    double horizontalFOV;
    Double[] cosVals;
    Double[] sinVals;
    ArrayList<Double[]> normalizedCorners;

    public Camera(int width, int height, double fov){
        setPos(new GridPosision(0, 0, 0));
        setRotation(new Rotation(0, 0, 0));
        this.aspectRatio = (double) width/height;
        this.focalLength = height/(2*Math.tan(fov));
        this.verticalFOV = fov;
        this.horizontalFOV = Math.atan(width / (2 * focalLength));
        this.pos = calcCenterToPos();
        this.normalizedCorners = calcCorners();
    }

    private GridPosision calcCenterToPos(){
        GridPosision negFacingVector = new GridPosision(
            -cosVals[2] - sinVals[1],
            -cosVals[0] - sinVals[2],
            -cosVals[1] - sinVals[0]
        );
        Double scalor = this.focalLength / (Math.sqrt(Math.pow(negFacingVector.x(), 2) + 
        Math.pow(negFacingVector.y(), 2) + Math.pow(negFacingVector.z(), 2)));

        double x = scalor * negFacingVector.x() + this.centerPos.x();
        double y = scalor * negFacingVector.y() + this.centerPos.y();
        double z = scalor * negFacingVector.z() + this.centerPos.z();

        return new GridPosision(x, y, z);

    }

    private ArrayList<Double[]> calcCorners(){
        ArrayList<Double[]> corners = new ArrayList<>();
        Double[] upperLeft = {this.centerPos.x() };
    }

    @Override
    public void setPos(GridPosision pos){
        this.centerPos = pos;
    }

    @Override
    public void setRotation(Rotation rotation){
        this.rotation = rotation;
        cosVals = new Double[] {Math.cos(rotation.getxAxis()), Math.cos(rotation.getyAxis()), Math.cos(rotation.getzAxis())};
        sinVals = new Double[] {Math.sin(rotation.getxAxis()), Math.sin(rotation.getyAxis()), Math.sin(rotation.getzAxis())};

    }

    public boolean isRendered(GridPosision vertex){
       

        
    }


    private double[] normalize(double[] vector){
        double vectorMagnitude = Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2) + Math.pow(vector[2], 2));
        double[] normalizedVector = {vector[0] / vectorMagnitude, vector[1] / vectorMagnitude, vector[2] / vectorMagnitude};
        return normalizedVector;
    }

}
