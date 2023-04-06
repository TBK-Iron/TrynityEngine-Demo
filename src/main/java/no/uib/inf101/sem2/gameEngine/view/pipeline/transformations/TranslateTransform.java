package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

public class TranslateTransform implements Transformation {
    Matrix matrix;

    public TranslateTransform(Vector position) {
        this.matrix = getPosistionMatrix(position);
    }

    private Matrix getPosistionMatrix(Vector v){
        Matrix posMatrix = new Matrix(new float[][] {
            {1, 0, 0, v.get(0)},
            {0, 1, 0, v.get(1)},
            {0, 0, 1, v.get(2)},
            {0, 0, 0, 1}
        });
        return posMatrix;
    }

    @Override
    public Matrix getMatrix() {
        return this.matrix;
    }

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
