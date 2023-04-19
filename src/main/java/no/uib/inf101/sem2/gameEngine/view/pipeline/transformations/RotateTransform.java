package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

public final class RotateTransform implements Transformation {
    
    private final Matrix matrix;

    public RotateTransform(RelativeRotation rotation){
        this.matrix = getRotationMatrix(rotation);
    }

    private static Matrix getRotationMatrix(RelativeRotation rot){
        float pivot = rot.getPivot();
        float upDown = -rot.getUpDown();
        float leftRight = rot.getLeftRight();

        Matrix pivotMatrix = new Matrix(new float[][]{
            {(float) Math.cos(pivot), (float) -Math.sin(pivot), 0},
            {(float) Math.sin(pivot), (float) Math.cos(pivot), 0},
            {0, 0, 1}
        });

        Matrix updownMatrix = new Matrix(new float[][] {
            {1, 0, 0},
            {0, (float) Math.cos(upDown), (float) -Math.sin(upDown)},
            {0, (float) Math.sin(upDown), (float) Math.cos(upDown)}
        });

        Matrix leftRightMatrix = new Matrix(new float[][] {
            {(float) Math.cos(leftRight), 0f, (float) Math.sin(leftRight)},
            {0f, 1f, 0f},
            {(float) -Math.sin(leftRight), 0f, (float) Math.cos(leftRight)}
        });

        Matrix rotationMatrix = Matrix.multiply(pivotMatrix, Matrix.multiply(updownMatrix, leftRightMatrix));

        return rotationMatrix;
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }

    @Override
    public Face transform(Face face) {
        ArrayList<GridPosition> newVertices = new ArrayList<GridPosition>();
        for (GridPosition vertex : face.getPoints()) {
            Vector t = this.matrix.multiply(new Vector((Position3D) vertex));
            newVertices.add(new Position3D(t.get(0), t.get(1), t.get(2)));
        }
        return new Face(newVertices, face.getTexture());
    }
}
