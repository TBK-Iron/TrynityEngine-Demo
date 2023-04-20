package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;

/**
 * The Transformation interface represents a transformation that can be applied to a 3D shape or face.
 * Implementations of this interface should provide a transformation matrix and a method to transform a face.
 */
public interface Transformation {

    /**
     * Returns the transformation matrix representing this transformation.
     * The matrix should be applied to vertices to transform them according
     * to the specific implementation of the transformation.
     *
     * @return The transformation matrix.
     */
    public Matrix getMatrix();

    /**
     * Transforms the given face according to the specific implementation of this transformation.
     * The resulting face should have its vertices transformed based on the transformation matrix
     * provided by the getMatrix() method.
     *
     * @param face The face to transform.
     * @return The transformed face.
     */
    public Face transform(Face face);
}
