package no.uib.inf101.sem2.gameEngine.view.raycaster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
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
        this.viewport = new Camera(width, height, Math.PI/2, new GridPosition(0, 0, 0), new RelativeRotation(0.0, 0.0));
    }


    public ArrayList<ArrayList<int[]>> castTo2D(ArrayList<Face> sortedFaces){
        ArrayList<ArrayList<int[]>> castedFaces = new ArrayList<>();
        for(Face face : sortedFaces){
            if(faceIsRendered(face)){

                ArrayList<int[]> castedFace = new ArrayList<>();
                for(GridPosition point : face.getPoints()){
                    double dx = point.x() - this.viewport.getCastPos().x();
                    double dy = point.y() - this.viewport.getCastPos().y();
                    double dz = point.z() - this.viewport.getCastPos().z();

                    RelativeRotation rot = Camera.getVectorRotation(new double[] {dx, dy, dz});

                    RelativeRotation upperLeft = this.viewport.getCornerRotations().upperLeft();
                    RelativeRotation lowerRight = this.viewport.getCornerRotations().lowerRight();

                    double xRatio = (rot.getLeftRight() - upperLeft.getLeftRight())/(lowerRight.getLeftRight() - upperLeft.getLeftRight());
                    double yRatio = (rot.getUpDown() - upperLeft.getUpDown())/(lowerRight.getUpDown() - upperLeft.getUpDown());


                    int x = (int) (xRatio * this.width);
                    int y = (int) (yRatio * this.height);

                    castedFace.add(new int[] {x, y});
                }
                castedFaces.add(castedFace);
            }
        }

        return castedFaces;
    }

    private boolean faceIsRendered(Face face){
        for(GridPosition point : face.getPoints()){
            if(viewport.isRendered(point)){
                return true;
            }
        }
        return false;
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

            Polygon face2D = new Polygon(xVals, yVals, xVals.length);
            bufferGraphics.fillPolygon(face2D);
        }

        return buffer;
    }

}
