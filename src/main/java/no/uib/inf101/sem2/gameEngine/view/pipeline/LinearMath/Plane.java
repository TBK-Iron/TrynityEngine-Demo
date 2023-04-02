package no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath;

import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.model.shape.Position4D;


public class Plane {

    public Vector normal;
    public float dist;

    public Plane(Vector normal, float dist) {
        this.normal = normal;
        this.dist = dist;
    }

    public void normalize(){
        Vector normalizedNormal = normal.scaledBy(1/normal.magnitude());
        this.normal = normalizedNormal;
    }

    public boolean isVertexWithinPlane(GridPosition point){
        float distance = point.x() * this.normal.get(0) + point.y() * this.normal.get(1) + point.z() * this.normal.get(2) - this.dist;
        return distance <= 0;
    }

    public GridPosition interpolate(GridPosition p1, GridPosition p2) {
        //Can't use the dot product function here because p1 and p2 are 4D while the normal is 3D
        float d1 = p1.x() * this.normal.get(0) + p1.y() * this.normal.get(1) + p1.z() * this.normal.get(2) + this.dist;
        float d2 = p2.x() * this.normal.get(0) + p2.y() * this.normal.get(1) + p2.z() * this.normal.get(2) + this.dist;

        float t = d1 / (d1 - d2);

        float x = p1.x() + t * (p2.x() - p1.x());
        float y = p1.y() + t * (p2.y() - p1.y());
        float z = p1.z() + t * (p2.z() - p1.z());

        return new Position3D(x, y, z);
    }

    @Override
    public String toString() {
        return "Plane{" +
            "normal=" + normal +
            ", dist=" + dist +
            '}';
    }
}