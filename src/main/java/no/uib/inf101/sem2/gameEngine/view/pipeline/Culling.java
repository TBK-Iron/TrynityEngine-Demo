package no.uib.inf101.sem2.gameEngine.view.pipeline;

import java.util.ArrayList;
import java.util.Comparator;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

public class Culling {
    /**
     * Performs backface culling on the faces that exists within a list of 3D shapes.
     *
     * @param shapes The list of 3D shapes to be culled.
     * @return A new list of 3D shapes containing only the visible faces after backface culling.
     */
    protected static ArrayList<Shape3D> backfaceCull(ArrayList<Shape3D> shapes){
        ArrayList<Shape3D> notCulledShapes = new ArrayList<>();
        for(Shape3D shape : shapes){
            ArrayList<Face> notCulledFaces = new ArrayList<>();
            for(Face face : shape.getFaces()){
                Vector faceDirection = new Vector((Position3D) face.getPointClosestToOrigin());
                float dotProduct = Vector.dotProduct(face.getNormalVector(), faceDirection);
                if(dotProduct <= 0){
                    notCulledFaces.add(face);
                }
            }
            if(!notCulledFaces.isEmpty()){
                notCulledShapes.add(new Shape3D(notCulledFaces));
            }
        }
        return notCulledShapes;
    }

    /**
     * Performs view-frustum culling on a list of 3D shapes based on the given camera frustum.
     *
     * @param shapes         The list of 3D shapes to be culled.
     * @param cameraFrustum  The camera frustum used for view-frustum culling.
     * @return A new list of 3D shapes containing only the visible shapes after view-frustum culling.
     */
    protected static ArrayList<Shape3D> viewfrustrumCull(ArrayList<Shape3D> shapes, Frustum cameraFrustum){
        ArrayList<Shape3D> notCulledShapes = new ArrayList<>();
        for(Shape3D shape : shapes){
            if(cameraFrustum.isShapeVisible(shape)){
                notCulledShapes.add(shape);
            }
        }
        return notCulledShapes;
    }

    /**
     * Performs occlusion culling on a list of 3D shapes using a depth buffer.
     * 
     * @author ChatGPT
     *
     * @param shapes The list of 3D shapes to be culled.
     * @return A new list of 3D shapes containing only the visible shapes after occlusion culling.
     */

    //This does not work
    protected static ArrayList<Shape3D> occlusionCull(ArrayList<Shape3D> shapes) {
        ArrayList<Shape3D> notCulledShapes = new ArrayList<>();

        // Sort the shapes by their distance to the camera
        shapes.sort(Comparator.comparingDouble(shape -> shape.getDistanceToOrigin()));

        // Create a depth buffer
        DepthBuffer depthBuffer = new DepthBuffer();

        for (Shape3D shape : shapes) {
            boolean isOccluded = true;

            // Check if the shape is occluded using the depth buffer
            for (Face face : shape.getFaces()) {
                if (!depthBuffer.isOccluded(face)) {
                    isOccluded = false;
                    depthBuffer.update(face);
                }
            }

            if (!isOccluded) {
                notCulledShapes.add(shape);
            }
        }

        return notCulledShapes;
    }
}
