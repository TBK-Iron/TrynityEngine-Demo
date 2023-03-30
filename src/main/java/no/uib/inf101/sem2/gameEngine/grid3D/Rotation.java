package no.uib.inf101.sem2.gameEngine.grid3D;

public class Rotation {

    double xAxis;
    double yAxis;
    double zAxis;

    public Rotation(double xAxis, double yAxis, double zAxis){
        this.xAxis = xAxis % (2*Math.PI);
        this.yAxis = yAxis % (2*Math.PI);
        this.zAxis = zAxis % (2*Math.PI);
    }

    public double getxAxis(){
        return this.xAxis;
    }

    public double getyAxis(){
        return this.yAxis;
    }

    public double getzAxis(){
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
