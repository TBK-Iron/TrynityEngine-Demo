package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

public class WorldTransform implements Transformation {

    private final Matrix matrix;

    public WorldTransform(GridPosition shapePos, RelativeRotation shapeRotation){
        this.matrix = createWorldMatrix(shapePos, shapeRotation);
    }

    private Matrix createWorldMatrix(GridPosition shapePos, RelativeRotation shapeRotation){
        Matrix rotM = new RotateTransform(shapeRotation, false).getMatrix();
        Matrix expRotationMatrix = new Matrix(new float[][]{
            {rotM.get(0, 0), rotM.get(0, 1), rotM.get(0, 2), 0},
            {rotM.get(1, 0), rotM.get(1, 1), rotM.get(1, 2), 0},
            {rotM.get(2, 0), rotM.get(2, 1), rotM.get(2, 2), 0},
            {0, 0, 0, 1}
        });

        Matrix positionMatrix = new TranslateTransform(new Vector((Position3D) shapePos)).getMatrix();
        Matrix viewMatrix = Matrix.multiply(positionMatrix, expRotationMatrix);
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
