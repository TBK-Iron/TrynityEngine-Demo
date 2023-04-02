package no.uib.inf101.sem2.gameEngine.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.swing.JPanel;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.gPipeline;

public class GameView extends JPanel{
    
    gPipeline pipeline;
    ViewableGameModel model;

    final Config config;

    public GameView(ViewableGameModel model, Config config){
        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.WHITE);
        this.config = config;
        pipeline = new gPipeline(model, this.config);
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }

    private void drawGame(Graphics2D g2){
        ArrayList<Shape3D> cameraSpaceShapes = pipeline.cameraTransform(this.model.getShapes());
        ArrayList<Shape3D> notCulledShapes = pipeline.cull(cameraSpaceShapes);
        ArrayList<Face> clippedFaces = pipeline.clip(notCulledShapes);
        ArrayList<Face> projectedFaces = pipeline.projectTransform(clippedFaces);
        ArrayList<Face> NDCTransformedFaces = pipeline.NDCTransform(projectedFaces);
        ArrayList<Face> sortedFaces = pipeline.sortFacesByZ(NDCTransformedFaces);
        ArrayList<Face> castedFaces = pipeline.castTo2D(sortedFaces);

        for(Face face : castedFaces){
            System.out.println("face: " + face);
        }
        BufferedImage nextScene = pipeline.getSceneImage(castedFaces);
        System.out.println(nextScene);
        g2.drawImage(nextScene, 0, 0, null);
    }
}
