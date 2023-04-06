package no.uib.inf101.sem2.gameEngine.model.shape;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;


public class Shape3D {
    ArrayList<Face> faces;
    ArrayList<GridPosition> uniquePoints;
    GridPosition anchoredPos;
    RelativeRotation rotation;

    public Shape3D(ShapeData shapeData){
        this.faces = new ArrayList<>();
        this.anchoredPos = shapeData.position();
        this.rotation = shapeData.rotation();
        this.faces = parseTrymFile(shapeData.file());
    }

    //Should only be used for when the shape is already in world space.
    public Shape3D(ArrayList<Face> faces){
        this.faces = faces;
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
                    //Parse points
                    ArrayList<GridPosition> posisions = new ArrayList<>();
                    for(String point : points){
                        float[] dPoint = new float[3];
                        String[] sPoint = point.replace("(", "").replace(")", "").split(",");
                        if(sPoint.length != 3){
                            myReader.close();
                            throw new UnsupportedOperationException("Points must be 3 values long (x, y, z)");
                        }
                        for(int j = 0; j < 3; j++){
                            dPoint[j] = Float.parseFloat(sPoint[j]);
                        }
                        posisions.add(new Position3D(dPoint[0], dPoint[1], dPoint[2]));
                    }
                    FaceTexture texture;
                    if(face[1].equals("COLOR")){
                        //Parse color value
                        String colorTexture = face[2];
                        float[] uv = new float[posisions.size() * 2];
                        for(int i = 0; i < uv.length; i++){
                            uv[i] = 0.5f;
                        }
                       texture = new FaceTexture(colorTexture, uv);
                    } else if(face[1].equals("TEXTURE")){
                        //Parse texture value
                        String textureKey = face[2];
                        String[] uv = face[3].split(";");
                        float[] fuv = new float[uv.length];
                        for(int i = 0; i < uv.length; i++){
                            fuv[i] = Float.parseFloat(uv[i]);
                        }
                        if(posisions.size() * 2 != fuv.length){
                            myReader.close();
                            throw new UnsupportedOperationException("Texture must have the same number of uv values as points");
                        }
                        texture = new FaceTexture(textureKey, fuv);
                    } else {
                        myReader.close();
                        throw new UnsupportedOperationException("Face must be either COLOR or TEXTURE");
                    }
                    
                    
                    faces.addAll(new Face(posisions, texture).getThreeVertexFaces());
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
    public ArrayList<GridPosition> getUniquePoints(){
        if(this.uniquePoints == null){
            ArrayList<GridPosition> uniquePoints = new ArrayList<>();
            for(Face face : this.faces){
                for(GridPosition point1 : face.getPoints()){
                    boolean addPoint = true;
                    for(GridPosition point2 : uniquePoints){
                        float dx = point1.x() - point2.x();
                        float dy = point1.y() - point2.y();
                        float dz = point1.z() - point2.z();
                        if(dx > 0.000001 || dy > 0.000001 || dz > 0.000001){
                            addPoint = false;
                            break;
                        }
                    }
                    if(addPoint){
                        uniquePoints.add(point1);
                    }
                }
            }
            this.uniquePoints = uniquePoints;
        } 

        return this.uniquePoints;
    }

    public void setFaces(ArrayList<Face> faces){
        this.faces = faces;
    }
}
