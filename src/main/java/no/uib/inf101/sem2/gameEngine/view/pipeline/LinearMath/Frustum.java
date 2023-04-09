package no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.BoundingSphere;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.FaceTexture;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

public class Frustum {
    private Plane[] planes;

    public Frustum(Matrix projectionMatrix, float near, float far){

        planes = new Plane[6];

        extractPlanes(projectionMatrix, near, far);
        normalizePlanes();

        /* System.out.println("Left: " + planes[0]);
        System.out.println("Right: " + planes[1]);
        System.out.println("Bottom: " + planes[2]);
        System.out.println("Top: " + planes[3]);
        System.out.println("Near: " + planes[4]);
        System.out.println("Far: " + planes[5]); */
    }

    private void extractPlanes(Matrix m, float near, float far){
        this.planes[0] = new Plane(new Vector(new float[]{m.get(0,3) + m.get(0,0), m.get(1,3) + m.get(1,0), m.get(2,3) + m.get(2,0)}), 0); // Left
        this.planes[1] = new Plane(new Vector(new float[]{m.get(0,3)- m.get(0,0), m.get(1,3)- m.get(1,0), m.get(2,3)- m.get(2,0)}), 0); // Right
        this.planes[2] = new Plane(new Vector(new float[]{m.get(0,3)+ m.get(0,1), m.get(1,3)+ m.get(1,1), m.get(2,3)+ m.get(2,1)}), 0); // Bottom
        this.planes[3] = new Plane(new Vector(new float[]{m.get(0,3)- m.get(0,1), m.get(1,3)- m.get(1,1), m.get(2,3)- m.get(2,1)}), 0); // Top
        this.planes[4] = new Plane(new Vector(new float[]{m.get(0,3)+ m.get(0,2), m.get(1,3)+ m.get(1,2), m.get(2,3)+ m.get(2,2)}), near); // Near
        this.planes[5] = new Plane(new Vector(new float[]{m.get(0,3)- m.get(0,2), m.get(1,3)- m.get(1,2), m.get(2,3)- m.get(2,2)}), far); // Far

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
            float distance = Vector.dotProduct(plane.normal, new Vector((Position3D) center)) - plane.dist;
            //System.out.println("Distance: " + distance + " Radius: " + radius);
            // If the bounding sphere is entirely outside the plane, the shape should be culled
            if (distance > radius) {
                return false;
            }
        }

        // If the bounding sphere is inside all the frustum planes, the shape should not be culled
        return true;
    }


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

        float[] inputUV = face.getTexture().uvMap();
        float[] outputUV = new float[]{};

        for(int i = 0; i < inputVertices.size(); i++){
            GridPosition currentPoint = inputVertices.get(i);
            GridPosition nextPoint = inputVertices.get((i+1) % inputVertices.size());

            float[] currentUV = new float[]{inputUV[i*2], inputUV[i*2+1]};
            float[] nextUV = new float[]{inputUV[((i+1) % inputVertices.size())*2], inputUV[((i+1) % inputVertices.size())*2+1]};
 
            boolean isCurrentInside = plane.isVertexWithinPlane(currentPoint);
            boolean isNextInside = plane.isVertexWithinPlane(nextPoint);

            //System.out.println("Current: " + currentPoint + " is inside: " + isCurrentInside);

            if(isCurrentInside){
                outputVertices.add(currentPoint);
                outputUV = appendFloatArray(outputUV, currentUV);
            }
            if(isCurrentInside != isNextInside){
                float t = plane.calculateT(currentPoint, nextPoint);
                GridPosition intersectionPoint = interpolatePosition(t, currentPoint, nextPoint);
                float[] intersectionUV = interpolateUV(t, currentUV, nextUV);
                if(intersectionPoint != null){
                    outputVertices.add(intersectionPoint);
                    outputUV = appendFloatArray(outputUV, intersectionUV);
                }
            }
        }

        FaceTexture texture = new FaceTexture(face.getTexture().textureKey(), outputUV);
        Face clippedFace = new Face(outputVertices, texture);
        return clippedFace;
    }

    private static float[] appendFloatArray(float[] array1, float[] array2){
        float[] appendedArray = new float[array1.length + array2.length];
        for(int i = 0; i < array1.length; i++){
            appendedArray[i] = array1[i];
        }
        for(int i = 0; i < array2.length; i++){
            appendedArray[i + array1.length] = array2[i];
        }

        return appendedArray;
    }

    private static GridPosition interpolatePosition(float t, GridPosition p1, GridPosition p2){
        float x = p1.x() + t * (p2.x() - p1.x());
        float y = p1.y() + t * (p2.y() - p1.y());
        float z = p1.z() + t * (p2.z() - p1.z());

        return new Position3D(x, y, z);
    }

    /* private static float[] interpolateUV(float t, float[] uv1, float[] uv2, float z1, float z2) {
        System.out.println("z1: " + z1 + " z2: " + z2);
        System.out.println("current:" + uv1[0] + " " + uv1[1] + " next: " + uv2[0] + " " + uv2[1]);
        float z1Inv = 1.0f / z1;
        float z2Inv = 1.0f / z2;
    
        float u1 = uv1[0] * z1Inv;
        float v1 = uv1[1] * z1Inv;
        float u2 = uv2[0] * z2Inv;
        float v2 = uv2[1] * z2Inv;
    
        float interpolatedZInv = z1Inv + t * (z2Inv - z1Inv);
        float interpolatedU = u1 + t * (u2 - u1);
        float interpolatedV = v1 + t * (v2 - v1);
    
        float[] interpolatedUV = new float[2];
        interpolatedUV[0] = interpolatedU / interpolatedZInv;
        interpolatedUV[1] = interpolatedV / interpolatedZInv;
    
        return interpolatedUV;
    } */

    private static float[] interpolateUV(float t, float[] uv1, float[] uv2){
        float[] interpolatedUV = new float[2];
        interpolatedUV[0] = uv1[0] + t * (uv2[0] - uv1[0]);
        interpolatedUV[1] = uv1[1] + t * (uv2[1] - uv1[1]);

        return interpolatedUV;
    }
    
    
}
