package no.uib.inf101.sem2.gameEngine.grid3D;

public class Rotation {

    double xAxis;
    double yAxis;
    double zAxis;

    public Rotation(double xAxis, double yAxis, double zAxis){
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.zAxis = zAxis;
    }

    public double getxAxis(){
        return this.xAxis % (2*Math.PI);
    }

    public double getyAxis(){
        return this.yAxis % (2*Math.PI);
    }

    public double getzAxis(){
        return this.zAxis % (2*Math.PI);
    }

    @Override
    public String toString() {
        return "Rotation{" +
                "xAxis=" + (xAxis % (2*Math.PI)) +
                ", yAxis=" + (yAxis % (2*Math.PI)) +
                ", zAxis=" + (zAxis % (2*Math.PI)) +
                '}';
    }
}
