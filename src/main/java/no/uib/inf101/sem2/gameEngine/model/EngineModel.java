package no.uib.inf101.sem2.gameEngine.model;

import java.io.File;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.controller.ControllableEngineModel;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.ViewableEngineModel;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.RotateTransform;

public class EngineModel implements ViewableEngineModel, ControllableEngineModel {
    
    ArrayList<Shape3D> shapes;
    ArrayList<Entity> entities;
    RelativeRotation cameraRotation;
    Vector cameraMoveSpeed;
    GridPosition cameraPos;
    Config config;
    
    public EngineModel(Config config){
        this.shapes = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.cameraPos = new Position3D(0, 0, 0);
        this.cameraMoveSpeed = new Vector(new float[]{0, 0, 0});
        this.cameraRotation = new RelativeRotation(0, 0);
        this.config = config;
    }

    public void createShape(ShapeData shapeData){
        shapes.add(new Shape3D(shapeData));
    }

    public void createEntity(ShapeData shapeData){
        entities.add(new Entity(shapeData));
    }

    public ArrayList<Shape3D> getShapes(){
        ArrayList<Shape3D> allShapes = new ArrayList<>();

        for(Shape3D shape : this.shapes){
            allShapes.add(shape);
        }
        for(Entity entity : this.entities){
            allShapes.add((Shape3D) entity);
        }

        return allShapes;
    }
    public float maxDistFromPointToFace(GridPosition pos1, Face face){
        float maxDistance = 0.0f;
        for(GridPosition pos2 : face.getPoints()){
            float dist = (float) Math.sqrt(Math.pow(pos2.x() - pos1.x(), 2) + Math.pow(pos2.x() - pos1.x(), 2) + Math.pow(pos2.x() - pos1.x(), 2));
            if(dist > maxDistance){
                maxDistance = dist;
            }
        }
        return maxDistance;
    }
    public void updateCameraPosition(){
        if(this.cameraMoveSpeed.magnitude() != 0){
            this.cameraPos = Vector.add(new Vector(this.cameraPos), this.cameraMoveSpeed).getPoint();
        }
        //System.out.println("Camera position set to: " + this.cameraPos);
    }

    public RelativeRotation getCameraRotation(){
        return this.cameraRotation;
    }

    public GridPosition getCameraPosition(){
        return this.cameraPos;
    }

    public void addToCameraRotation(RelativeRotation cameraRotation){
        //System.out.println(cameraRotation);
        this.cameraRotation = this.cameraRotation.add(cameraRotation);
        //System.out.println("Camera rotation set to: " + this.cameraRotation);
    }

    public void setMovementDelta(Vector relativeDelta){
        Vector delta;
        if(relativeDelta.magnitude() != 0){
            Matrix rotationMatrix = new RotateTransform(new RelativeRotation(0, this.cameraRotation.getLeftRight())).getMatrix();
            delta = rotationMatrix.multiply(relativeDelta);
        } else {
            
            delta = relativeDelta;
        }
        
        this.cameraMoveSpeed = delta;
        //System.out.println("Camera movement set to: " + this.cameraMoveSpeed);
    }

}
