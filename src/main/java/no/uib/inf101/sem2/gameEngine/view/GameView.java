package no.uib.inf101.sem2.gameEngine.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.swing.JPanel;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.view.raycaster.Raycaster;

public class GameView extends JPanel{
    
    Raycaster raycaster;
    ViewableGameModel model;

    static final Config config = new DefaultConfig();

    public GameView(ViewableGameModel model){
        this.setPreferredSize(new Dimension(GameView.WIDTH, GameView.HEIGHT));
        this.setBackground(Color.WHITE);
        raycaster = new Raycaster(config);
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }

    private void drawGame(Graphics2D g2){
        ArrayList<Face> culledFaces = raycaster.cull(this.model.getAllFaces());
        ArrayList<Face> clippedFaces = raycaster.clip(culledFaces);
        ArrayList<Face> cameraSpaceFaces = raycaster.cameraTransform(clippedFaces);
        ArrayList<Face> sortedFaces = raycaster.sortFacesByZ(cameraSpaceFaces);
        ArrayList<Face> castedFaces = raycaster.castTo2D(sortedFaces);
        for(Face face : clippedFaces){
            System.out.println(face);
        }
        BufferedImage nextScene = raycaster.getSceneImage(castedFaces);
        g2.drawImage(nextScene, 0, 0, null);
    }
}
