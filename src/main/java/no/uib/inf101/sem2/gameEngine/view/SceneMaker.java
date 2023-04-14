package no.uib.inf101.sem2.gameEngine.view;

import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Map;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.gPipeline;

public class SceneMaker{
    
    private final gPipeline pipeline;
    private final ViewableEngineModel model;
    private final Map<String, BufferedImage> textures;

    private final Config config;

    public SceneMaker(ViewableEngineModel model, Config config, Map<String, BufferedImage> textures){
        this.config = config;
        this.model = model;
        this.textures = textures;

        pipeline = new gPipeline(this.config, this.textures);
    }

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
        //System.out.println("FPS:" + 1000000000/duration + " ms per frame: " + duration/1000000);
        
        return nextScene;
    }
}
