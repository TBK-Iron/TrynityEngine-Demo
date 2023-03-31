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
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Frustum;
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

        double leftRightRot = 20; //Degrees
        double upDownRot = 0; //Degrees
        double renderDistance = 10000;
        
        this.viewport = new Camera(0.1*this.width/this.height, 0.1, renderDistance, fov, new Position3D(0, 0, 0), new RelativeRotation(Math.toRadians(upDownRot), Math.toRadians(leftRightRot)));
    }

    public ArrayList<Face> cull(ArrayList<Face> faces){
        faces = backfaceCull(faces, this.viewport.getCastPos());
        faces = viewfrustrumCull(faces, this.viewport.getFrustum());

        //TODO: Implement occlusion culling
        //faces = occlusionCull(faces);

        return faces;
    }


    private static ArrayList<Face> backfaceCull(ArrayList<Face> faces, GridPosition pos){
        ArrayList<Face> culledFaces = new ArrayList<>();
        for(Face face : faces){
            double dotProduct = Vector.dotProduct(face.getNormalVector(), Vector.getVector(face.get(0), pos));
            if(dotProduct > 0){
                culledFaces.add(face);
            }
        }
        return culledFaces;
    }

    private static ArrayList<Face> viewfrustrumCull(ArrayList<Face> faces, Frustum cameraFrustum){
        ArrayList<Face> culledFaces = new ArrayList<>();
        for(Face face : faces){
            if(!cameraFrustum.isFaceCulled(face)){
                culledFaces.add(face);
            }
        }
        return culledFaces;
    }

    public ArrayList<Face> castTo2D(ArrayList<Face> faces){
        
        ArrayList<Face> castedFaces = new ArrayList<>();
        for(Face face : faces){
            //System.out.println(face);
                
            ArrayList<GridPosition> castedPoints = new ArrayList<>();
            boolean faceIsRendered = true;
            for(GridPosition point : face.getPoints()){
                double dx = point.x() - this.viewport.getCastPos().x();
                double dy = point.y() - this.viewport.getCastPos().y();
                double dz = point.z() - this.viewport.getCastPos().z();

                Vector ray = new Vector(new double[] {dx, dy, dz});

                Vector cameraSpaceRay = viewport.getViewProjectionMatrix().viewMatrixTransform(ray);
                Vector xyRatios = viewport.getViewProjectionMatrix().projectionMatrixTransform(cameraSpaceRay);

                //System.out.println("X-Y ratios: " + xyRatios);

                double screenX = (this.width/2) * (xyRatios.get(0) + 1);
                double screenY = this.height - (this.height/2) * (xyRatios.get(1) + 1);

                if(cameraSpaceRay.get(2) > 0){
                    screenX = this.width - screenX;
                    screenY = this.height - screenY;
                }

                GridPosition finalPos = new Position2D(screenX, screenY);

                castedPoints.add(finalPos);

                /*Matrix viewMatrix = Matrix.getRotationMatrix(this.viewport.getRotation().getAbsolute().getNegRotation());
                Vector rotatedRay = viewMatrix.multiply(ray);

                double xRatio;
                double yRatio;

                double Vx = rotatedRay.get(0);
                double Vy = rotatedRay.get(1);
                double Vz = rotatedRay.get(2);

                double cWidth = this.viewport.getWidth();
                double cHeight = this.viewport.getHeight();

                double focalL = this.viewport.getFocalLength();

                if(rotatedRay.get(2) > 0){
                    xRatio = 0.5 * (2*focalL*Vx+cWidth*Vz) / (cWidth* Vz);
                    yRatio = (cHeight*Vz - 2*focalL*Vy)/(2*cHeight*Vz);
                } else {
                    xRatio = (0.5 * (cWidth*Vz - 2*focalL*Vx)) / (cWidth * Vz);
                    yRatio = (cHeight*Vz + 2*focalL*Vy)/(2*cHeight*Vz);
                }
                

                GridPosition finalPos = new Position2D(xRatio*this.width, yRatio*this.height);

                if(!viewport.isRendered(rotatedRay, finalPos)){
                    faceIsRendered = false;
                    break;
                }

                castedPoints.add(finalPos);*/
            }
            if(faceIsRendered){
                castedFaces.add(new Face(castedPoints, face.getColor()));
                //System.out.println(new Face(castedPoints, face.getColor()));
            }
        }

        return castedFaces;
    }

    /*private boolean faceIsRendered(Face face){
        for(GridPosition point : face.getPoints()){
            //System.out.println(point + " = " + viewport.isRendered(point));
            if(viewport.isRendered(point)){
                return true;
            }
        }
        return false;
    }*/

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
