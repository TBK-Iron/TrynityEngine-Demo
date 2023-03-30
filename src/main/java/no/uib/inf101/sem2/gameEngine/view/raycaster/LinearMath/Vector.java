package no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath;

import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.view.raycaster.RelativeRotation;

public class Vector {
    double[] value;
    public Vector(double[] value){
        this.value = value;
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

    private double magnitude(){
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
        String result = "Points:[" + this.value[0] + ", " + this.value[1] + ", " + this.value[2] + "]";
        for(int i = 0; i < getDims(); i++){
            result += this.value[i] + ", ";
        }
        result += "]";

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