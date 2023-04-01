package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;
import java.util.Iterator;

import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

import java.awt.Color;

public class Face {
    ArrayList<GridPosition> points;
    Color color;
    Vector nVector;

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

    public Vector getNormalVector(){
        if(nVector != null){
            return nVector;
        } else {
            Vector v1 = new Vector(new float[] {points.get(1).x()-points.get(0).x(), points.get(1).y()-points.get(0).y(), points.get(1).z()-points.get(0).z()});
            Vector v2 = new Vector(new float[] {points.get(2).x()-points.get(0).x(), points.get(2).y()-points.get(0).y(), points.get(2).z()-points.get(0).z()});
            return Vector.crossProduct(v1, v2);
        
        }
    }

    public Vector[] getAABB(){
        float[] minVals = new float[] {999999999, 999999999, 999999999};
        float[] maxVals = new float[] {-999999999, -999999999, -999999999};

        for(GridPosition point : this.points){
            //X
            if(point.x() < minVals[0]){
                minVals[0] = point.x();
            }
            if (point.x() > maxVals[0]){
                maxVals[0] = point.x();
            }
            //Y
            if(point.y() < minVals[1]){
                minVals[1] = point.y();
            }
            if (point.y() > maxVals[1]){
                maxVals[1] = point.y();
            }
            //Y
            if(point.z() < minVals[2]){
                minVals[2] = point.z();
            }
            if (point.z() > maxVals[2]){
                maxVals[2] = point.z();
            }
        }
        Vector minVector = new Vector(minVals);
        Vector maxVector = new Vector(maxVals);

        return new Vector[] {minVector, maxVector};
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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Face otherFace = (Face) obj;

        if (points.size() != otherFace.points.size()) {
            return false;
        }

        if(!this.color.equals(otherFace.color)){
            if(this.color != null && otherFace.color != null){
                return false;
            }
        }

        for (int i = 0; i < points.size(); i++) {
            GridPosition thisPoint = points.get(i);
            GridPosition otherPoint = otherFace.points.get(i);

            if (Math.abs(thisPoint.x() - otherPoint.x()) > 0.0001 ||
                Math.abs(thisPoint.y() - otherPoint.y()) > 0.0001 ||
                Math.abs(thisPoint.z() - otherPoint.z()) > 0.0001) {
                return false;
            }
        }

        return true;
    }

    public void removeDuplicatePoints(){
        for(int i = 0; i < this.points.size(); i++){
            GridPosition currentPos = this.points.get(i);
            GridPosition nextPos = this.points.get((i + 1) % (this.points.size()));

            if(Math.abs(currentPos.x() - nextPos.x()) < 0.0001){
                if(Math.abs(currentPos.y() - nextPos.y()) < 0.0001){
                    if(Math.abs(currentPos.z() - nextPos.z()) < 0.0001){
                        this.points.remove(i);
                        i--;
                    }
                }
            }
        }
    }

}
