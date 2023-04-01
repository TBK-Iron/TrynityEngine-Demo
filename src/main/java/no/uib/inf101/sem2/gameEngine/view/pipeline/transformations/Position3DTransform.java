package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

public class Position3DTransform implements Transformation {
    Matrix matrix;

    public Position3DTransform(GridPosition position) {
        this.matrix = getPosistionMatrix(position);
    }

    private Matrix getPosistionMatrix(GridPosition pos){
        Matrix posMatrix = new Matrix(new float[][] {
            {1+pos.x(), pos.x(), pos.x()},
            {pos.y(), 1+pos.y(), pos.y()},
            {pos.z(), pos.z(), 1+pos.z()}
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
            Vector t = this.matrix.multiply(new Vector(vertex));
            newVertices.add(new Position3D(t.get(0), t.get(1), t.get(2)));
        }
        return new Face(newVertices, face.getColor());
    }
    
}
