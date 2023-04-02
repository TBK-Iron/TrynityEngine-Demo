/*package no.uib.inf101.sem2.gameEngine.grid3D;

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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Rotation){
            Rotation rot = (Rotation) obj;
            if(Math.abs(rot.getxAxis() - this.getxAxis()) < 0.0001){
                if(Math.abs(rot.getyAxis() - this.getyAxis()) < 0.0001){
                    if(Math.abs(rot.getzAxis() - this.getzAxis()) < 0.0001){
                        return true;
                    }
                }
            }
        }

        return false;
        
    }
}
*/