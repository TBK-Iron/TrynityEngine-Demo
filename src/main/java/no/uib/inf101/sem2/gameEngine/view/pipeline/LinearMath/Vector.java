package no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position2D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position4D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public final class Vector {
    private final float[] value;

    public Vector(float[] value){
        this.value = value;
    }

    public Vector(Position3D value){
        this.value = new float[]{
            value.x(),
            value.y(),
            value.z()
        };
    }

    public static Vector getVector(GridPosition p1, GridPosition p2){
        float x = p2.x() - p1.x();
        float y = p2.y() - p1.y();
        float z = p2.z() - p1.z();

        return new Vector(new float[] {x, y, z});
    }

    public static Vector add(Vector v1, Vector v2){
        if(v1.getDims() != v2.getDims()){
            throw new IllegalArgumentException();
        }
        float[] result = new float[v1.getDims()];
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
        float[] result = new float[3];
        result[0] = v1.get(1) * v2.get(2) - v1.get(2) * v2.get(1);
        result[1] = v1.get(2) * v2.get(0) - v1.get(0) * v2.get(2);
        result[2] = v1.get(0) * v2.get(1) - v1.get(1) * v2.get(0);
        return new Vector(result);
    }

    //Made by chatGPT
    public static float dotProduct(Vector v1, Vector v2) {
        if (v1.getDims() != v2.getDims()) {
            throw new IllegalArgumentException("Vectors must have the same dimensions for dot product.");
        }
        float result = 0;
        for (int i = 0; i < v1.getDims(); i++) {
            result += v1.get(i) * v2.get(i);
        }
        return result;
    }

    public float get(int i){
        return this.value[i];
    }

    public int getDims(){
        return value.length;
    }


    public static RelativeRotation getVectorRotation(Vector vector){
        vector = vector.normalized();
        float upDown = (float) Math.asin(vector.value[1]);

        Vector xzV = (new Vector(new float[] {vector.value[0], vector.value[2]})).normalized();
        float leftRight = (float) Math.atan2(xzV.value[0], xzV.value[1]);

        return new RelativeRotation(upDown, leftRight);
    }


    public Vector normalized(){
        float vectorMagnitude = this.magnitude();
        return this.scaledBy(1/vectorMagnitude);
    }

    public Vector scaledBy(float scalar){
        float[] scaledVector = new float[this.value.length];
        for(int i = 0; i < this.value.length; i++){
            scaledVector[i] = this.value[i] * scalar;
        }
        return new Vector(scaledVector);
    }

    public float magnitude(){
        float magnitude = 0;
        for(int i = 0; i < this.getDims(); i++){
            magnitude += Math.pow(this.value[i], 2);
        }
        return (float) Math.sqrt(magnitude);
    }

    public GridPosition getPoint(){
        if(getDims() == 2){
            return new Position2D(this.value[0], this.value[1]);
        } else if(getDims() == 3){
            return new Position3D(this.value[0], this.value[1], this.value[2]);
        } else if (getDims() == 4){
            return new Position4D(this.value[0], this.value[1], this.value[2], this.value[3]);
        } else {
            throw new UnsupportedOperationException("Vector must have 2, 3 or 4 dimensions to be converted to a point.");
        }
        
    }

    @Override
    public String toString(){
        String[] sPoints = new String[getDims()];
        for(int i = 0; i < getDims(); i++){
            sPoints[i] = String.valueOf(this.value[i]);
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