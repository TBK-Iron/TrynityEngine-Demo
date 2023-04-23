package no.uib.inf101.sem2.gameEngine.view.pipeline;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.Camera;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Projection;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.RotateTransform;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Transformation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.TranslateTransform;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.View;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.WorldTransform;
import no.uib.inf101.sem2.gameEngine.view.textures.Rasterizer;

/**
 * gPipeline is an implementation of the IPipeline interface, which is responsible for transforming,
 * culling, clipping, and rasterizing 3D shapes in a 3D scene. The gPipeline class takes care of
 * various transformation operations like world, camera, projection, NDC, and 2D space transformations.
 * It also performs culling and clipping operations to optimize the rendering process.
 */
public class gPipeline implements IPipeline {
    private Config config;
    private Transformation projectTransformation;
    private Frustum frustum;
    private final Rasterizer rasterizer;

    private float usedFOV;
    private float usedWidth;
    private float usedHeight;
    private float usedNearPlane;
    private float usedFarPlane;

    /**
     * Constructs a new gPipeline instance with the specified configuration and texture map.
     * @param config The configuration containing information about the screen dimensions and other rendering settings.
     * @param textures A map of texture names to their corresponding BufferedImage instances.
     */
    public gPipeline(Config config, Map<String, BufferedImage> textures){
        this.config = config;

        initFrustum();
        
        

        this.rasterizer = new Rasterizer(textures, config);
    }

    private void initFrustum(){
        this.projectTransformation = new Projection(this.config.verticalFOV(), ((float) this.config.screenWidth())/ ((float) this.config.screenHeight()), this.config.nearPlane(), this.config.farPlane());
        this.frustum = new Frustum(this.projectTransformation.getMatrix(), this.config.nearPlane(), this.config.farPlane());
    }

    private boolean configChanged(Config config){
        if(this.usedFOV != config.verticalFOV() || this.usedWidth != config.screenWidth() || this.usedHeight != config.screenHeight() || this.usedNearPlane != config.nearPlane() || this.usedFarPlane != config.farPlane()){
            this.usedFOV = config.verticalFOV();
            this.usedWidth = config.screenWidth();
            this.usedHeight = config.screenHeight();
            this.usedNearPlane = config.nearPlane();
            this.usedFarPlane = config.farPlane();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Transforms the given shapes to world space using their position and rotation.
     * @param shapes The list of Shape3D instances to be transformed.
     * @return A new list of Shape3D instances transformed to world space.
     */
    @Override
    public ArrayList<Shape3D> worldTransform(ArrayList<Shape3D> shapes){
        ArrayList<Shape3D> worldSpaceShapes = new ArrayList<>();
        for(Shape3D shape : shapes){
            /* Transformation rTrans = new RotateTransform(shape.getRotation(), false);
            Transformation posTrans = new TranslateTransform(new Vector((Position3D) shape.getPosition()));
            ArrayList<Face> worldSpaceFaces = new ArrayList<>();

            for(Face face : shape.getFaces()){
                Face transformedFace =  posTrans.transform(rTrans.transform(face));
                worldSpaceFaces.add(transformedFace);
            }
            worldSpaceShapes.add(new Shape3D(worldSpaceFaces)); */
            Transformation worldTransform = new WorldTransform(shape.getPosition(), shape.getRotation());

            ArrayList<Face> transformedFaces = new ArrayList<>();
            for(Face face : shape.getFaces()){
                transformedFaces.add(worldTransform.transform(face));
            }
            worldSpaceShapes.add(new Shape3D(transformedFaces));
        }

        return worldSpaceShapes;
    }

    /**
     * Transforms the given shapes to camera space using the camera's position and rotation.
     * @param shapes The list of Shape3D instances to be transformed.
     * @param camera The camera used for the transformation.
     * @return A new list of Shape3D instances transformed to camera space.
     */
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

    /**
     * Culls the given shapes based on backface and view frustum culling techniques.
     * @param shapes The list of Shape3D instances to be culled.
     * @return A new list of Shape3D instances after culling.
     */
    @Override
    public ArrayList<Shape3D> cull(ArrayList<Shape3D> shapes){

        if(configChanged(this.config)){
            initFrustum();
        }

        shapes = Culling.backfaceCull(shapes);
        shapes = Culling.viewfrustrumCull(shapes, this.frustum);

        //TODO: Implement occlusion culling
        //shapes = Culling.occlusionCull(shapes);


        return shapes;
    }

    /**
     * Clips the given shapes based on the view frustum.
     * @param shapes The list of Shape3D instances to be clipped.
     * @return A new list of Face instances after clipping.
     */
    @Override
    public ArrayList<Face> clip(ArrayList<Shape3D> shapes){
        ArrayList<Face> clippedFaces = new ArrayList<>();

        for(Shape3D shape : shapes){
            for(Face face : shape.getFaces()){
                face = this.frustum.clipFace(face);

                if(face.getPoints().size() != 0){
                    clippedFaces.addAll(face.getThreeVertexFaces());
                }
            }
        }
        return clippedFaces;
    }

    /**
     * Transforms the given faces using the projection transformation.
     * @param faces The list of Face instances to be transformed.
     * @return A new list of Face instances after projection transformation.
     */
    @Override
    public ArrayList<Face> projectTransform(ArrayList<Face> faces){
        ArrayList<Face> transformedFaces = new ArrayList<>();

        for(Face face : faces){
            transformedFaces.add(this.projectTransformation.transform(face));
        }
        
        return transformedFaces;
    }

    /**
     * Transforms the given faces to normalized device coordinates (NDC) space.
     * @param faces The list of Face instances to be transformed.
     * @return A new list of Face instances after NDC transformation.
     */
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

    /**
     * Transforms the given faces from NDC space to 2D screen space.
     * @param faces The list of Face instances to be transformed.
     * @return A new list of Face instances after 2D screen space transformation.
     */
    @Override
    public ArrayList<Face> castTo2D(ArrayList<Face> faces){

        ArrayList<Face> castedFaces = new ArrayList<>();
        for(Face face : faces){
                
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

    /**
     * Rasterizes the given faces and returns a BufferedImage containing the rendered result.
     * @param faces The list of Face instances to be rasterized.
     * @return A BufferedImage containing the rasterized faces.
     */
    @Override
    public BufferedImage rastarizeFaces(ArrayList<Face> faces) {

        BufferedImage rastarizedImage = this.rasterizer.rastarize(faces);
        
        return rastarizedImage;
    }

}
