package no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position2D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position4D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

/**
 * A class representing an n-dimensional vector with various vector operations.
 * This class supports basic operations such as addition, subtraction, multiplication, and division,
 * as well as more advanced operations like dot product, cross product, and vector normalization.
 */
public final class Vector {
    private final float[] value;

    /**
     * Constructs a new Vector with the given float array.
     *
     * @param value The float array representing the vector.
     */
    public Vector(float[] value){
        this.value = value;
    }

    /**
     * Constructs a new Vector from the given Position3D.
     *
     * @param value The Position3D object representing the vector.
     */
    public Vector(Position3D value){
        this.value = new float[]{
            value.x(),
            value.y(),
            value.z()
        };
    }

    /**
     * Returns a new Vector representing the difference between two GridPositions.
     *
     * @param p1 The first GridPosition.
     * @param p2 The second GridPosition.
     * @return A new Vector representing the difference between p1 and p2.
     */
    public static Vector getVector(GridPosition p1, GridPosition p2){
        if(!(p1 instanceof Position3D) || !(p2 instanceof Position3D)){
            throw new IllegalArgumentException("Both GridPositions must be 3D");
        } else {
            float x = p2.x() - p1.x();
            float y = p2.y() - p1.y();
            float z = p2.z() - p1.z();

            return new Vector(new float[] {x, y, z});
        }
    }

    /**
     * Returns a new Vector representing the component-wise addition of two vectors.
     *
     * @param v1 The first Vector.
     * @param v2 The second Vector.
     * @return A new Vector representing the component-wise addition of v1 and v2.
     * @throws IllegalArgumentException If the input vectors have different dimensions.
     */
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

    /**
     * Returns a new Vector representing the component-wise subtraction of two vectors.
     *
     * @param v1 The first Vector.
     * @param v2 The second Vector.
     * @return A new Vector representing the component-wise subtraction of v1 and v2.
     * @throws IllegalArgumentException If the input vectors have different dimensions.
     */
    public static Vector subtract(Vector v1, Vector v2){
        if(v1.getDims() != v2.getDims()){
            throw new IllegalArgumentException();
        }
        float[] result = new float[v1.getDims()];
        for(int i = 0; i < v1.getDims(); i++){
            result[i] = v1.get(i) - v2.get(i);
        }
        return new Vector(result);
    }

    /**
     * Returns a new Vector representing the component-wise multiplication of two vectors.
     *
     * @param v1 The first Vector.
     * @param v2 The second Vector.
     * @return A new Vector representing the component-wise multiplication of v1 and v2.
     * @throws IllegalArgumentException If the input vectors have different dimensions.
     */
    public static Vector multiply(Vector v1, Vector v2){
        if(v1.getDims() != v2.getDims()){
            throw new IllegalArgumentException();
        }
        float[] result = new float[v1.getDims()];
        for(int i = 0; i < v1.getDims(); i++){
            result[i] = v1.get(i) * v2.get(i);
        }
        return new Vector(result);
    }

    /**
     * Returns a new Vector representing the component-wise division of two vectors.
     *
     * @param v1 The first Vector.
     * @param v2 The second Vector.
     * @return A new Vector representing the component-wise division of v1 and v2.
     * @throws IllegalArgumentException If the input vectors have different dimensions.
     */
    public static Vector divide(Vector v1, Vector v2){
        if(v1.getDims() != v2.getDims()){
            throw new IllegalArgumentException();
        }
        float[] result = new float[v1.getDims()];
        for(int i = 0; i < v1.getDims(); i++){
            result[i] = v1.get(i) / v2.get(i);
        }
        return new Vector(result);
    }

    /**
     * Returns a new Vector with the minimum component-wise values of two vectors.
     *
     * @param v1 The first Vector.
     * @param v2 The second Vector.
     * @return A new Vector with the minimum component-wise values of v1 and v2.
     * @throws IllegalArgumentException If the input vectors have different dimensions.
     */
    public static Vector minVector(Vector v1, Vector v2){
        if(v1.getDims() != v2.getDims()){
            throw new IllegalArgumentException();
        }
        float[] result = new float[v1.getDims()];
        for(int i = 0; i < v1.getDims(); i++){
            result[i] = Math.min(v1.get(i), v2.get(i));
        }
        return new Vector(result);
    }

    /**
     * Returns a new Vector with the maximum component-wise values of two vectors.
     *
     * @param v1 The first Vector.
     * @param v2 The second Vector.
     * @return A new Vector with the maximum component-wise values of v1 and v2.
     * @throws IllegalArgumentException If the input vectors have different dimensions.
     */
    public static Vector maxVector(Vector v1, Vector v2){
        if(v1.getDims() != v2.getDims()){
            throw new IllegalArgumentException();
        }
        float[] result = new float[v1.getDims()];
        for(int i = 0; i < v1.getDims(); i++){
            result[i] = Math.max(v1.get(i), v2.get(i));
        }
        return new Vector(result);
    }

    /**
     * Returns a new Vector representing the cross product of two 3-dimensional vectors.
     *
     * @param v1 The first Vector.
     * @param v2 The second Vector.
     * @return A new Vector representing the cross product of v1 and v2.
     * @throws IllegalArgumentException If the input vectors are not 3-dimensional.
     * @author ChatGPT
     */
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


    /**
     * Returns the dot product of two vectors.
     *
     * @param v1 The first Vector.
     * @param v2 The second Vector.
     * @return The dot product of v1 and v2.
     * @throws IllegalArgumentException If the input vectors have different dimensions.
     * @author ChatGPT
     */
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

    
    /**
     * Returns the value at the specified index in the Vector.
     *
     * @param i The index of the value to be retrieved.
     * @return The float value at the specified index.
     */
    public float get(int i){
        return this.value[i];
    }

    /**
     * Returns the number of dimensions of the Vector.
     *
     * @return The number of dimensions of the Vector.
     */
    public int getDims(){
        return value.length;
    }


    /**
     * Returns a new RelativeRotation object representing the rotation of the Vector.
     *
     * @return A new RelativeRotation object representing the rotation of the Vector.
     */
    public static RelativeRotation getVectorRotation(Vector vector){
        vector = vector.normalized();
        float upDown = (float) Math.asin(vector.value[1]);

        Vector xzV = (new Vector(new float[] {vector.value[0], vector.value[2]})).normalized();
        float leftRight = (float) Math.atan2(xzV.value[0], xzV.value[1]);

        return new RelativeRotation(upDown, leftRight);
    }

    /**
     * Returns a new Vector representing the normalized version of the current Vector.
     *
     * @return A new Vector representing the normalized version of the current Vector.
     */
    public Vector normalized(){
        float vectorMagnitude = this.magnitude();
        return this.scaledBy(1/vectorMagnitude);
    }

    /**
     * Returns a new Vector obtained by scaling the current Vector by a scalar value.
     *
     * @param scalar The scalar value to scale the Vector by.
     * @return A new Vector obtained by scaling the current Vector by the scalar value.
     */
    public Vector scaledBy(float scalar){
        float[] scaledVector = new float[this.value.length];
        for(int i = 0; i < this.value.length; i++){
            scaledVector[i] = this.value[i] * scalar;
        }
        return new Vector(scaledVector);
    }

    /**
     * Returns the magnitude (length) of the Vector.
     *
     * @return The magnitude (length) of the Vector.
     */
    public float magnitude(){
        float magnitude = 0;
        for(int i = 0; i < this.getDims(); i++){
            magnitude += Math.pow(this.value[i], 2);
        }
        return (float) Math.sqrt(magnitude);
    }


    /**
     * Returns a GridPosition object representing the current Vector as a point.
     * The Vector must have 2, 3, or 4 dimensions to be converted to a point.
     *
     * @return A GridPosition object representing the current Vector as a point.
     * @throws UnsupportedOperationException If the Vector has an unsupported number of dimensions.
     */
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

    /**
     * Returns a string representation of the current Vector.
     *
     * @return A string representation of the current Vector.
     */
    @Override
    public String toString(){
        String[] sPoints = new String[getDims()];
        for(int i = 0; i < getDims(); i++){
            sPoints[i] = String.valueOf(this.value[i]);
        }
        String result = "Points:[" + String.join(", ", sPoints) + "]";

        return result;
    }

    /**
     * Compares the current Vector with another object for equality.
     *
     * @param o2 The other object to compare with the current Vector.
     * @return A boolean value indicating whether the current Vector and the other object are equal.
     */
    @Override
    public boolean equals(Object o2){
        if(!(o2 instanceof Vector)){
            return false;
        } else if (o2 == this){
            return true;
        }
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