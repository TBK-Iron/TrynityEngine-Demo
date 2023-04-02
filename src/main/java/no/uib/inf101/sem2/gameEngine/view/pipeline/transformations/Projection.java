package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

public class Projection implements Transformation {
    
    Matrix matrix;

    public Projection(float fov, float aspectRatio, float near, float far){
        this.matrix = createProjectionMatrix(fov, aspectRatio, near, far);
        //System.out.println("Projection matrix: " + this.matrix);
    }

    private Matrix createProjectionMatrix(float fov, float aspectRatio, float near, float far){
        float tanHalfFov = (float) Math.tan(fov / 2); 

        //System.out.println("aspectRatio: " + aspectRatio + ", fov: " + fov + ", near: " + near + ", far: " + far);

        Matrix pMatrix = new Matrix( new float[][] {
            {1/(aspectRatio*tanHalfFov), 0           , 0                         , 0 },
            {0                         , 1/tanHalfFov, 0                         , 0 },
            {0                         , 0           , -(far + near)/(far - near), -1},
            {0                         , 0           , -(2*far*near)/(far - near), 0 }
        });
    
        return pMatrix;
    }

    @Override
    public Matrix getMatrix() {
        return this.matrix;
    }

    @Override
    public Face transform(Face face) {
        ArrayList<GridPosition> newVertices = new ArrayList<GridPosition>();
        for(GridPosition vertex : face.getPoints()){
            Vector v = new Vector(vertex);
            if(v.getDims() != 3){
                throw new IllegalArgumentException("Vector must have 3 dimensions");
            }
            Vector homogenousV = new Vector(new float[] {v.get(0), v.get(1), v.get(2), 1});
            Vector transformedV = this.matrix.multiply(homogenousV);
            
            newVertices.add(transformedV.getPoint());
        }
        return new Face(newVertices, face.getColor());
    }
}
