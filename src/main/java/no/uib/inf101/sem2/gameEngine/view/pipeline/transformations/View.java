package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;

public class View implements Transformation {
    Matrix matrix;

    public View(RelativeRotation cameraRotation, GridPosition cameraPos){
        this.matrix = createViewMatrix(cameraRotation, cameraPos);
    }

    private Matrix createViewMatrix(RelativeRotation cameraRotation, GridPosition cameraPos){
        Matrix rotationMatrix = new RotateTransform(cameraRotation.getAbsolute().getNegRotation()).getMatrix();
        Matrix positionMatrix = new Position3DTransform(cameraPos).getMatrix();
        Matrix viewMatrix = Matrix.multiply(rotationMatrix, positionMatrix);
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
            Vector t = this.matrix.multiply(new Vector(vertex));
            newVertices.add(new Position3D(t.get(0), t.get(1), t.get(2)));
        }
        return new Face(newVertices, face.getColor());
    }
}
