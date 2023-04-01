package no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.grid3D.Grid;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;

public class Frustum {
    Plane[] planes;

    public Frustum(Matrix viewProjectionMatrix){

        planes = new Plane[6];

        extractPlanes(viewProjectionMatrix);
        normalizePlanes();
    }

    private void extractPlanes(Matrix viewProjMatrix){
        float[][] m = viewProjMatrix.value;

        planes[0] = new Plane(new Vector(new float[]{m[0][3] + m[0][0], m[1][3] + m[1][0], m[2][3] + m[2][0]}), m[3][3] + m[3][0]); // Left
        planes[1] = new Plane(new Vector(new float[]{m[0][3] - m[0][0], m[1][3] - m[1][0], m[2][3] - m[2][0]}), m[3][3] - m[3][0]); // Right
        planes[2] = new Plane(new Vector(new float[]{m[0][3] + m[0][1], m[1][3] + m[1][1], m[2][3] + m[2][1]}), m[3][3] + m[3][1]); // Bottom
        planes[3] = new Plane(new Vector(new float[]{m[0][3] - m[0][1], m[1][3] - m[1][1], m[2][3] - m[2][1]}), m[3][3] - m[3][1]); // Top
        planes[4] = new Plane(new Vector(new float[]{m[0][3] + m[0][2], m[1][3] + m[1][2], m[2][3] + m[2][2]}), m[3][3] + m[3][2]); // Near
        planes[5] = new Plane(new Vector(new float[]{m[0][3] - m[0][2], m[1][3] - m[1][2], m[2][3] - m[2][2]}), m[3][3] - m[3][2]); // Far

    }

    private void normalizePlanes() {
        for(int i = 0; i < planes.length; i++){
            planes[i] = new Plane(planes[i].normal().scaledBy(1/planes[i].normal().magnitude()), planes[i].dist());
        }
    }

    public boolean isFaceCulled(Face face){
        Vector[] AABB = face.getAABB();
        Vector min = AABB[0];
        Vector max = AABB[1];

        for(int i = 0; i < 6; i++){
            float[] p = new float[3];
            if (this.planes[i].normal().get(0) >= 0) {
                p[0] = min.value[0];
            } else {
                p[0] = max.value[0];
            }
            
            if (this.planes[i].normal().get(1) >= 0) {
                p[1] = min.value[1];
            } else {
                p[1] = max.value[1];
            }
            
            if (this.planes[i].normal().get(2) >= 0) {
                p[2] = min.value[2];
            } else {
                p[2] = max.value[2];
            }

            float dist = (new Vector(p)).magnitude();

            if(dist < 0){
                return true;
            }
        }
        return false;
    }

    //TODO: fix method for clipping faces with the frustum
    public Face clipFace(Face face) {
        Face clippedFace = face;

        for(Plane plane : this.planes){
            clippedFace = clipFaceAgainstPlane(clippedFace, plane);
        }
        return clippedFace;
    } 
    
    private Face clipFaceAgainstPlane(Face face, Plane plane){
        ArrayList<GridPosition> inputVertices = face.getPoints();
        ArrayList<GridPosition> outputVertices = new ArrayList<>();

        for(int i = 0; i < inputVertices.size(); i++){
            GridPosition currentPoint = inputVertices.get(i);
            GridPosition nextPoint = inputVertices.get((i+1) % inputVertices.size());

            boolean isCurrentInside = isVertexWithinPlane(currentPoint, plane);
            boolean isNextInside = isVertexWithinPlane(nextPoint, plane);

            if(isCurrentInside){
                System.out.println("test");
                outputVertices.add(currentPoint);
            }
            if(isCurrentInside != isNextInside){
                GridPosition intersectionPoint = intersectEdgePlane(currentPoint, nextPoint, plane);
                if(intersectionPoint != null){
                    outputVertices.add(intersectionPoint);
                }
            }
        }

        Face clippedFace = new Face(outputVertices, face.getColor());
        return clippedFace;
    }

    private static boolean isVertexWithinPlane(GridPosition point, Plane plane){
        float distance = point.x() * plane.normal().get(0) + point.y() * plane.normal().get(1) + point.z() * plane.normal().get(2) + plane.dist(); 
        System.out.println("distance");
        return distance >= 0;
    }

    private static GridPosition intersectEdgePlane(GridPosition p1, GridPosition p2, Plane plane) {
        float d1 = p1.x() * plane.normal().get(0) + p1.y() * plane.normal().get(1) + p1.z() * plane.normal().get(2) + plane.dist();
        float d2 = p2.x() * plane.normal().get(0) + p2.y() * plane.normal().get(1) + p2.z() * plane.normal().get(2) + plane.dist();

        float t = d1 / (d1 - d2);

        float x = p1.x() + t * (p2.x() - p1.x());
        float y = p1.y() + t * (p2.y() - p1.y());
        float z = p1.z() + t * (p2.z() - p1.z());

        return new Position3D(x, y, z);
    }
    
    
}
