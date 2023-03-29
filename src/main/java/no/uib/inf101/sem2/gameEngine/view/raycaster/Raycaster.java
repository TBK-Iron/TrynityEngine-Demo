package no.uib.inf101.sem2.gameEngine.view.raycaster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosision;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.view.GameView;

public class Raycaster {
    ICamera viewport;
    double fov;
    int width;
    int height;

    public Raycaster(int width, int height){
        this.width = width;
        this.height = height;
        this.viewport = new Camera(width, height, Math.PI/2, new GridPosision(0, 0, 0), new RelativeRotation(0.0, 0.0));
    }


    public ArrayList<ArrayList<Double[]>> castTo2D(ArrayList<Face> sortedFaces){
        ArrayList<ArrayList<Double[]>> castedFaces = new ArrayList<>();
    }

    public BufferedImage getSceneImage(ArrayList<ArrayList<int[]>> castedFaces){
        BufferedImage buffer = new BufferedImage(GameView.WIDTH, GameView.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D bufferGraphics = buffer.createGraphics();

        for(ArrayList<int[]> face : castedFaces){
            int[] xVals = new int[face.size()];
            int[] yVals = new int[face.size()];

            for(int i = 0; i < face.size(); i++){
                xVals[i] = face.get(i)[0];
                yVals[i] = face.get(i)[1];
            }

            Polygon Face2D = new Polygon(xVals, yVals, xVals.length);
            bufferGraphics.fillPolygon(Face2D);
        }

        return buffer;
    }

}
