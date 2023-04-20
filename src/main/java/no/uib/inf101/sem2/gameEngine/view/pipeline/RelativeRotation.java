package no.uib.inf101.sem2.gameEngine.view.pipeline;

import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.RotateTransform;

public final class RelativeRotation {
    private final float pivot;
    private final float upDown;
    private final float leftRight;

     /**
     * Constructs a new RelativeRotation object with the specified pivot, up-down, and left-right rotation values.
     *
     * @param pivot     The pivot rotation value.
     * @param upDown    The up-down rotation value.
     * @param leftRight The left-right rotation value.
     */
    public RelativeRotation(float pivot, float upDown, float leftRight){
        this.pivot = pivot % ((float) (2*Math.PI));

        if(upDown > Math.PI/2){
            this.upDown = (float) Math.PI/2;
        } else if (upDown < -Math.PI/2){
            this.upDown = -(float) Math.PI/2;
        } else {
            this.upDown = upDown;
        }

        this.leftRight = leftRight % ((float) (2*Math.PI));
    }

    /**
     * Constructs a new RelativeRotation object with the specified up-down and left-right rotation values.
     * The pivot rotation value is set to 0.
     * The reason this contructor exists is for backwards compatibility with when the pivot rotation value was not a parameter.
     *
     * @param upDown    The up-down rotation value.
     * @param leftRight The left-right rotation value.
     */
    public RelativeRotation(float upDown, float leftRight){
        this(0, upDown, leftRight);
    }

    /**
     * Returns the negative rotation of this relative rotation.
     *
     * @return A new RelativeRotation object representing the negative rotation.
     */
    public RelativeRotation getNegRotation(){
        return new RelativeRotation(-this.pivot,-this.upDown, -this.leftRight);
    }

    /**
     * Adds the given RelativeRotation to this relative rotation.
     *
     * @param rotation2 The RelativeRotation to be added.
     * @return A new RelativeRotation object representing the sum of the two rotations.
     */
    public RelativeRotation add(RelativeRotation rotation2){
        float newPivot = this.pivot + rotation2.pivot;
        float newLeftRight = this.leftRight + rotation2.leftRight;
        float newUpDown = this.upDown - rotation2.upDown;
        
        return new RelativeRotation(newPivot, newUpDown, newLeftRight);
    }

    /**
     * Returns a vector representing the direction of this rotation.
     * @return A vector representing the direction of this rotation.
     */
    public Vector getVector(){
        Matrix rotMatrix = new RotateTransform(this, false).getMatrix();
        Vector zeroRotationVector = new Vector(new float[]{0, 0, 1});

        return rotMatrix.multiply(zeroRotationVector);
    }

    /**
     * Returns the left-right rotation value.
     *
     * @return The left-right rotation value.
     */
    public float getLeftRight(){
        return this.leftRight;
    }

    /**
     * Returns the up-down rotation value.
     *
     * @return The up-down rotation value.
     */
    public float getUpDown(){
        return this.upDown;
    }

    /**
     * Returns the pivot rotation value.
     *
     * @return The pivot rotation value.
     */
    public float getPivot(){
        return this.pivot;
    }

    @Override
    public String toString(){
        String result = "RelativeRotation[pivot=" + pivot + ", leftRight=" + leftRight + ", upDown=" + upDown + "]";
        return result;
    }

    /**
     * Checks if the given object is equal to this RelativeRotation object.
     * Rotations are considered equal if they are equal up to four decimal places.
     *
     * @param o2 The object to compare with this RelativeRotation.
     * @return true if the given object is equal to this RelativeRotation, false otherwise.
     */
    @Override
    public boolean equals(Object o2){
        RelativeRotation r2 = (RelativeRotation) o2;

        //Check if all rotations are equal up to four decimals
        if(Math.round(this.upDown*10000) == Math.round(r2.upDown*10000)){
            if(Math.round(this.leftRight*10000) == Math.round(r2.leftRight*10000)){
                if(Math.round(this.pivot*10000) == Math.round(r2.pivot*10000)){
                    return true;
                }
            }
        } 
        return false;
        
    }
}
