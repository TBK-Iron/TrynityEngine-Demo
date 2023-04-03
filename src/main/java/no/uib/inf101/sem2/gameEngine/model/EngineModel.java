package no.uib.inf101.sem2.gameEngine.model;

import java.io.File;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.controller.ControllableEngineModel;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.ViewableEngineModel;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public class EngineModel implements ViewableEngineModel, ControllableEngineModel {
    
    ArrayList<Shape3D> shapes;
    ArrayList<Entity> entities;
    RelativeRotation cameraRotation;
    GridPosition cameraPos;
    
    public EngineModel(){
        this.shapes = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.cameraPos = new Position3D(0, 0, 0);
        this.cameraRotation = new RelativeRotation(0, 0);
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

    public void addToCameraPosition(GridPosition cameraPos){
        this.cameraPos = cameraPos;
        //System.out.println("Camera position set to: " + cameraPos);
    }

}
