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
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.model.shape.Position2D;

public class Raycaster {
    ICamera viewport;
    double fov;
    int width;
    int height;

    public Raycaster(int width, int height){
        this.width = width;
        this.height = height;
        
        this.viewport = new Camera(width, height, Math.PI/2, new Position3D(0, 0, 0), new RelativeRotation(Math.PI/2, Math.PI/2));
    }


    public ArrayList<Face> castTo2D(ArrayList<Face> faces){
        
        ArrayList<Face> castedFaces = new ArrayList<>();
        for(Face face : faces){
            
            if(faceIsRendered(face)){

                ArrayList<GridPosition> castedPoints = new ArrayList<>();
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

                    castedPoints.add(new Position2D(x, y));
                }
                castedFaces.add(new Face(castedPoints, face.getColor()));
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

    public BufferedImage getSceneImage(ArrayList<Face> castedFaces){
        BufferedImage buffer = new BufferedImage(GameView.WIDTH, GameView.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D bufferGraphics = buffer.createGraphics();

        for(Face face : castedFaces){
            //System.out.println(face);
            int[] xVals = new int[face.getPoints().size()];
            int[] yVals = new int[face.getPoints().size()];

            for(int i = 0; i < face.getPoints().size(); i++){
                xVals[i] = (int) face.getPoints().get(i).x();
                yVals[i] = (int) face.getPoints().get(i).y();
            }
            
            bufferGraphics.setColor(face.getColor());
            Polygon face2D = new Polygon(xVals, yVals, xVals.length);
            bufferGraphics.fillPolygon(face2D);
        }

        return buffer;
    }

    public GridPosition getCamPos(){
        return viewport.getCastPos();
    }

}
