package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

/**
 * Represents a single face of a 3D shape, composed of a list of vertices.
 * A face can have a texture, and various methods are provided for
 * manipulating and querying the face properties.
 */
public class Face {
    private final List<GridPosition> points;
    private Vector nVector;
    private final FaceTexture texture;

    /**
     * Constructs a new Face with the specified list of vertices and texture.
     * @param points List of vertices forming the face
     * @param texture Texture applied to the face
     * @throws IllegalArgumentException if the number of vertices doesn't match the number of UV coordinates
     */
    public Face(List<GridPosition> points, FaceTexture texture){
        if(points.size() * 2 != texture.uvMap().length){
            throw new IllegalArgumentException("Each vertex must have a corresponding UV coordinate");
        }

        this.points = points;
        this.texture = texture;
    }

    public List<GridPosition> getPoints(){
        return this.points;
    }

    public FaceTexture getTexture(){
        return this.texture;
    } 

    public int size(){
        return this.points.size();
    }

    /**
     * Returns the normal vector of the face.
     * @return Normal vector of the face
     */
    public Vector getNormalVector(){
        if(nVector != null){
            return nVector;
        } else {
            Vector v1 = new Vector(new float[] {points.get(1).x()-points.get(0).x(), points.get(1).y()-points.get(0).y(), points.get(1).z()-points.get(0).z()});
            Vector v2 = new Vector(new float[] {points.get(2).x()-points.get(0).x(), points.get(2).y()-points.get(0).y(), points.get(2).z()-points.get(0).z()});
            return Vector.crossProduct(v1, v2);
        
        }
    }

    /**
     * Returns the minimum and maximum points of the axis-aligned bounding box in the XY plane.
     * @return An array of two Vectors: {minVector, maxVector}
     */
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
        sb.append("\n");
        float[] uvMap = this.texture.uvMap();
        for(int i = 0; i < this.points.size(); i++){
            sb.append(", u" + i + ": " + uvMap[i*2]);
            sb.append(", v" + i + ": " + uvMap[i*2 +1]);
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
    
        if (texture.uvMap().length != otherFace.texture.uvMap().length) {
            return false;
        }
    
        for (int i = 0; i < texture.uvMap().length; i++) {
            if (Math.abs(texture.uvMap()[i] - otherFace.texture.uvMap()[i]) > 0.0001) {
                return false;
            }
        }
    
        return true;
    }

    /**
     * Returns the point in the face that is closest to the origin, 
     * note: this is not the vertex that is closest to the origin.
     * @return Point closest to the origin
     */
    public GridPosition getPointClosestToOrigin() {
        if (points.isEmpty()) {
            return null;
        }
    
        GridPosition closestPoint = points.get(0);
    
        for (int i = 0; i < points.size(); i++) {
            GridPosition startPoint = points.get(i);
            GridPosition endPoint = points.get((i + 1) % points.size());
    
            Vector v = Vector.getVector(startPoint, endPoint);
            Vector w = new Vector((Position3D) startPoint);
    
            float t = -Vector.dotProduct(w, v) / Vector.dotProduct(v, v);
    
            if (t < 0) {
                t = 0;
            } else if (t > 1) {
                t = 1;
            }
    
            Vector projection = Vector.add(w, v.scaledBy(t));
    
            if (projection.magnitude() < new Vector((Position3D) closestPoint).magnitude()) {
                closestPoint = new Position3D(projection.get(0), projection.get(1), projection.get(2));
            }
        }
    
        return closestPoint;
    }

    /**
     * Returns a list of new faces, each composed of three vertices, by splitting the original face.
     * @return A list of faces, each with three vertices
     * @throws IllegalArgumentException if the face has less than 3 vertices
     */
    public ArrayList<Face> getThreeVertexFaces(){
        ArrayList<Face> faces = new ArrayList<>();
        if(this.points.size() < 3){
            throw new IllegalArgumentException("Face must have at least 3 points");
        } else if(this.points.size() == 3){
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
            for(int i = 2; i < face2uv.length; i++){
                face2uv[i] = this.texture.uvMap()[i+2];
            }
    
            Face face1 = new Face(threeFirstPoints, new FaceTexture(this.texture.textureKey(), face1uv));
    
            // Create a new list of points excluding the point at index 1
            ArrayList<GridPosition> remainingPoints = new ArrayList<>(this.points);
            remainingPoints.remove(1);
    
            Face face2 = new Face(remainingPoints, new FaceTexture(this.texture.textureKey(), face2uv));
    
            faces.add(face1);
            faces.addAll(face2.getThreeVertexFaces());
        }
        return faces;
    }

    /**
     * Returns the center of the face.
     * @return The center position of the face
     */
    public GridPosition getCenter() {
        float x = 0;
        float y = 0;
        float z = 0;
        int numVertices = points.size();

        for (GridPosition point : points) {
            x += point.x();
            y += point.y();
            z += point.z();
        }

        x /= numVertices;
        y /= numVertices;
        z /= numVertices;

        return new Position3D(x, y, z);
    }

}
