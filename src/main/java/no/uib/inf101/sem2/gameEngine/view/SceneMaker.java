package no.uib.inf101.sem2.gameEngine.view;

import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Map;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.gPipeline;

public class SceneMaker{
    
    gPipeline pipeline;
    ViewableEngineModel model;
    Map<String, BufferedImage> textures;

    final Config config;

    public SceneMaker(ViewableEngineModel model, Config config, Map<String, BufferedImage> textures){
        this.config = config;
        this.model = model;
        this.textures = textures;

        pipeline = new gPipeline(this.model, this.config);
    }

    public BufferedImage getNextSceneImage(){
        ArrayList<Shape3D> worldSpaceShapes = pipeline.worldTransform(this.model.getRenderShapes());
        ArrayList<Shape3D> cameraSpaceShapes = pipeline.cameraTransform(worldSpaceShapes, this.model.getCamera());
        ArrayList<Shape3D> notCulledShapes = pipeline.cull(cameraSpaceShapes);
        ArrayList<Face> clippedFaces = pipeline.clip(notCulledShapes);
        ArrayList<Face> projectedFaces = pipeline.projectTransform(clippedFaces);
        ArrayList<Face> NDCTransformedFaces = pipeline.NDCTransform(projectedFaces);
        ArrayList<Face> sortedFaces = pipeline.sortFacesByZ(NDCTransformedFaces);
        ArrayList<Face> castedFaces = pipeline.castTo2D(sortedFaces);

        /* for(Face face : castedFaces){
            System.out.println("face: " + face);
        } */

        BufferedImage nextScene = pipeline.rastarizeFaces(castedFaces, this.textures);
        return nextScene;
    }
}
