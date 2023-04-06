package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

public class ViewProjection implements Transformation {
    Matrix matrix;
    public ViewProjection(Matrix viewM, Matrix projMatrix){
        this.matrix = createViewProjectionMatrix(viewM, projMatrix);
    }

    private Matrix createViewProjectionMatrix(Matrix vM, Matrix ProjM){
        Matrix expandedViewMatrix = new Matrix(new float[][]{
            {vM.get(0, 0), vM.get(0, 1), vM.get(0, 2), 0},
            {vM.get(1, 0), vM.get(1, 1), vM.get(1, 2), 0},
            {vM.get(2, 0), vM.get(2, 1), vM.get(2, 2), 0},
            {0      , 0      , 0      , 1}
        });

        return Matrix.multiply(ProjM, expandedViewMatrix);
    }


    @Override
    public Matrix getMatrix() {
        return matrix;
    }

    @Override
    public Face transform(Face face) {
        ArrayList<GridPosition> newVertices = new ArrayList<GridPosition>();
        for (GridPosition vertex : face.getPoints()) {
            Vector t = this.matrix.multiply(new Vector(vertex));
            newVertices.add(new Position3D(t.get(0), t.get(1), t.get(2)));
        }
        return new Face(newVertices, face.getTexture());
    }
    
}
