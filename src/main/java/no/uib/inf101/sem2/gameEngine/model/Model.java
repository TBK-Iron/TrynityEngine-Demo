package no.uib.inf101.sem2.gameEngine.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.view.ViewableGameModel;

public class Model implements ViewableGameModel {
    
    ArrayList<Shape3D> shapes;
    ArrayList<Entity> entities;
    
    public Model(){
        shapes = new ArrayList<>();
        entities = new ArrayList<>();
    }
    public void createShape(GridPosition pos, Rotation rotation, String filename){
        File shapeFile = new File(filename);
        shapes.add(new Shape3D(pos, rotation, shapeFile));
    }

    public void createEntity(GridPosition pos, Rotation rotation, String filename){
        File shapeFile = new File(filename);
        entities.add(new Entity(pos, rotation, shapeFile));
    }

    public ArrayList<Face> getAllFaces(){
        ArrayList<Face> faces = new ArrayList<>();

        for(Shape3D shape : shapes){
            for(Face face : shape.getFaces()){
                faces.add(face);
            }
        }
        for(Entity entity : entities){
            for(Face face : entity.getFaces()){
                faces.add(face);
            }
        }

        return faces;
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

}
