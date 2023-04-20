package no.uib.inf101.sem2.gameEngine.view;

import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Map;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.gPipeline;

/**
 * SceneMaker is responsible for managing the rendering process by coordinating the gPipeline
 * and the ViewableEngineModel. It takes care of the entire rendering pipeline from world space
 * transformation to rasterization, generating a BufferedImage for each frame in the scene.
 */
public class SceneMaker{
    
    private final gPipeline pipeline;
    private final ViewableEngineModel model;
    private final Map<String, BufferedImage> textures;

    private final Config config;

    /**
     * Constructs a new SceneMaker instance with the specified model, configuration, and texture map.
     * @param model The ViewableEngineModel instance that provides the necessary information for rendering.
     * @param config The configuration containing information about the screen dimensions and other rendering settings.
     * @param textures A map of texture names to their corresponding BufferedImage instances.
     */
    public SceneMaker(ViewableEngineModel model, Config config, Map<String, BufferedImage> textures){
        this.config = config;
        this.model = model;
        this.textures = textures;

        pipeline = new gPipeline(this.config, this.textures);
    }

    /**
     * Generates the next scene image by running the entire rendering pipeline.
     * @return A BufferedImage representing the next frame in the scene.
     */
    public BufferedImage getNextSceneImage(){
        long startTime = System.nanoTime();
        ArrayList<Shape3D> worldSpaceShapes = pipeline.worldTransform(this.model.getRenderShapes());
        ArrayList<Shape3D> cameraSpaceShapes = pipeline.cameraTransform(worldSpaceShapes, this.model.getCamera());
        ArrayList<Shape3D> notCulledShapes = pipeline.cull(cameraSpaceShapes);
        ArrayList<Face> clippedFaces = pipeline.clip(notCulledShapes);
        ArrayList<Face> projectedFaces = pipeline.projectTransform(clippedFaces);
        ArrayList<Face> NDCTransformedFaces = pipeline.NDCTransform(projectedFaces);
        ArrayList<Face> castedFaces = pipeline.castTo2D(NDCTransformedFaces);

        BufferedImage nextScene = pipeline.rastarizeFaces(castedFaces);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        //System.out.println("FPS:" + 1000000000/duration + " ms per frame: " + (float)duration/1000000);
        
        return nextScene;
    }
}
