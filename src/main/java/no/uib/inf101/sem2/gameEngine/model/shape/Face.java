package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

public class Face {
    ArrayList<GridPosision> points;
    ArrayList<Color> colors;

    public Face(ArrayList<GridPosision> points, ArrayList<Color> colors){
        this.points = points;
        this.colors = colors;
    }

    public GridPosision get(int i){
        return this.points.get(i);
    }

    public Iterable<GridPosision> getPoints(){
        return this.points;
    }

    public Color getColor(int i){
        return this.colors.get(i);
    } 

    public int size(){
        return this.points.size();
    }

    public void set(int i, GridPosision newPos){
        points.set(i, newPos);
    }
}
