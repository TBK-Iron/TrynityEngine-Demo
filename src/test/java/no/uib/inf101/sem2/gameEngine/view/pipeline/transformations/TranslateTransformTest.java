package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.FaceTexture;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslateTransformTest {

    private TranslateTransform translateTransform;
    private Vector position;

    @BeforeEach
    public void setUp() {
        position = new Vector(new float[] {2, 3, 4});
        translateTransform = new TranslateTransform(position);
    }

    @Test
    public void testTranslationMatrix() {
        Matrix expectedTranslationMatrix = new Matrix(new float[][]{
            {1, 0, 0, position.get(0)},
            {0, 1, 0, position.get(1)},
            {0, 0, 1, position.get(2)},
            {0, 0, 0, 1}
        });
        assertEquals(expectedTranslationMatrix, translateTransform.getMatrix());
    }

    @Test
    public void testTransform() {
        GridPosition p1 = new Position3D(1, 1, 1);
        GridPosition p2 = new Position3D(1, -1, 1);
        GridPosition p3 = new Position3D(-1, -1, 1);
        GridPosition p4 = new Position3D(-1, 1, 1);

        ArrayList<GridPosition> vertices = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
        FaceTexture texture = new FaceTexture("testTexture", new float[]{0, 0, 0, 1, 1, 1, 1, 0});
        Face face = new Face(vertices, texture);

        Face transformedFace = translateTransform.transform(face);

        GridPosition t1 = new Position3D(3, 4, 5);
        GridPosition t2 = new Position3D(3, 2, 5);
        GridPosition t3 = new Position3D(1, 2, 5);
        GridPosition t4 = new Position3D(1, 4, 5);

        ArrayList<GridPosition> transformedVertices = new ArrayList<>(Arrays.asList(t1, t2, t3, t4));

        Face expectedTransformedFace = new Face(transformedVertices, face.getTexture());
        assertEquals(expectedTransformedFace, transformedFace);
    }
}