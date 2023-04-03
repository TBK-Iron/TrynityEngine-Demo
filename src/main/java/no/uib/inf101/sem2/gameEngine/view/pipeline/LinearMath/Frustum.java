package no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.BoundingSphere;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

public class Frustum {
    Plane[] planes;

    public Frustum(Matrix viewProjectionMatrix, float near, float far){

        planes = new Plane[6];

        extractPlanes(viewProjectionMatrix, near, far);
        normalizePlanes();

        /* System.out.println("Left: " + planes[0]);
        System.out.println("Right: " + planes[1]);
        System.out.println("Bottom: " + planes[2]);
        System.out.println("Top: " + planes[3]);
        System.out.println("Near: " + planes[4]);
        System.out.println("Far: " + planes[5]); */
    }

    private void extractPlanes(Matrix viewProjMatrix, float near, float far){
        float[][] m = viewProjMatrix.value;

        this.planes[0] = new Plane(new Vector(new float[]{m[0][3] + m[0][0], m[1][3] + m[1][0], m[2][3] + m[2][0]}), 0); // Left
        this.planes[1] = new Plane(new Vector(new float[]{m[0][3] - m[0][0], m[1][3] - m[1][0], m[2][3] - m[2][0]}), 0); // Right
        this.planes[2] = new Plane(new Vector(new float[]{m[0][3] + m[0][1], m[1][3] + m[1][1], m[2][3] + m[2][1]}), 0); // Bottom
        this.planes[3] = new Plane(new Vector(new float[]{m[0][3] - m[0][1], m[1][3] - m[1][1], m[2][3] - m[2][1]}), 0); // Top
        this.planes[4] = new Plane(new Vector(new float[]{m[0][3] + m[0][2], m[1][3] + m[1][2], m[2][3] + m[2][2]}), near); // Near
        this.planes[5] = new Plane(new Vector(new float[]{m[0][3] - m[0][2], m[1][3] - m[1][2], m[2][3] - m[2][2]}), far); // Far

    }

    private void normalizePlanes() {
        for(int i = 0; i < planes.length; i++){
            planes[i].normalize();
        }
    }

    // Checks if the shape's bounding sphere is within the view frustum
    public boolean isShapeVisible(Shape3D shape) {
        BoundingSphere boundingSphere = shape.getBoundingSphere();

        GridPosition center = boundingSphere.centerPos();
        float radius = boundingSphere.radius();

        for (Plane plane : planes) {
            float distance = Vector.dotProduct(plane.normal, new Vector(center)) - plane.dist;

            // If the bounding sphere is entirely outside the plane, the shape should be culled
            if (distance < -radius) {
                return false;
            }
        }

        // If the bounding sphere is inside all the frustum planes, the shape should not be culled
        return true;
    }


    //TODO: fix method for clipping faces with the frustum
    public Face clipFace(Face face) {
        Face clippedFace = face;

        for(Plane plane : this.planes){
            clippedFace = clipFaceAgainstPlane(clippedFace, plane);
            //System.out.println(clippedFace);
        }
        //clippedFace.removeDuplicatePoints();

        return clippedFace;
    }
    private Face clipFaceAgainstPlane(Face face, Plane plane){
        ArrayList<GridPosition> inputVertices = face.getPoints();
        ArrayList<GridPosition> outputVertices = new ArrayList<>();

        for(int i = 0; i < inputVertices.size(); i++){
            GridPosition currentPoint = inputVertices.get(i);
            GridPosition nextPoint = inputVertices.get((i+1) % inputVertices.size());

            boolean isCurrentInside = plane.isVertexWithinPlane(currentPoint);
            boolean isNextInside = plane.isVertexWithinPlane(nextPoint);

            //System.out.println("Current: " + currentPoint + " is inside: " + isCurrentInside);

            if(isCurrentInside){
                outputVertices.add(currentPoint);
            }
            if(isCurrentInside != isNextInside){
                GridPosition intersectionPoint = plane.interpolate(currentPoint, nextPoint);
                if(intersectionPoint != null){
                    outputVertices.add(intersectionPoint);
                }
            }
        }

        Face clippedFace = new Face(outputVertices, face.getColor());
        return clippedFace;
    }
    
    
}
