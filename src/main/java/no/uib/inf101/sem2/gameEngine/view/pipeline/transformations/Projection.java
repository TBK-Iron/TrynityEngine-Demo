package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position4D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

/**
 * This class represents a 3D projection transformation to be applied to faces.
 * It is responsible for creating a projection matrix and transforming faces
 * using that matrix.
 */
public final class Projection implements Transformation {
    
    private final Matrix matrix;

    /**
     * Constructs a new Projection transformation.
     *
     * @param fov        the field of view angle in radians
     * @param aspectRatio the aspect ratio of the screen
     * @param near       the near clipping plane distance
     * @param far        the far clipping plane distance
     */
    public Projection(float fov, float aspectRatio, float near, float far){
        this.matrix = createProjectionMatrix(fov, aspectRatio, near, far);
    }

    /**
     * Creates a projection matrix using the specified parameters.
     *
     * @param fov        the field of view angle in radians
     * @param aspectRatio the aspect ratio of the screen
     * @param near       the near clipping plane distance
     * @param far        the far clipping plane distance
     * @return the projection matrix
     */
    private Matrix createProjectionMatrix(float fov, float aspectRatio, float near, float far){
        float tanHalfFov = (float) Math.tan(fov / 2); 

        Matrix pMatrix = new Matrix( new float[][] {
            {1/(aspectRatio*tanHalfFov), 0           , 0                         , 0 },
            {0                         , 1/tanHalfFov, 0                         , 0 },
            {0                         , 0           , -(far + near)/(far - near), -1},
            {0                         , 0           , -(2*far*near)/(far - near), 0 }
        });
    
        return pMatrix;
    }

    /**
     * Returns the projection matrix.
     *
     * @return the projection matrix
     */
    @Override
    public Matrix getMatrix() {
        return this.matrix;
    }

    /**
     * Transforms the input face using the projection matrix.
     *
     * @param face the face to be transformed
     * @return the transformed face
     */
    @Override
    public Face transform(Face face) {
        ArrayList<GridPosition> newVertices = new ArrayList<GridPosition>();
        for(GridPosition vertex : face.getPoints()){
            Vector v = new Vector((Position3D) vertex);
            if(v.getDims() != 3){
                throw new IllegalArgumentException("Vector must have 3 dimensions");
            }
            Vector homogenousV = new Vector(new float[] {v.get(0), v.get(1), v.get(2), 1});
            Vector transformedV = this.matrix.multiply(homogenousV);

            GridPosition newP = transformedV.getPoint();
            
            newVertices.add(new Position4D(newP.x(), newP.y(), v.get(2), newP.w()));
        }
        return new Face(newVertices, face.getTexture());
    }
}
