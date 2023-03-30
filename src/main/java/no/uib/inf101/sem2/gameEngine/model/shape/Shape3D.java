package no.uib.inf101.sem2.gameEngine.model.shape;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import no.uib.inf101.sem2.gameEngine.grid3D.Grid;
import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;

public class Shape3D implements IShape {
    ArrayList<Face> unchangedFaces;
    ArrayList<Face> faces;
    GridPosition anchoredPos;
    Rotation rotation;

    public Shape3D(GridPosition pos, Rotation rotation,File file){
        this.faces = new ArrayList<>();
        this.unchangedFaces = new ArrayList<>();
        this.anchoredPos = pos;
        this.rotation = rotation;
        this.faces = parseTrymFile(file);
        this.unchangedFaces = faces;
        updateRotation();
        updatePosition();
        
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
                    Color faceColor = new Color(Integer.parseInt(sColors[0]), Integer.parseInt(sColors[1]), Integer.parseInt(sColors[2]));

                    //Parse points
                    ArrayList<GridPosition> posisions = new ArrayList<>();
                    for(String point : points){
                        double[] dPoint = {0.0, 0.0, 0.0};
                        String[] sPoint = point.replace("(", "").replace(")", "").split(",");
                        for(int j = 0; j < 3; j++){
                            dPoint[j] = Double.parseDouble(sPoint[j]);
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




    protected void updatePosition(){
        for(int i = 0; i < this.faces.size(); i++){
            for(int j = 0; j < this.faces.get(i).size(); j++){
                double x = unchangedFaces.get(i).get(j).x() + anchoredPos.x();
                double y = unchangedFaces.get(i).get(j).y() + anchoredPos.y();
                double z = unchangedFaces.get(i).get(j).z() + anchoredPos.z();

                this.faces.get(i).set(j, new Position3D(x, y, z));
            }
        }
    }

    protected void updateRotation(){
        double[] cosVals = {Math.cos(this.rotation.getxAxis()), Math.cos(this.rotation.getyAxis()), Math.cos(this.rotation.getzAxis())};
        double[] sinVals = {Math.sin(this.rotation.getxAxis()), Math.sin(this.rotation.getyAxis()), Math.sin(this.rotation.getzAxis())};
        for(int i = 0; i < this.faces.size(); i++){
            for(int j = 0; j < this.faces.get(i).size(); j++){
                GridPosition point = this.unchangedFaces.get(i).get(j);
                Double[] newPoint = new Double[3];

                //Rotation z-axis
                newPoint[0] = point.x()*cosVals[2] - point.y()*sinVals[2];
                newPoint[1] = point.x()*sinVals[2] + point.y()*cosVals[2];
                //Rotation y-axis
                double helper = newPoint[0];
                newPoint[0] = newPoint[0]*cosVals[1] - point.z()*sinVals[1];
                newPoint[2] = helper*sinVals[1] + point.z()*cosVals[1];
                //Rotation x-axis
                helper = newPoint[2];
                newPoint[2] = newPoint[2]*cosVals[0] - newPoint[1]*sinVals[0];
                newPoint[1] = helper*sinVals[0] + newPoint[1]*cosVals[0];

                this.faces.get(i).set(j, new Position3D(newPoint[0], newPoint[1], newPoint[2]));
            }
        }
    }

    public GridPosition getPos(){
        return this.anchoredPos;
    }

    public ArrayList<Face> getFaces(){
        return this.faces;
    }
}
