package no.uib.inf101.sem2.gameEngine.view.raycaster;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;

public class RelativeRotation {
    float upDown;
    float leftRight;
    Rotation absoluteRotation;

    public RelativeRotation(float upDown, float leftRight){
        this.upDown = upDown;
        this.leftRight = leftRight;
        if(!isValidRotation(upDown, leftRight)){
            throw new IllegalArgumentException("UpDown rotation " + upDown + " and left right rotation " + leftRight + " is not a valid rotation.");
        }
    }

    private static boolean isValidRotation(float upDown, float leftRight){
        if(upDown > Math.PI/2 || upDown < -Math.PI/2){
            return false;
        } else {
            return true;
        }
    }

    public Rotation getAbsolute(){
        if(this.absoluteRotation == null){
            float xAxis = (float) Math.cos(this.leftRight) * this.upDown;
            float yAxis = this.leftRight;
            float zAxis = (float) Math.sin(this.leftRight) * this.upDown;

            this.absoluteRotation = new Rotation(xAxis, yAxis, zAxis);
        }
        
        return this.absoluteRotation;
    }

    public RelativeRotation add(RelativeRotation rotation2){
        float newLeftRight = (this.leftRight + rotation2.leftRight) % ((float) ( 2*Math.PI));
        float newUpDown;
        if(this.upDown + rotation2.upDown > Math.PI/2){
            newUpDown = (float) Math.PI/2 - (this.upDown + rotation2.upDown - (float) Math.PI/2);
        } else if(this.upDown + rotation2.upDown < -Math.PI/2){
            newUpDown = -(float) Math.PI/2 - (this.upDown + rotation2.upDown + (float) Math.PI/2);
        } else {
            newUpDown = this.upDown + rotation2.upDown;
        }
        

        return new RelativeRotation(newUpDown, newLeftRight);
    }

    protected float getLeftRight(){
        return this.leftRight % ((float) (2*Math.PI));
    }

    protected float getUpDown(){
        return this.upDown;
    }

    @Override
    public String toString(){
        String result = "RelativeRotation[leftRight=" + leftRight + ", upDown=" + upDown + "]";
        return result;
    }

    @Override
    public boolean equals(Object o2){
        RelativeRotation r2 = (RelativeRotation) o2;

        //Check if all rotations are equal up to four decimals
        if(Math.round(this.upDown*10000) == Math.round(r2.upDown*10000) && Math.round(this.leftRight*10000) == Math.round(r2.leftRight*10000)){
            return true;
        } else {
            return false;
        }
    }
}
