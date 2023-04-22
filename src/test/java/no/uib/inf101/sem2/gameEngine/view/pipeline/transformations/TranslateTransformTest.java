package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.FaceTexture;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
import org.junit.jupiter.api.Test;

public class TranslateTransformTest {

    @Test
    public void testTransform() {
        // Prepare test data
        List<GridPosition> vertices = new ArrayList<>();
        vertices.add(new Position3D(0, 0, 0));
        vertices.add(new Position3D(1, 0, 0));
        vertices.add(new Position3D(0, 1, 0));
        FaceTexture texture = new FaceTexture("testTexture", new float[]{0, 0, 1, 0, 0, 1});
        Face face = new Face(vertices, texture);

        // Translate by (2, 3, 4)
        Vector translationVector = new Vector(new float[]{2, 3, 4});
        TranslateTransform transform = new TranslateTransform(translationVector);
        Face transformedFace = transform.transform(face);

        // Check if the vertices of the transformed face are correct
        List<GridPosition> transformedVertices = transformedFace.getPoints();
        assertEquals(new Position3D(2, 3, 4), transformedVertices.get(0));
        assertEquals(new Position3D(3, 3, 4), transformedVertices.get(1));
        assertEquals(new Position3D(2, 4, 4), transformedVertices.get(2));

        // Check if the texture of the transformed face is the same as the original face
        assertEquals(face.getTexture(), transformedFace.getTexture());
    }

    //TODO make test for get matrix

}