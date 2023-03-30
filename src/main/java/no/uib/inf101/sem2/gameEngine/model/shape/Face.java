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

    public ArrayList<GridPosition> getPoints(){
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Face[points: ");
        
        Iterator<GridPosition> iterator = points.iterator();
        while (iterator.hasNext()) {
            GridPosition point = iterator.next();
            sb.append(point);
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        
        sb.append(", color: ");
        sb.append(color.toString());
        sb.append("]");
        
        return sb.toString();
    }



}
