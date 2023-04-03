package no.uib.inf101.sem2.gameEngine.model.shape;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;


public class Shape3D implements IShape {
    ArrayList<Face> unchangedFaces;
    ArrayList<Face> faces;
    GridPosition anchoredPos;
    RelativeRotation rotation;

    public Shape3D(ShapeData shapeData){
        this.faces = new ArrayList<>();
        this.unchangedFaces = new ArrayList<>();
        this.anchoredPos = shapeData.position();
        this.rotation = shapeData.rotation();
        this.faces = parseTrymFile(shapeData.file());
        this.unchangedFaces = faces;    
    }

    //Should only be used for when the shape is already in world space.
    public Shape3D(ArrayList<Face> faces){
        this.faces = faces;
        this.unchangedFaces = faces;
        this.anchoredPos = new Position3D(0, 0, 0);
        this.rotation = new RelativeRotation(0, 0);
    }

    private static ArrayList<Face> parseTrymFile(File file){
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(!fileType.equals("trym")){
            throw new UnsupportedOperationException("Files of filetype " + fileType + " are not supported. Only .trym files are parsable");
        }
        try{
            Scanner myReader = new Scanner(file, "UTF-8");
            
            ArrayList<Face> faces = new ArrayList<>();
            while(myReader.hasNextLine()){
                String[] face = myReader.nextLine().split(":");
                if(face.length != 0){
                    String[] points = face[0].split(";"); 
                    //Parse color value
                    String[] sColors = face[2].replace("(", "").replace(")", "").split(",");
                    Color faceColor;
                    if(sColors.length == 3){
                        faceColor = new Color(Integer.parseInt(sColors[0]), Integer.parseInt(sColors[1]), Integer.parseInt(sColors[2]));
                    } else if(sColors.length == 4){
                        faceColor = new Color(Integer.parseInt(sColors[0]), Integer.parseInt(sColors[1]), Integer.parseInt(sColors[2]), Integer.parseInt(sColors[3]));
                    } else {
                        throw new UnsupportedOperationException("Color values must be either 3 or 4 values long");
                    }
                    //Parse points
                    ArrayList<GridPosition> posisions = new ArrayList<>();
                    for(String point : points){
                        float[] dPoint = new float[3];
                        String[] sPoint = point.replace("(", "").replace(")", "").split(",");
                        for(int j = 0; j < 3; j++){
                            dPoint[j] = Float.parseFloat(sPoint[j]);
                        }
                        posisions.add(new Position3D(dPoint[0], dPoint[1], dPoint[2]));
                    }
                    faces.add(new Face(posisions, faceColor));
                }
            }
            myReader.close();
            return faces;
        } catch(FileNotFoundException e){
            System.out.println("file not found: " + e.getMessage());
        }
        return null;
    }

    public RelativeRotation getRotation(){
        return this.rotation;
    }

    public GridPosition getPosition(){
        return this.anchoredPos;
    }

    public BoundingSphere getBoundingSphere(){
        float x = 0;
        float y = 0;
        float z = 0;

        int pointCount = 0;
        for(Face face : this.faces){
            for(GridPosition point : face.getPoints()){
                x += point.x();
                y += point.y();
                z += point.z();
                pointCount++;
            }
        }
        x /= pointCount;
        y /= pointCount;
        z /= pointCount;
        GridPosition center = new Position3D(x, y, z);
        float radius = 0;
        for(Face face : this.faces){
            for(GridPosition point : face.getPoints()){
                float distance = (float) Math.sqrt(Math.pow(point.x() - center.x(), 2) + Math.pow(point.y() - center.y(), 2) + Math.pow(point.z() - center.z(), 2));
                if(distance > radius){
                    radius = distance;
                }
            }
        }
        return new BoundingSphere(center, radius);
    }


    public ArrayList<Face> getFaces(){
        return this.faces;
    }

    public void setFaces(ArrayList<Face> faces){
        this.faces = faces;
    }
}
