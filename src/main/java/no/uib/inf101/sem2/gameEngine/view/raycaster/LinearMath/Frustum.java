package no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;

public class Frustum {
    Vector[] planes;

    public Frustum(Matrix viewProjectionMatrix){
        planes = new Vector[6];

        extractPlanes(viewProjectionMatrix);
        normalizePlanes();
    }

    private void extractPlanes(Matrix viewProjMatrix){
        double[][] m = viewProjMatrix.value;

        planes[0] = new Vector(new double[]{m[0][3] + m[0][0], m[1][3] + m[1][0], m[2][3] + m[2][0], m[3][3] + m[3][0]}); // Left
        planes[1] = new Vector(new double[]{m[0][3] - m[0][0], m[1][3] - m[1][0], m[2][3] - m[2][0], m[3][3] - m[3][0]}); // Right
        planes[2] = new Vector(new double[]{m[0][3] + m[0][1], m[1][3] + m[1][1], m[2][3] + m[2][1], m[3][3] + m[3][1]}); // Bottom
        planes[3] = new Vector(new double[]{m[0][3] - m[0][1], m[1][3] - m[1][1], m[2][3] - m[2][1], m[3][3] - m[3][1]}); // Top
        planes[4] = new Vector(new double[]{m[0][3] + m[0][2], m[1][3] + m[1][2], m[2][3] + m[2][2], m[3][3] + m[3][2]}); // Near
        planes[5] = new Vector(new double[]{m[0][3] - m[0][2], m[1][3] - m[1][2], m[2][3] - m[2][2], m[3][3] - m[3][2]}); // Far

    }

    private void normalizePlanes() {
        for(int i = 0; i < planes.length; i++){
            planes[i] = planes[i].scaledBy(1/planes[i].magnitude());
        }
    }

    public boolean isFaceCulled(Face face){
        Vector[] AABB = face.getAABB();
        Vector min = AABB[0];
        Vector max = AABB[1];

        for(int i = 0; i < 6; i++){
            double[] p = new double[3];
            if (planes[i].value[0] >= 0) {
                p[0] = min.value[0];
            } else {
                p[0] = max.value[0];
            }
            
            if (planes[i].value[1] >= 0) {
                p[1] = min.value[1];
            } else {
                p[1] = max.value[1];
            }
            
            if (planes[i].value[2] >= 0) {
                p[2] = min.value[2];
            } else {
                p[2] = max.value[2];
            }

            double dist = (new Vector(p)).magnitude();

            if(dist < 0){
                return true;
            }
        }
        return false;
    }

    //TODO: create method for clipping faces with the frustum
    public Face clipFace(Face face) {
        ArrayList<Vector> clippedVertices = new ArrayList<Vector>();
        ArrayList<Vector> clippedTexCoords = new ArrayList<Vector>();
        ArrayList<Vector> clippedNormals = new ArrayList<Vector>();
    
        double[] distances = getMinDistances(face);

        for(int i = 1; i < face.getPoints().size() + 1; i++){
            double d1 = distances[i - 1];
            double d2 = distances[i % face.getPoints().size()];
            if((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)){
                
            }
        }
    
       
    } 
    
    private double[] getMinDistances(Face face){
        double[] distances = new double[face.getPoints().size()];
        for (int i = 0; i < face.getPoints().size(); i++) {
            double minDist = 999999999;
            for(int j = 0; j < planes.length; j++){
                Vector v = new Vector(face.get(i));
                double dist = Vector.dotProduct(v, planes[i]) + v.magnitude();
                if(dist < minDist){
                    minDist = dist;
                }
            }
            distances[i] = minDist;
        }  
        return distances;      
    }
    
}
