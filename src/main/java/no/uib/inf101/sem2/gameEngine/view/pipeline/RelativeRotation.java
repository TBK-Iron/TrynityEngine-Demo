package no.uib.inf101.sem2.gameEngine.view.pipeline;

public class RelativeRotation {
    float upDown;
    float leftRight;

    public RelativeRotation(float upDown, float leftRight){
        this.upDown = upDown;
        this.leftRight = leftRight;
        validateRotation();
    }

    private void validateRotation(){
        if(this.upDown > Math.PI/2){
            this.upDown = (float) Math.PI/2;
        } else if (upDown < -Math.PI/2){
            this.upDown = -(float) Math.PI/2;
        }
        this.leftRight = this.leftRight % ((float) (2*Math.PI));
    }

    public RelativeRotation getNegRotation(){
        return new RelativeRotation(-this.upDown, -this.leftRight);
    }

    public RelativeRotation add(RelativeRotation rotation2){
        float newLeftRight = this.leftRight + rotation2.leftRight;
        float newUpDown = this.upDown - rotation2.upDown;
        
        return new RelativeRotation(newUpDown, newLeftRight);
    }

    public float getLeftRight(){
        return this.leftRight;
    }

    public float getUpDown(){
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
