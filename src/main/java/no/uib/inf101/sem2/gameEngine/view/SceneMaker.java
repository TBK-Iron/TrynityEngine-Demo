package no.uib.inf101.sem2.gameEngine.view;

import java.awt.image.BufferedImage;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.gPipeline;

public class SceneMaker{
    
    gPipeline pipeline;
    ViewableEngineModel model;

    final Config config;

    public SceneMaker(ViewableEngineModel model, Config config){
        this.config = config;
        this.model = model;

        pipeline = new gPipeline(this.model, this.config);
    }

    /* @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
        if(true){
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image transparentImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Cursor transparentCursor = toolkit.createCustomCursor(transparentImage, new Point(0, 0), "transparentCursor");
            this.setCursor(transparentCursor);
        }
    } */

    public BufferedImage getNextSceneImage(){
        ArrayList<Shape3D> worldSpaceShapes = pipeline.worldTransform(this.model.getShapes());
        ArrayList<Shape3D> cameraSpaceShapes = pipeline.cameraTransform(worldSpaceShapes);
        ArrayList<Shape3D> notCulledShapes = pipeline.cull(cameraSpaceShapes);
        ArrayList<Face> clippedFaces = pipeline.clip(notCulledShapes);
        ArrayList<Face> projectedFaces = pipeline.projectTransform(clippedFaces);
        ArrayList<Face> NDCTransformedFaces = pipeline.NDCTransform(projectedFaces);
        ArrayList<Face> sortedFaces = pipeline.sortFacesByZ(NDCTransformedFaces);
        ArrayList<Face> castedFaces = pipeline.castTo2D(sortedFaces);

        /* for(Face face : castedFaces){
            System.out.println("face: " + face);
        } */

        BufferedImage nextScene = pipeline.getSceneImage(castedFaces);
        return nextScene;
    }
}
