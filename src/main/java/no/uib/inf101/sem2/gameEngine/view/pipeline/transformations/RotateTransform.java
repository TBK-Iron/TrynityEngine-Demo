package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;

public class RotateTransform implements Transformation {
    
    Matrix matrix;

    public RotateTransform(Rotation rotation){
        this.matrix = getRotationMatrix(rotation);
    }

    private static Matrix getRotationMatrix(Rotation rot){
        float rotX = rot.getxAxis();
        float rotY = rot.getyAxis();
        float rotZ = rot.getzAxis();

        Matrix matrixX = new Matrix(new float[][] {
            {1f, 0f, 0f},
            {0f, (float) Math.cos(rotX), (float) -Math.sin(rotX)},
            {0f, (float) Math.sin(rotX), (float) Math.cos(rotX)}
        });

        Matrix matrixY = new Matrix(new float[][] {
            {(float) Math.cos(rotY), 0f, (float) Math.sin(rotY)},
            {0f, 1f, 0f},
            {(float) -Math.sin(rotY), 0f, (float) Math.cos(rotY)}
        });

        Matrix matrixZ = new Matrix(new float[][] {
            {(float) Math.cos(rotZ), (float) -Math.sin(rotZ), 0f},
            {(float) Math.sin(rotZ), (float) Math.cos(rotZ), 0f},
            {0f, 0f, 1f}
        });

        Matrix rotationMatrix = Matrix.multiply(Matrix.multiply(matrixX, matrixY), matrixZ);

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
            Vector t = this.matrix.multiply(new Vector(vertex));
            newVertices.add(new Position3D(t.get(0), t.get(1), t.get(2)));
        }
        return new Face(newVertices, face.getColor());
    }
}
