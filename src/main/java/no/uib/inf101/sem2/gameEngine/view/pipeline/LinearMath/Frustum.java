package no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath;

import java.util.ArrayList;
import java.util.List;

import no.uib.inf101.sem2.gameEngine.model.collision.BoundingSphere;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.FaceTexture;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

/**
 * The Frustum class represents the view frustum of the camera. The view frustum is the part of the
 * 3D space that is visible on the screen. It consists of six planes, which include left, right,
 * top, bottom, near, and far planes.
 *
 * The class provides methods for checking if a 3D shape is visible within the view frustum and
 * clipping faces against the frustum planes.
 */
public class Frustum {

    // An array of six planes that make up the view frustum.
    private Plane[] planes;

    /**
     * Constructs a Frustum object using a projection matrix, near, and far clipping distances.
     *
     * @param projectionMatrix the projection matrix used for the frustum
     * @param near the near clipping distance
     * @param far the far clipping distance
     */
    public Frustum(Matrix projectionMatrix, float near, float far){

        planes = new Plane[6];

        extractPlanes(projectionMatrix, near, far);
        normalizePlanes();
    }

    /**
     * Extracts the planes that make up the view frustum using the provided projection matrix and
     * near and far clipping distances.
     *
     * @param m the projection matrix used for the frustum
     * @param near the near clipping distance
     * @param far the far clipping distance
     */
    private void extractPlanes(Matrix m, float near, float far){
        this.planes[0] = new Plane(new Vector(new float[]{m.get(0,3) + m.get(0,0), m.get(1,3) + m.get(1,0), m.get(2,3) + m.get(2,0)}), 0); // Left
        this.planes[1] = new Plane(new Vector(new float[]{m.get(0,3)- m.get(0,0), m.get(1,3)- m.get(1,0), m.get(2,3)- m.get(2,0)}), 0); // Right
        this.planes[2] = new Plane(new Vector(new float[]{m.get(0,3)+ m.get(0,1), m.get(1,3)+ m.get(1,1), m.get(2,3)+ m.get(2,1)}), 0); // Bottom
        this.planes[3] = new Plane(new Vector(new float[]{m.get(0,3)- m.get(0,1), m.get(1,3)- m.get(1,1), m.get(2,3)- m.get(2,1)}), 0); // Top
        this.planes[4] = new Plane(new Vector(new float[]{m.get(0,3)+ m.get(0,2), m.get(1,3)+ m.get(1,2), m.get(2,3)+ m.get(2,2)}), near); // Near
        this.planes[5] = new Plane(new Vector(new float[]{m.get(0,3)- m.get(0,2), m.get(1,3)- m.get(1,2), m.get(2,3)- m.get(2,2)}), far); // Far

    }

    /**
     * Normalizes all planes of the frustum.
     */
    private void normalizePlanes() {
        for(int i = 0; i < planes.length; i++){
            planes[i].normalize();
        }
    }

    /**
     * Checks if the given Shape3D is visible within the view frustum by checking if any part of the shape's
     * bounding sphere is within the frustum planes.
     *
     * @param shape the Shape3D object to check for visibility
     * @return true if the shape is visible within the view frustum, false otherwise
     */
    public boolean isShapeVisible(Shape3D shape) {
        BoundingSphere boundingSphere = shape.getBoundingSphere();

        GridPosition center = boundingSphere.centerPos();
        float radius = boundingSphere.radius();

        for (Plane plane : planes) {
            float distance = Vector.dotProduct(plane.normal, new Vector((Position3D) center)) - plane.dist;
            // If the bounding sphere is entirely outside the plane, the shape should be culled
            if (distance > radius) {
                return false;
            }
        }

        // If the bounding sphere is inside all the frustum planes, the shape should not be culled
        return true;
    }

    /**
     * Clips the given Face against the frustum planes, creating a new Face that is entirely
     * within the frustum.
     *
     * @param face the Face object to be clipped
     * @return a new Face object that is entirely within the view frustum
     */
    public Face clipFace(Face face) {
        Face clippedFace = face;

        for(Plane plane : this.planes){
            clippedFace = clipFaceAgainstPlane(clippedFace, plane);
        }

        return clippedFace;
    }

    /**
     * Clips the given Face against the specified Plane, creating a new Face that is entirely
     * within the plane.
     *
     * @param face the Face object to be clipped
     * @param plane the Plane object against which the face should be clipped
     * @return a new Face object that is entirely within the specified plane
     */
    private Face clipFaceAgainstPlane(Face face, Plane plane){
        List<GridPosition> inputVertices = face.getPoints();
        List<GridPosition> outputVertices = new ArrayList<>();

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

    /**
     * Appends two float arrays into a single array.
     *
     * @param array1 the first float array
     * @param array2 the second float array
     * @return a new float array containing the elements of both input arrays
     */
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

    /**
     * Interpolates the position between two GridPosition objects using the given parameter t.
     *
     * @param t the interpolation parameter
     * @param p1 the first GridPosition object
     * @param p2 the second GridPosition object
     * @return a new GridPosition object representing the interpolated position
     */
    private static GridPosition interpolatePosition(float t, GridPosition p1, GridPosition p2){
        float x = p1.x() + t * (p2.x() - p1.x());
        float y = p1.y() + t * (p2.y() - p1.y());
        float z = p1.z() + t * (p2.z() - p1.z());

        return new Position3D(x, y, z);
    }

    /**
     * Interpolates the UV coordinates between two sets of UV coordinates using the given parameter t.
     *
     * @param t the interpolation parameter
     * @param uv1 the first set of UV coordinates
     * @param uv2 the second set of UV coordinates
     * @return a new float array representing the interpolated UV coordinates
     */
    private static float[] interpolateUV(float t, float[] uv1, float[] uv2){
        float[] interpolatedUV = new float[2];
        interpolatedUV[0] = uv1[0] + t * (uv2[0] - uv1[0]);
        interpolatedUV[1] = uv1[1] + t * (uv2[1] - uv1[1]);

        return interpolatedUV;
    }
    
    
}
