package no.uib.inf101.sem2.gameEngine.view.raycaster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

        double leftRightRot = 0; //Degrees
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

    public ArrayList<Face> clip(ArrayList<Face> faces){
        ArrayList<Face> clippedFaces = new ArrayList<>();
        for(int i = 0; i < faces.size(); i++){
            clippedFaces.add(this.viewport.getFrustum().clipFace(faces.get(i)));
        }
        return clippedFaces;
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
            for(GridPosition point : face.getPoints()){
                
                Vector xyRatios = viewport.getViewProjectionMatrix().projectionMatrixTransform(new Vector(point));

                //System.out.println("X-Y ratios: " + xyRatios);

                double screenX = (this.width/2) * (xyRatios.get(0) + 1);
                double screenY = this.height - (this.height/2) * (xyRatios.get(1) + 1);

                if(point.z() > 0){
                    screenX = this.width - screenX;
                    screenY = this.height - screenY;
                }

                GridPosition finalPos = new Position2D(screenX, screenY);

                castedPoints.add(finalPos);

            }
           
            castedFaces.add(new Face(castedPoints, face.getColor()));

        }

        return castedFaces;
    }

    public ArrayList<Face> cameraTransform(ArrayList<Face> faces){
        ArrayList<Face> transformedFaces = new ArrayList<>();
        for(Face face : faces){
            ArrayList<GridPosition> transformedPoints = new ArrayList<>();
            for(GridPosition point : face.getPoints()){
                double dx = point.x() - this.viewport.getCastPos().x();
                double dy = point.y() - this.viewport.getCastPos().y();
                double dz = point.z() - this.viewport.getCastPos().z();

                Vector ray = new Vector(new double[] {dx, dy, dz});

                Vector cameraSpaceRay = viewport.getViewProjectionMatrix().viewMatrixTransform(ray);
                transformedPoints.add(cameraSpaceRay.getPoint());
            }
            transformedFaces.add(new Face(transformedPoints, face.getColor()));
        }
        return transformedFaces;
    }

    public ArrayList<Face> sortFacesByZ(ArrayList<Face> faces){
        ArrayList<Double> highestZVals = new ArrayList<>();

        for(Face face : faces){
            double highestZ = 0;
            for(GridPosition point : face.getPoints()){
                if(highestZ < point.z()){
                    highestZ = point.z();
                }
            }
            highestZVals.add(highestZ);
        }

        Collections.sort(faces, new Comparator<Face>() {
            @Override
            public int compare(Face f1, Face f2){
                Double value1 = highestZVals.get(faces.indexOf(f1));
                Double value2 = highestZVals.get(faces.indexOf(f2));

                return Double.compare(highestZVals.indexOf(value1), highestZVals.indexOf(value2));
            }
        });

        return faces;
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
