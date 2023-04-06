package no.uib.inf101.sem2.gameEngine.view.pipeline;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.Camera;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position2D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.ViewableEngineModel;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Projection;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.RotateTransform;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Transformation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.TranslateTransform;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.View;

public class gPipeline implements IPipeline {
    Config config;
    Transformation projectTransformation;
    Frustum frustum;

    public gPipeline(ViewableEngineModel model, Config config){
        this.config = config;

        this.projectTransformation = new Projection(config.verticalFOV(), ((float) config.screenWidth())/ ((float) config.screenHeight()), config.nearPlane(), config.farPlane());
        this.frustum = new Frustum(this.projectTransformation.getMatrix(), this.config.nearPlane(), this.config.farPlane());
    }

    @Override
    public ArrayList<Shape3D> worldTransform(ArrayList<Shape3D> shapes){
        ArrayList<Shape3D> worldSpaceShapes = new ArrayList<>();
        for(Shape3D shape : shapes){
            Transformation rTrans = new RotateTransform(shape.getRotation().getNegRotation());
            Transformation posTrans = new TranslateTransform(new Vector(shape.getPosition()));
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
        //faces = occlusionCull(shapes);

        return shapes;
    }

    @Override
    public ArrayList<Face> clip(ArrayList<Shape3D> shapes){
        ArrayList<Face> clippedFaces = new ArrayList<>();
        for(Shape3D shape : shapes)
            for(Face face : shape.getFaces()){
                clippedFaces.add(this.frustum.clipFace(face));
            }
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
                float z = point.z() / point.w();

                transformedPoints.add(new Position3D(x, y, z));
            }
            transformedFaces.add(new Face(transformedPoints, face.getTexture()));
        }
        return transformedFaces;
    }

    @Override
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

    @Override
    public ArrayList<Face> castTo2D(ArrayList<Face> faces){
        
        ArrayList<Face> castedFaces = new ArrayList<>();
        for(Face face : faces){
            //System.out.println(face);
                
            ArrayList<GridPosition> castedPoints = new ArrayList<>();
            for(GridPosition point : face.getPoints()){
                float x = ((point.x() + 1) / 2.0f) * this.config.screenWidth();
                float y = ((point.y() + 1) / 2.0f) * this.config.screenHeight();

                castedPoints.add(new Position2D(x, y));
                
            }
           
            castedFaces.add(new Face(castedPoints, face.getTexture()));

        }

        return castedFaces;
    }

    @Override
    public BufferedImage rastarizeFaces(ArrayList<Face> faces, Map<String, BufferedImage> textures) {
        int[] rastarizedFace = new int[this.config.screenWidth() * this.config.screenHeight()];

        for(Face face : faces){
            int[] textureData;
            int textureWidth;
            int textureHeight;
            //Check if face is textured or just has a color
            if(face.getTexture().textureKey().startsWith("#")){
                Color color = Color.decode(face.getTexture().textureKey().trim());

                textureData = new int[]{color.getRGB()};
                textureWidth = 1;
                textureHeight = 1;
            } else {
                BufferedImage texture = textures.get(face.getTexture().textureKey());
                textureData = texture.getRGB(0, 0, texture.getWidth(), texture.getHeight(), null, 0, 0);
                textureWidth = texture.getWidth();
                textureHeight = texture.getHeight();
            }

            float[] vertices = new float[face.getPoints().size() * 2];
            for(int i = 0; i < face.getPoints().size(); i++){
                vertices[i * 2] = face.getPoints().get(i).x();
                vertices[i * 2 + 1] = face.getPoints().get(i).y();
            }

            RasterizerKernel kernel = new RasterizerKernel(vertices, face.getTexture().uvMap(), textureData, rastarizedFace, textureWidth, textureHeight, this.config.screenWidth(), this.config.screenHeight());
            int localSize = 256;
            int globalSize = (int) Math.ceil((double) (this.config.screenWidth() * this.config.screenHeight()) / localSize) * localSize;
            System.out.println("Global size: " + globalSize + " Local size: " + localSize);
            System.out.println("Vertices: " + vertices.length + " UVs: " + face.getTexture().uvMap().length + " Texture data: " + textureData.length + " Rastarized face: " + rastarizedFace.length + "output: " + rastarizedFace.length);
            
            kernel.execute(globalSize, localSize);
            kernel.dispose();
            
        }
        BufferedImage sceneImage = new BufferedImage(this.config.screenWidth(), this.config.screenHeight(), BufferedImage.TYPE_INT_ARGB);
        sceneImage.setRGB(0, 0, this.config.screenWidth(), this.config.screenHeight(), rastarizedFace, 0, 0);
        return sceneImage;
    }


    /* public BufferedImage getSceneImage(ArrayList<Face> castedFaces){
        BufferedImage buffer = new BufferedImage(this.config.screenWidth(), this.config.screenHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D bufferGraphics = buffer.createGraphics();

        for(Face face : castedFaces){
            //System.out.println(face);
            int[] xVals = new int[face.getPoints().size()];
            int[] yVals = new int[face.getPoints().size()];

            for(int i = 0; i < face.getPoints().size(); i++){
                xVals[i] = (int) face.getPoints().get(i).x();
                yVals[i] = (int) face.getPoints().get(i).y();
            }
            
            bufferGraphics.setColor(face.getTexture());
            Polygon face2D = new Polygon(xVals, yVals, xVals.length);
            bufferGraphics.fillPolygon(face2D);
        }

        return buffer;
    } */

}
