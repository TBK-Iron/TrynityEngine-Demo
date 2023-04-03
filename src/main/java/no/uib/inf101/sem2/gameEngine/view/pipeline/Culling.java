package no.uib.inf101.sem2.gameEngine.view.pipeline;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

public class Culling {
    public static ArrayList<Shape3D> backfaceCull(ArrayList<Shape3D> shapes){
        ArrayList<Shape3D> notCulledShapes = new ArrayList<>();
        for(Shape3D shape : shapes){
            ArrayList<Face> notCulledFaces = new ArrayList<>();
            for(Face face : shape.getFaces()){
                //Since the shapes are in camera space the view direction is always this
                Vector faceDirection = new Vector(face.getPointClosestToOrigin());
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

    public static ArrayList<Shape3D> viewfrustrumCull(ArrayList<Shape3D> shapes, Frustum cameraFrustum){
        ArrayList<Shape3D> notCulledShapes = new ArrayList<>();
        for(Shape3D shape : shapes){
            if(cameraFrustum.isShapeVisible(shape)){
                notCulledShapes.add(shape);
            }
        }
        return notCulledShapes;
    }

    //TODO: Implement occlusion culling
    public static ArrayList<Shape3D> occlusionCull(ArrayList<Shape3D> shapes){
        return null;
    }
}
