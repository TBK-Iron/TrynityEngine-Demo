package no.uib.inf101.sem2.gameEngine.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import no.uib.inf101.sem2.gameEngine.grid3D.Grid;
import no.uib.inf101.sem2.gameEngine.grid3D.GridPosision;
import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;

public class Shape3D {
    ArrayList<ArrayList<GridPosision>> unchangedFaces;
    ArrayList<ArrayList<GridPosision>> faces;
    GridPosision anchoredPos;
    Rotation rotation;

    public Shape3D(GridPosision pos, Rotation rotation,File file){
        this.faces = new ArrayList<>();
        this.unchangedFaces = new ArrayList<>();
        this.anchoredPos = pos;
        this.rotation = rotation;
        readFile(file);
        updateRotation();
        updatePosision();
        
    }
    private void readFile(File file){
        try{
            Scanner myReader = new Scanner(file, "UTF-8");
            int i = 0;
            while(myReader.hasNextLine()){
                String face = myReader.nextLine();
                if(face != ""){
                    String[] points = face.split(";"); 
                    faces.add(new ArrayList<>());
                    for(String point : points){
                        Double[] dPoint = {0.0, 0.0, 0.0};
                        String[] sPoint = point.replace("(", "").replace(")", "").split(",");
                        for(int j = 0; j < 3; j++){
                            dPoint[j] = Double.parseDouble(sPoint[j]);
                        }

                        faces.get(i).add(new GridPosision(dPoint[0], dPoint[1], dPoint[2]));
                        unchangedFaces.get(i).add(new GridPosision(dPoint[0], dPoint[1], dPoint[2]));
                    }
                }
                i++;
            }
            myReader.close();
        } catch(FileNotFoundException e){
            System.out.println("file not found: " + e.getMessage());
        }
    }

    protected void updatePosision(){
        for(int i = 0; i < this.faces.size(); i++){
            for(int j = 0; j < this.faces.get(i).size(); j++){
                double x = unchangedFaces.get(i).get(j).x() + anchoredPos.x();
                double y = unchangedFaces.get(i).get(j).y() + anchoredPos.y();
                double z = unchangedFaces.get(i).get(j).z() + anchoredPos.z();

                this.faces.get(i).set(j, new GridPosision(x, y, z));
            }
        }
    }

    protected void updateRotation(){
        double[] cosVals = {Math.cos(this.rotation.getxAxis()), Math.cos(this.rotation.getyAxis()), Math.cos(this.rotation.getzAxis())};
        double[] sinVals = {Math.sin(this.rotation.getxAxis()), Math.sin(this.rotation.getyAxis()), Math.sin(this.rotation.getzAxis())};
        for(int i = 0; i < this.faces.size(); i++){
            for(int j = 0; j < this.faces.get(i).size(); j++){
                GridPosision point = this.unchangedFaces.get(i).get(j);
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

                this.faces.get(i).set(j, new GridPosision(newPoint[0], newPoint[1], newPoint[2]));
            }
        }
    }

    public GridPosision getPos(){
        return this.anchoredPos;
    }

    public ArrayList<ArrayList<GridPosision>> getShape(){
        return this.faces;
    }
}
