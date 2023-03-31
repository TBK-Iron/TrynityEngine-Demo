package no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import no.uib.inf101.sem2.gameEngine.grid3D.Grid;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.view.raycaster.RelativeRotation;

public class Vector {
    double[] value;
    public Vector(double[] value){
        this.value = value;
    }

    public Vector(GridPosition value){
        this.value = new double[3];
        this.value[0] = value.x();
        this.value[1] = value.y();
        this.value[2] = value.z();
    }

    public static Vector getVector(GridPosition p1, GridPosition p2){
        double x = p2.x() - p1.x();
        double y = p2.y() - p1.y();
        double z = p2.z() - p1.z();

        return new Vector(new double[] {x, y, z});
    }

    public static Vector add(Vector v1, Vector v2){
        if(v1.getDims() != v2.getDims()){
            throw new IllegalArgumentException();
        }
        double[] result = new double[v1.getDims()];
        for(int i = 0; i < v1.getDims(); i++){
            result[i] = v1.get(i) + v2.get(i);
        }
        return new Vector(result);
    }

    //Made by chatGPT
    public static Vector crossProduct(Vector v1, Vector v2) {
        if (v1.getDims() != 3 || v2.getDims() != 3) {
            throw new IllegalArgumentException("Cross product is only supported for 3-dimensional vectors.");
        }
        double[] result = new double[3];
        result[0] = v1.get(1) * v2.get(2) - v1.get(2) * v2.get(1);
        result[1] = v1.get(2) * v2.get(0) - v1.get(0) * v2.get(2);
        result[2] = v1.get(0) * v2.get(1) - v1.get(1) * v2.get(0);
        return new Vector(result);
    }

    //Made by chatGPT
    public static double dotProduct(Vector v1, Vector v2) {
        if (v1.getDims() != v2.getDims()) {
            throw new IllegalArgumentException("Vectors must have the same dimensions for dot product.");
        }
        double result = 0;
        for (int i = 0; i < v1.getDims(); i++) {
            result += v1.get(i) * v2.get(i);
        }
        return result;
    }

    public double get(int i){
        return this.value[i];
    }

    public int getDims(){
        return value.length;
    }


    public static RelativeRotation getVectorRotation(Vector vector){
        vector = vector.normalized();
        double upDown = Math.asin(vector.value[1]);

        Vector xzV = (new Vector(new double[] {vector.value[0], vector.value[2]})).normalized();
        double leftRight = Math.atan2(xzV.value[0], xzV.value[1]);

        return new RelativeRotation(upDown, leftRight);
    }


    public Vector normalized(){
        double vectorMagnitude = this.magnitude();
        return this.scaledBy(1/vectorMagnitude);
    }

    public Vector scaledBy(double scalar){
        double[] scaledVector = new double[this.value.length];
        for(int i = 0; i < this.value.length; i++){
            scaledVector[i] = this.value[i] * scalar;
        }
        return new Vector(scaledVector);
    }

    public double magnitude(){
        double magnitude = 0;
        for(int i = 0; i < this.getDims(); i++){
            magnitude += Math.pow(this.value[i], 2);
        }
        return Math.sqrt(magnitude);
    }

    public Position3D getPoint(){
        if(getDims() != 3){
            throw new UnsupportedOperationException("getPoint method is only supported for 3 dimensional vectors");
        } else {
            return new Position3D(this.value[0], this.value[1], this.value[2]);
        }
        
    }

    @Override
    public String toString(){
        String[] sPoints = new String[getDims()];
        for(int i = 0; i < getDims(); i++){
            sPoints[i] = this.value[i] + "";
        }
        String result = "Points:[" + String.join(", ", sPoints) + "]";

        return result;
    }

    @Override
    public boolean equals(Object o2){
        Vector v2 = (Vector) o2;
        if(this.getDims() != v2.getDims()){
            return false;
        } else {
            for(int i = 0; i < this.getDims(); i++){
                if(Math.round(this.value[i] * 1000) != Math.round(v2.value[i] * 1000)){
                    return false;
                }
            }
        }
        return true;
    }
}