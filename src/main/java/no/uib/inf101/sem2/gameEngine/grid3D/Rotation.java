package no.uib.inf101.sem2.gameEngine.grid3D;

public class Rotation {

    float xAxis;
    float yAxis;
    float zAxis;

    public Rotation(float xAxis, float yAxis, float zAxis){
        this.xAxis = xAxis % ((float) (2*Math.PI));
        this.yAxis = yAxis % ((float) (2*Math.PI));
        this.zAxis = zAxis % ((float) (2*Math.PI));
    }

    public float getxAxis(){
        return this.xAxis;
    }

    public float getyAxis(){
        return this.yAxis;
    }

    public float getzAxis(){
        return this.zAxis;
    }

    public Rotation getNegRotation(){
        return new Rotation(-xAxis, -yAxis, -zAxis);
    }

    @Override
    public String toString() {
        return "Rotation{" +
                "xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", zAxis=" + zAxis +
                '}';
    }
}
