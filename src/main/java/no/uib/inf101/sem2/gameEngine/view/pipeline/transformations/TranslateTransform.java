package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

/**
 * The TranslateTransform class represents a translation transformation in a 3D space.
 * This class implements the Transformation interface, allowing it to transform a face
 * by translating its vertices by a specified position vector.
 */
public final class TranslateTransform implements Transformation {
    private final Matrix matrix;

    /**
     * Constructs a new TranslateTransform with the specified position vector.
     * The created transform will translate faces by the given position vector.
     *
     * @param position The position vector by which to translate faces.
     */
    public TranslateTransform(Vector position) {
        this.matrix = getPosistionMatrix(position);
    }

    /**
     * Creates a position matrix for the given position vector.
     *
     * @param v The position vector.
     * @return A position matrix representing the translation transformation.
     */
    private Matrix getPosistionMatrix(Vector v){
        Matrix posMatrix = new Matrix(new float[][] {
            {1, 0, 0, v.get(0)},
            {0, 1, 0, v.get(1)},
            {0, 0, 1, v.get(2)},
            {0, 0, 0, 1}
        });
        return posMatrix;
    }

    /**
     * Returns the translation matrix representing this translation transformation.
     *
     * @return The translation matrix.
     */
    @Override
    public Matrix getMatrix() {
        return this.matrix;
    }

    /**
     * Transforms the given face by translating its vertices according to the translation matrix.
     *
     * @param face The face to transform.
     * @return The transformed face with its vertices translated.
     */
    @Override
    public Face transform(Face face) {
        ArrayList<GridPosition> newVertices = new ArrayList<GridPosition>();
        for (GridPosition vertex : face.getPoints()) {
            Vector t = this.matrix.multiply(new Vector(new float[]{vertex.x(), vertex.y(), vertex.z(), 1}));
            newVertices.add(new Position3D(t.get(0), t.get(1), t.get(2)));
        }
        return new Face(newVertices, face.getTexture());
    }
    
}
