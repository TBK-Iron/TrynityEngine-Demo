package no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

/**
 * Represents a plane in 3D space defined by a normal vector and a distance.
 */
public class Plane {

    /**
     * The normal vector of the plane.
     */
    protected Vector normal;

    /**
     * The distance of the plane from the origin.
     */
    protected float dist;

    /**
     * Constructs a new Plane with the specified normal vector and distance.
     *
     * @param normal the normal vector of the plane
     * @param dist the distance of the plane from the origin
     */
    protected Plane(Vector normal, float dist) {
        this.normal = normal;
        this.dist = dist;
    }

    /**
     * Normalizes the normal vector of this plane.
     */
    protected void normalize(){
        Vector normalizedNormal = normal.scaledBy(1/normal.magnitude());
        this.normal = normalizedNormal;
    }

    /**
     * Checks if the given vertex (point) is within the plane.
     * 
     * @param point the GridPosition representing the vertex to be checked
     * @return true if the vertex is within the plane, false otherwise
     */
    protected boolean isVertexWithinPlane(GridPosition point){
        float distance = point.x() * this.normal.get(0) + point.y() * this.normal.get(1) + point.z() * this.normal.get(2) - this.dist;
        return distance <= 0;
    }

    /**
     * Calculates the parameter 't' used to determine the intersection point of a line segment and the plane.
     * 
     * @param p1 the GridPosition representing the first point of the line segment
     * @param p2 the GridPosition representing the second point of the line segment
     * @return the value of the parameter 't'
     */
    protected float calculateT(GridPosition p1, GridPosition p2) {
        //Can't use the dot product function here because p1 and p2 are 4D while the normal is 3D
        float d1 = p1.x() * this.normal.get(0) + p1.y() * this.normal.get(1) + p1.z() * this.normal.get(2) - this.dist;
        float d2 = p2.x() * this.normal.get(0) + p2.y() * this.normal.get(1) + p2.z() * this.normal.get(2) - this.dist;

        float t = d1 / (d1 - d2);

        return t;
    }

    @Override
    public String toString() {
        return "Plane{" +
            "normal=" + normal +
            ", dist=" + dist +
            '}';
    }
}