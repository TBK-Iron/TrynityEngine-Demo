package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

/**
 * The View class represents a view transformation in the rendering pipeline.
 * It applies camera position and orientation to a given Face, transforming its
 * vertices from world coordinates to camera coordinates.
 *
 * This class implements the Transformation interface.
 */
public final class View implements Transformation {
    private final Matrix matrix;

    /**
     * Constructs a View transformation with the given camera rotation and position.
     *
     * @param cameraRotation The rotation of the camera.
     * @param cameraPos The position of the camera in the world coordinates.
     */
    public View(RelativeRotation cameraRotation, GridPosition cameraPos){
        this.matrix = createViewMatrix(cameraRotation, cameraPos);
    }

    /**
     * Creates a view matrix using the camera rotation and position.
     *
     * @param cameraRotation The rotation of the camera.
     * @param cameraPos The position of the camera in the world coordinates.
     * @return The view matrix.
     */
    private Matrix createViewMatrix(RelativeRotation cameraRotation, GridPosition cameraPos){
        Matrix rotM = new RotateTransform(cameraRotation.getNegRotation(), true).getMatrix();
        Matrix expRotationMatrix = new Matrix(new float[][]{
            {rotM.get(0, 0), rotM.get(0, 1), rotM.get(0, 2), 0},
            {rotM.get(1, 0), rotM.get(1, 1), rotM.get(1, 2), 0},
            {rotM.get(2, 0), rotM.get(2, 1), rotM.get(2, 2), 0},
            {0, 0, 0, 1}
        });
        Matrix positionMatrix = new TranslateTransform(new Vector((Position3D) cameraPos).scaledBy(-1)).getMatrix();
        Matrix viewMatrix = Matrix.multiply(expRotationMatrix, positionMatrix);
        return viewMatrix;
    }

    /**
     * Returns the view matrix associated with this View transformation.
     *
     * @return The view matrix.
     */
    @Override
    public Matrix getMatrix() {
        return matrix;
    }

    /**
     * Transforms the given Face using the view matrix, translating its
     * vertices from world coordinates to camera coordinates.
     *
     * @param face The Face to be transformed.
     * @return The transformed Face.
     */
    @Override
    public Face transform(Face face) {
        ArrayList<GridPosition> newVertices = new ArrayList<GridPosition>();
        for (GridPosition vertex : face.getPoints()) {
            Vector t = this.matrix.multiply(new Vector(new float[] {vertex.x(), vertex.y(), vertex.z(), 1}));
            newVertices.add(new Position3D(t.get(0), t.get(1), t.get(2)));
        }
        return new Face(newVertices, face.getTexture());
    }
}
