package no.uib.inf101.sem2.gameEngine.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.swing.JPanel;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.gPipeline;

public class GameView extends JPanel{
    
    gPipeline pipeline;
    ViewableGameModel model;

    static final Config config = new DefaultConfig();

    public GameView(ViewableGameModel model){
        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.WHITE);
        pipeline = new gPipeline(config);
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
        ArrayList<Face> clipSpaceFaces = pipeline.clipTransform(notCulledShapes);
        ArrayList<Face> clippedFaces = pipeline.clip(clipSpaceFaces);
        ArrayList<Face> NCDSpaceFaces = pipeline.NDCTransform(clippedFaces);
        ArrayList<Face> sortedFaces = pipeline.sortFacesByZ(NCDSpaceFaces);
        ArrayList<Face> castedFaces = pipeline.castTo2D(sortedFaces);

        for(Face face : clippedFaces){
            System.out.println(face);
        }
        BufferedImage nextScene = pipeline.getSceneImage(castedFaces);
        g2.drawImage(nextScene, 0, 0, null);
    }
}
