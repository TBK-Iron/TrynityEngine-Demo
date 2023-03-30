package no.uib.inf101.sem2.gameEngine.view.raycaster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.text.AbstractDocument.LeafElement;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.view.GameView;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Vector;
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
        this.fov = Math.PI/2;
        
        this.viewport = new Camera(width, height, fov, new Position3D(0, 0, 0), new RelativeRotation(0.0, 0.0));
    }


    public ArrayList<Face> castTo2D(ArrayList<Face> faces){
        
        ArrayList<Face> castedFaces = new ArrayList<>();
        for(Face face : faces){
            //System.out.println(face);
            if(faceIsRendered(face)){
                
                ArrayList<GridPosition> castedPoints = new ArrayList<>();
                for(GridPosition point : face.getPoints()){
                    double dx = point.x() - this.viewport.getCastPos().x();
                    double dy = point.y() - this.viewport.getCastPos().y();
                    double dz = point.z() - this.viewport.getCastPos().z();

                    Vector ray = new Vector(new double[] {dx, dy, dz});

                    Matrix rotationMatrix = Matrix.getRotationMatrix(this.viewport.getRotation().getAbsolute().getNegRotation());
                    Vector rotatedRay = rotationMatrix.multiply(ray).normalized().scaledBy(this.viewport.getFocalLength());
                    
                    /*RelativeRotation rayRotation = Vector.getVectorRotation(rotatedRay);

                    double upDown = rayRotation.getUpDown();
                    double leftRight;
                    if(rayRotation.getLeftRight() > Math.PI){
                        leftRight = rayRotation.getLeftRight() - 2*Math.PI;
                    } else {
                        leftRight = rayRotation.getLeftRight();
                    }

                    double horizontalFOV = this.fov * width/height;

                    double xRatio = (leftRight + horizontalFOV/2)/horizontalFOV;
                    double yRatio = 1-(fov + 2*upDown)/(2*fov);*/

                    double xRatio = (rotatedRay.get(0) * 2 * this.height + this.width)/(2*this.width) ;
                    double yRatio = 0.5 - rotatedRay.get(1);

                    GridPosition finalPos = new Position2D(xRatio*this.width, yRatio*this.height);

                    castedPoints.add(finalPos);
                }

                castedFaces.add(new Face(castedPoints, face.getColor()));
            }
        }

        return castedFaces;
    }

    private boolean faceIsRendered(Face face){
        for(GridPosition point : face.getPoints()){
            //System.out.println(point + " = " + viewport.isRendered(point));
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
