package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

public class Face {
    ArrayList<GridPosition> points;
    Color color;

    public Face(ArrayList<GridPosition> points, Color color){
        this.points = points;
        this.color = color;
    }

    public GridPosition get(int i){
        return this.points.get(i);
    }

    public Iterable<GridPosition> getPoints(){
        return this.points;
    }

    public Color getColor(){
        return this.color;
    } 

    public int size(){
        return this.points.size();
    }

    public void set(int i, GridPosition newPos){
        points.set(i, newPos);
    }
}
