package no.uib.inf101.sem2.gameEngine.view.pipeline;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import com.aparapi.Range;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.Camera;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position2D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Projection;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.RotateTransform;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Transformation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.TranslateTransform;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.View;
import no.uib.inf101.sem2.gameEngine.view.textures.Rasterizer;
import no.uib.inf101.sem2.gameEngine.view.textures.RasterizerKernel;

public class gPipeline implements IPipeline {
    private Config config;
    private Transformation projectTransformation;
    private Frustum frustum;
    private final Rasterizer rasterizer;

    public gPipeline(Config config, Map<String, BufferedImage> textures){
        this.config = config;

        this.projectTransformation = new Projection(config.verticalFOV(), ((float) config.screenWidth())/ ((float) config.screenHeight()), config.nearPlane(), config.farPlane());
        this.frustum = new Frustum(this.projectTransformation.getMatrix(), this.config.nearPlane(), this.config.farPlane());

        this.rasterizer = new Rasterizer(textures, config);
    }

    @Override
    public ArrayList<Shape3D> worldTransform(ArrayList<Shape3D> shapes){
        ArrayList<Shape3D> worldSpaceShapes = new ArrayList<>();
        for(Shape3D shape : shapes){
            Transformation rTrans = new RotateTransform(shape.getRotation());
            Transformation posTrans = new TranslateTransform(new Vector((Position3D) shape.getPosition()));
            ArrayList<Face> worldSpaceFaces = new ArrayList<>();

            for(Face face : shape.getFaces()){
                Face transformedFace =  posTrans.transform(rTrans.transform(face));
                worldSpaceFaces.add(transformedFace);
            }
            worldSpaceShapes.add(new Shape3D(worldSpaceFaces));
        }

        return worldSpaceShapes;
    }

    @Override
    public ArrayList<Shape3D> cameraTransform(ArrayList<Shape3D> shapes, Camera camera){

        Transformation viewTransform = new View(camera.getRotation(), camera.getPos());
        ArrayList<Shape3D> transformedShapes = new ArrayList<>();

        for(Shape3D shape : shapes){
            ArrayList<Face> transformedFaces = new ArrayList<>();
            for(Face face : shape.getFaces()){
                transformedFaces.add(viewTransform.transform(face));
            }
            transformedShapes.add(new Shape3D(transformedFaces));
        }

        return transformedShapes;
    }

    @Override
    public ArrayList<Shape3D> cull(ArrayList<Shape3D> shapes){

        shapes = Culling.backfaceCull(shapes);
        shapes = Culling.viewfrustrumCull(shapes, this.frustum);
        //System.out.print("Before Occlution culling: " + shapes.size() + " shapes,  ");
        //shapes = Culling.occlusionCull(shapes);
        //System.out.println("After Occlution culling: " + shapes.size());

        return shapes;
    }

    @Override
    public ArrayList<Face> clip(ArrayList<Shape3D> shapes){

       

        ArrayList<Face> clippedFaces = new ArrayList<>();
        for(Shape3D shape : shapes){
            for(Face face : shape.getFaces()){
                /* System.out.println("Unclipped face:");
                for(GridPosition point : face.getPoints()){
                    System.out.println("x: " + point.x() + " y: " + point.y() + " z: " + point.z());
                }
                float[] uv = face.getTexture().uvMap();
                for(int i = 0; i < uv.length/2; i++){
                    System.out.print("u" + i + ": " + uv[i*2] + " v" + i + ": " + uv[i*2+1] + " ");
                }
                System.out.println(); */
                face = this.frustum.clipFace(face);

                /* System.out.println("Clipped face:");
                for(GridPosition point : face.getPoints()){
                    System.out.println("x: " + point.x() + " y: " + point.y() + " z: " + point.z());
                }
                uv = face.getTexture().uvMap();
                for(int i = 0; i < uv.length/2; i++){
                    System.out.print("u" + i + ": " + uv[i*2] + " v" + i + ": " + uv[i*2+1] + " ");
                }
                System.out.println(); */


                if(face.getPoints().size() != 0){
                    clippedFaces.addAll(face.getThreeVertexFaces());
                }
            }
        }


        /* System.out.println("Clipped split faces:");
        for(Face face : clippedFaces){
            for(GridPosition point : face.getPoints()){
                System.out.println("x: " + point.x() + " y: " + point.y() + " z: " + point.z());
            }
            float[] uv = face.getTexture().uvMap();
            System.out.println("u1: " + uv[0] + " v1: " + uv[1] + " u2: " + uv[2] + " v2: " + uv[3] + " u3: " + uv[4] + " v3: " + uv[5] + "\n");
        } */
        return clippedFaces;
    }

    @Override
    public ArrayList<Face> projectTransform(ArrayList<Face> faces){
        ArrayList<Face> transformedFaces = new ArrayList<>();

        for(Face face : faces){
            transformedFaces.add(this.projectTransformation.transform(face));
        }
        
        return transformedFaces;
    }

    @Override
    public ArrayList<Face> NDCTransform(ArrayList<Face> faces){
        ArrayList<Face> transformedFaces = new ArrayList<>();
        for(Face face : faces){
            ArrayList<GridPosition> transformedPoints = new ArrayList<>();
            for(GridPosition point : face.getPoints()){
                float x = point.x() / point.w();
                float y = point.y() / point.w();

                transformedPoints.add(new Position3D(x, y, point.z()));
            }
            transformedFaces.add(new Face(transformedPoints, face.getTexture()));
        }
        return transformedFaces;
    }

    /* @Override
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
 */
    @Override
    public ArrayList<Face> castTo2D(ArrayList<Face> faces){

        ArrayList<Face> castedFaces = new ArrayList<>();
        for(Face face : faces){
            //System.out.println(face);
                
            ArrayList<GridPosition> castedPoints = new ArrayList<>();
            for(GridPosition point : face.getPoints()){
                float x = ((point.x() + 1) / 2.0f) * this.config.screenWidth();
                float y = ((point.y() + 1) / 2.0f) * this.config.screenHeight();

                if(x < 0 || x > this.config.screenWidth() || y < 0 || y > this.config.screenHeight()){
                    System.out.println("Out of bounds: " + x + ", " + y);
                }
                //Keeiping the z value for interpolating the z buffer values.
                castedPoints.add(new Position3D(x, y, point.z()));
                
            }
           
            castedFaces.add(new Face(castedPoints, face.getTexture()));

        }

        return castedFaces;
    }

    @Override
    public BufferedImage rastarizeFaces(ArrayList<Face> faces) {

        BufferedImage rastarizedImage = this.rasterizer.rastarize(faces);
        
        return rastarizedImage;
    }

}
