package no.uib.inf101.sem2.gameEngine.view.pipeline;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.view.GameView;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Transformation;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.model.shape.Position2D;

public class gPipeline implements IPipeline {
    ICamera viewport;
    float fov;
    int width;
    int height;

    public gPipeline(Config config){
        this.width = config.screenWidth();
        this.height = config.screenHeight();
        this.fov = config.verticalFOV();

        float leftRightRot = 0; //Degrees
        float upDownRot = 0; //Degrees
        
        this.viewport = new Camera(this.width, this.height, config.nearPlane(), config.farPlane(), fov);

        viewport.updateRotation(new RelativeRotation((float) Math.toRadians(upDownRot), (float) Math.toRadians(leftRightRot)));
    }

    public ArrayList<Shape3D> cameraTransform(ArrayList<Shape3D> shapes){
        ArrayList<Shape3D> transformedShapes = new ArrayList<>();
        Transformation cameraTransformation = this.viewport.getViewTranform();

        for(Shape3D shape : shapes){
            ArrayList<Face> transformedFaces = new ArrayList<>();
            for(Face face : shape.getFaces()){
                transformedFaces.add(cameraTransformation.transform(face));
            }
            transformedShapes.add(new Shape3D(transformedFaces));
        }

        return transformedShapes;
    }

    public ArrayList<Shape3D> cull(ArrayList<Shape3D> shapes){
        shapes = backfaceCull(shapes);
        shapes = viewfrustrumCull(shapes, this.viewport.getFrustum());

        //TODO: Implement occlusion culling
        //faces = occlusionCull(shapes);

        return shapes;
    }

    private static ArrayList<Shape3D> backfaceCull(ArrayList<Shape3D> shapes){
        ArrayList<Shape3D> culledShapes = new ArrayList<>();
        for(Shape3D shape : shapes){
            ArrayList<Face> culledFaces = new ArrayList<>();
            for(Face face : shape.getFaces()){
                float dotProduct = Vector.dotProduct(face.getNormalVector(), new Vector(face.get(0)));
                if(dotProduct > 0){
                    culledFaces.add(face);
                }
            }
            culledShapes.add(new Shape3D(culledFaces));
        }
        return culledShapes;
    }

    private static ArrayList<Shape3D> viewfrustrumCull(ArrayList<Shape3D> shapes, Frustum cameraFrustum){
        ArrayList<Shape3D> culledShapes = new ArrayList<>();
        for(Shape3D shape : shapes){
            if(cameraFrustum.isShapeVisible(shape)){
                culledShapes.add(shape);
            }
        }
        return culledShapes;
    }

    public ArrayList<Face> clipTransform(ArrayList<Shape3D> shapes){
        ArrayList<Face> transformedFaces = new ArrayList<>();
        Transformation clipTransformation = this.viewport.getProjectionTransform();

        for(Shape3D shape : shapes){
            for(Face face : shape.getFaces()){
                transformedFaces.add(clipTransformation.transform(face));
            }
        }
        return transformedFaces;
    }

    public ArrayList<Face> clip(ArrayList<Face> faces){
        ArrayList<Face> clippedFaces = new ArrayList<>();
        for(int i = 0; i < faces.size(); i++){
            clippedFaces.add(this.viewport.getFrustum().clipFace(faces.get(i)));
        }
        return clippedFaces;
    }

    public ArrayList<Face> NDCTransform(ArrayList<Face> faces){
        ArrayList<Face> transformedFaces = new ArrayList<>();
        for(Face face : faces){
            ArrayList<GridPosition> transformedPoints = new ArrayList<>();
            for(GridPosition point : face.getPoints()){
                float x = point.x() / point.w();
                float y = point.y() / point.w();
                float z = point.z() / point.w();

                transformedPoints.add(new Position3D(x, y, z));
            }
            transformedFaces.add(new Face(transformedPoints, face.getColor()));
        }
        return transformedFaces;
    }

    public ArrayList<Face> sortFacesByZ(ArrayList<Face> faces){
        ArrayList<Float> highestZVals = new ArrayList<>();

        for(Face face : faces){
            float highestZ = 0;
            for(GridPosition point : face.getPoints()){
                if(highestZ < point.z()){
                    highestZ = point.z();
                }
            }
            highestZVals.add(highestZ);
        }

        Collections.sort(faces, new Comparator<Face>() {
            @Override
            public int compare(Face f1, Face f2){
                float value1 = highestZVals.get(faces.indexOf(f1));
                float value2 = highestZVals.get(faces.indexOf(f2));

                return Float.compare(highestZVals.indexOf(value1), highestZVals.indexOf(value2));
            }
        });

        return faces;
    }

    public ArrayList<Face> castTo2D(ArrayList<Face> faces){
        
        ArrayList<Face> castedFaces = new ArrayList<>();
        for(Face face : faces){
            //System.out.println(face);
                
            ArrayList<GridPosition> castedPoints = new ArrayList<>();
            for(GridPosition point : face.getPoints()){
                float x = ((point.x() + 1) / 2.0f) * this.width;
                float y = ((point.y() + 1) / 2.0f) * this.height;

                castedPoints.add(new Position2D(x, y));
                
            }
           
            castedFaces.add(new Face(castedPoints, face.getColor()));

        }

        return castedFaces;
    }


    public BufferedImage getSceneImage(ArrayList<Face> castedFaces){
        BufferedImage buffer = new BufferedImage(GameView.WIDTH, GameView.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D bufferGraphics = buffer.createGraphics();

        for(Face face : castedFaces){
            //System.out.println(face);
            int[] xVals = new int[face.getPoints().size()];
            int[] yVals = new int[face.getPoints().size()];

            for(int i = 0; i < face.getPoints().size(); i++){
                xVals[i] = (int) face.getPoints().get(i).x();
                yVals[i] = (int) face.getPoints().get(i).y();
            }
            
            bufferGraphics.setColor(face.getColor());
            Polygon face2D = new Polygon(xVals, yVals, xVals.length);
            bufferGraphics.fillPolygon(face2D);
        }

        return buffer;
    }

    public GridPosition getCamPos(){
        return viewport.getCastPos();
    }

}