package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

public class View implements Transformation {
    Matrix matrix;

    public View(RelativeRotation cameraRotation, GridPosition cameraPos){
        this.matrix = createViewMatrix(cameraRotation, cameraPos);
    }

    private Matrix createViewMatrix(RelativeRotation cameraRotation, GridPosition cameraPos){
        Matrix rotM = new RotateTransform(cameraRotation.getNegRotation()).getMatrix();
        Matrix expRotationMatrix = new Matrix(new float[][]{
            {rotM.get(0, 0), rotM.get(0, 1), rotM.get(0, 2), 0},
            {rotM.get(1, 0), rotM.get(1, 1), rotM.get(1, 2), 0},
            {rotM.get(2, 0), rotM.get(2, 1), rotM.get(2, 2), 0},
            {0, 0, 0, 1}
        });
        Matrix positionMatrix = new TranslateTransform(new Vector(cameraPos).scaledBy(-1)).getMatrix();
        Matrix viewMatrix = Matrix.multiply(expRotationMatrix, positionMatrix);
        return viewMatrix;
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }

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
