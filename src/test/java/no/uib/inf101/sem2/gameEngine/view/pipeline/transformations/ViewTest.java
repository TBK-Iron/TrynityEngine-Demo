package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.FaceTexture;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.View;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

import java.util.List;


public class ViewTest {

    @Test
    public void testTransformIdentity() {
        RelativeRotation cameraRotation = new RelativeRotation(0, 0, 0);
        GridPosition cameraPos = new Position3D(0, 0, 0);

        View view = new View(cameraRotation, cameraPos);
        GridPosition vertex1 = new Position3D(1, 1, 1);
        GridPosition vertex2 = new Position3D(1, -1, 1);
        GridPosition vertex3 = new Position3D(-1, 1, 1);
        FaceTexture texture = new FaceTexture("testTexture", new float[]{0, 0, 1, 0, 0, 1});
        Face face = new Face(List.of(vertex1, vertex2, vertex3), texture);

        Face transformedFace = view.transform(face);
        assertEquals(face.getPoints(), transformedFace.getPoints());
    }

    @Test
    public void testTransformTranslation() {
        RelativeRotation cameraRotation = new RelativeRotation(0, 0, 0);
        GridPosition cameraPos = new Position3D(1, 2, 3);

        View view = new View(cameraRotation, cameraPos);
        GridPosition vertex1 = new Position3D(1, 1, 1);
        GridPosition vertex2 = new Position3D(1, -1, 1);
        GridPosition vertex3 = new Position3D(-1, 1, 1);
        FaceTexture texture = new FaceTexture("testTexture", new float[]{0, 0, 1, 0, 0, 1});
        Face face = new Face(List.of(vertex1, vertex2, vertex3), texture);

        Face transformedFace = view.transform(face);
        List<GridPosition> expectedVertices = List.of(
            new Position3D(0, -1, -2),
            new Position3D(0, -3, -2),
            new Position3D(-2, -1, -2)
        );
        assertEquals(expectedVertices, transformedFace.getPoints());
    }
}