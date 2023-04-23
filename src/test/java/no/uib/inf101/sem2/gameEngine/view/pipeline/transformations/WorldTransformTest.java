package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.FaceTexture;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public class WorldTransformTest {

    @Test
    public void testMatrixCreation() {
        GridPosition shapePos = new Position3D(2, 3, 4);
        RelativeRotation shapeRotation = new RelativeRotation(30, 60, 90);
        WorldTransform worldTransform = new WorldTransform(shapePos, shapeRotation);

        assertNotNull(worldTransform.getMatrix(), "Matrix should not be null");

        Matrix rotM = new RotateTransform(shapeRotation, false).getMatrix();
        Matrix rotM4x4 = new Matrix(new float[][]{
            {rotM.get(0, 0), rotM.get(0, 1), rotM.get(0, 2), 0},
            {rotM.get(1, 0), rotM.get(1, 1), rotM.get(1, 2), 0},
            {rotM.get(2, 0), rotM.get(2, 1), rotM.get(2, 2), 0},
            {0, 0, 0, 1}
        });
        
        Matrix translateTransform = new TranslateTransform(new Vector((Position3D) shapePos)).getMatrix();

        Matrix expectedMatrix = Matrix.multiply(translateTransform, rotM4x4);

        assertEquals(expectedMatrix, worldTransform.getMatrix(), "Incorrect matrix");

        
    }

    @Test
    public void testTransform() {
        GridPosition shapePos = new Position3D(2, 3, 4);
        RelativeRotation shapeRotation = new RelativeRotation((float) Math.PI/4, (float) Math.PI/2);
        WorldTransform worldTransform = new WorldTransform(shapePos, shapeRotation);

        GridPosition p1 = new Position3D(1, 0, 0);
        GridPosition p2 = new Position3D(0, 1, 0);
        GridPosition p3 = new Position3D(0, 0, 1);

        ArrayList<GridPosition> vertices = new ArrayList<>(Arrays.asList(p1, p2, p3));
        FaceTexture texture = new FaceTexture("testTexture", new float[]{0, 0, 0, 1, 1, 1});

        Face face = new Face(vertices, texture);

        Face transformedFace = worldTransform.transform(face);
        assertNotNull(transformedFace, "Transformed face should not be null");

        GridPosition t1 = new Position3D(2, 3, 3);
        GridPosition t2 = new Position3D(1.2928932f, 3.7071068f, 4);
        GridPosition t3 = new Position3D(2.7071068f, 3.7071068f, 4);

        ArrayList<GridPosition> expectedVertices = new ArrayList<>(Arrays.asList(t1, t2, t3));
        Face expectedFace = new Face(expectedVertices, face.getTexture());

        assertEquals(expectedFace, transformedFace);
    }
}