package no.uib.inf101.sem2.gameEngine.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import no.uib.inf101.sem2.gameEngine.grid3D.GridPosision;
import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.view.ViewableGameModel;

public class Model implements ViewableGameModel {
    
    ArrayList<Shape3D> shapes;
    ArrayList<Entity> entities;
    
    public Model(){

    }
    public void createShape(GridPosision pos, Rotation rotation, String filename){
        File shapeFile = new File(filename);
        shapes.add(new Shape3D(pos, rotation, shapeFile));
    }

    public void createEntity(GridPosision pos, Rotation rotation, String filename){
        File shapeFile = new File(filename);
        entities.add(new Entity(pos, rotation, shapeFile));
    }

    public ArrayList<Face> getSortedFaces(GridPosision viewPos){
        ArrayList<Face> faces = new ArrayList<>();
        ArrayList<Double> distances = new ArrayList<>();

        for(Shape3D shape : shapes){
            for(Face face : shape.getFaces()){
                faces.add(face);
                distances.add(maxDistFromPointToFace(viewPos, face));
            }
        }
        for(Entity entity : entities){
            for(Face face : entity.getFaces()){
                faces.add(face);
                distances.add(maxDistFromPointToFace(viewPos, face));
            }
        }
        Collections.sort(faces, new Comparator<Face>() {
            @Override
            public int compare(Face f1, Face f2){
                Double value1 = distances.get(faces.indexOf(f1));
                Double value2 = distances.get(faces.indexOf(f2));

                return Double.compare(distances.indexOf(value1), distances.indexOf(value2));
            }
        });

        return faces;
    }
    public double maxDistFromPointToFace(GridPosision pos1, Face face){
        Double maxDistance = 0.0;
        for(GridPosision pos2 : face.getPoints()){
            Double dist = Math.sqrt(Math.pow(pos2.x() - pos1.x(), 2) + Math.pow(pos2.x() - pos1.x(), 2) + Math.pow(pos2.x() - pos1.x(), 2));
            if(dist > maxDistance){
                maxDistance = dist;
            }
        }
        return maxDistance;
    }

}
