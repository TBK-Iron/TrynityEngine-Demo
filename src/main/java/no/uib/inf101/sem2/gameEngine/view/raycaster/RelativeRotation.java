package no.uib.inf101.sem2.gameEngine.view.raycaster;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;

public class RelativeRotation {
    Double upDown;
    Double leftRight;

    public RelativeRotation(Double upDown, Double leftRight){
        this.upDown = upDown;
        this.leftRight = leftRight;
        if(!isValidRotation(upDown, leftRight)){
            throw new IllegalArgumentException("UpDown rotation " + upDown + " and left right rotation " + leftRight + " is not a valid rotation.");
        }
    }

    private static boolean isValidRotation(Double upDown, Double leftRight){
        if(upDown > Math.PI/2 || upDown < -Math.PI/2){
            return false;
        } else {
            return true;
        }
    }

    public Rotation getAbsolute(){
        Double xAxis = Math.cos(this.leftRight) * this.upDown;
        Double yAxis = this.leftRight;
        Double zAxis = Math.sin(this.leftRight) * this.upDown;

        return new Rotation(xAxis, yAxis, zAxis);
    }

    public RelativeRotation add(RelativeRotation rotation2){
        Double newLeftRight = (this.leftRight + rotation2.leftRight) % (2*Math.PI);
        Double newUpDown;
        if(this.upDown + rotation2.upDown > Math.PI/2){
            newUpDown = Math.PI/2 - (this.upDown + rotation2.upDown - Math.PI/2);
        } else if(this.upDown + rotation2.upDown < -Math.PI/2){
            newUpDown = -Math.PI/2 - (this.upDown + rotation2.upDown + Math.PI/2);
        } else {
            newUpDown = this.upDown + rotation2.upDown;
        }
        

        return new RelativeRotation(newUpDown, newLeftRight);
    }

    protected double getLeftRight(){
        return this.leftRight % (2*Math.PI);
    }

    protected double getUpDown(){
        return this.upDown;
    }
}
