package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;
import java.util.Iterator;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position2D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

public class Face {
    ArrayList<GridPosition> points;
    Vector nVector;
    FaceTexture texture;

    public Face(ArrayList<GridPosition> points, FaceTexture texture){
        if(points.size() * 2 != texture.uvMap().length){
            throw new IllegalArgumentException("Each vertex must have a corresponding UV coordinate");
        }

        this.points = points;
        this.texture = texture;
    }

    public GridPosition get(int i){
        return this.points.get(i);
    }

    public ArrayList<GridPosition> getPoints(){
        return this.points;
    }

    public FaceTexture getTexture(){
        return this.texture;
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

    public Vector[] getAABB_xy(){
        float[] minVals = new float[] {999999999, 999999999};
        float[] maxVals = new float[] {-999999999, -999999999};

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
        }
        Vector minVector = new Vector(minVals);
        Vector maxVector = new Vector(maxVals);

        return new Vector[] {minVector, maxVector};
    }

    /* public Vector[] getAABB_xyz(){
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
            //Z
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
    } */


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

    public GridPosition getPointClosestToOrigin(){
        GridPosition closestPoint = this.points.get(0);
        for(int i = 1; i < this.points.size(); i++){
            GridPosition currentPoint = this.points.get(i);
            Vector v = Vector.getVector(closestPoint, currentPoint);
          
            float t = -Vector.dotProduct(v, new Vector((Position3D) closestPoint))/((float) Math.pow(v.get(0), 2) + (float) Math.pow(v.get(1), 2)+ (float) Math.pow(v.get(2), 2));
            
            if(t < 0){
                //closestPoint = closestPoint;
            } else if (t > 1){
                closestPoint = currentPoint;
            } else {
                closestPoint = new Position3D(v.get(0)*t + closestPoint.x(), v.get(1)*t + closestPoint.y(), v.get(2)*t + closestPoint.z());
            }
        }
        return closestPoint;
    }

    public ArrayList<Face> getThreeVertexFaces(){
        ArrayList<Face> faces = new ArrayList<>();
        if(this.points.size() < 3){
            throw new IllegalArgumentException("Face must have at least 3 points");
        }else if(this.points.size() == 3){
            faces.add(this);
        } else {
            ArrayList<GridPosition> threeFirstPoints = new ArrayList<>();
            threeFirstPoints.add(this.points.get(0));
            threeFirstPoints.add(this.points.get(1));
            threeFirstPoints.add(this.points.get(2));

            float[] face1uv = new float[] {this.texture.uvMap()[0], this.texture.uvMap()[1], this.texture.uvMap()[2], this.texture.uvMap()[3], this.texture.uvMap()[4], this.texture.uvMap()[5]};

            float[] face2uv = new float[this.texture.uvMap().length - 2];
            face2uv[0] = this.texture.uvMap()[0];
            face2uv[1] = this.texture.uvMap()[1];
            //System.out.println(this.texture.uvMap()[0]);
            for(int i = 2; i < face2uv.length; i++){
                //System.out.println(i + ": " + this.texture.uvMap()[i+2]);
                face2uv[i] = this.texture.uvMap()[i+2];
            }


            Face face1 = new Face(threeFirstPoints, new FaceTexture(this.texture.textureKey(), face1uv));
            this.points.remove(1);
            Face face2 = new Face(this.points, new FaceTexture(this.texture.textureKey(), face2uv));

            faces.add(face1);
            faces.addAll(face2.getThreeVertexFaces());
        }
        return faces;
    }

}
