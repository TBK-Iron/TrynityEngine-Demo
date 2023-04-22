package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.FaceTexture;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewTest {

    private RelativeRotation rotation;
    private GridPosition position;

    @BeforeEach
    public void setUp() {
        rotation = new RelativeRotation(0, 0);
        position = new Position3D(0, 0, 0);
    }

    @Test
    public void testGetMatrix() {
        View view = new View(rotation, position);
        Matrix expectedMatrix = view.getMatrix();
        assertEquals(expectedMatrix, view.getMatrix());
    }

    private Face createTestFace() {
        Position3D p1 = new Position3D(1, 1, 1);
        Position3D p2 = new Position3D(1, -1, 1);
        Position3D p3 = new Position3D(-1, -1, 1);
        Position3D p4 = new Position3D(-1, 1, 1);

        ArrayList<GridPosition> vertices = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
        FaceTexture texture = new FaceTexture("testTexture", new float[]{0, 0, 0, 1, 1, 1, 1, 0});

        return new Face(vertices, texture);
    }

    @Test
    public void testTransform_NoChange() {
        View view = new View(rotation, position);
        Face face = createTestFace();
        Face transformedFace = view.transform(face);
        assertEquals(face, transformedFace);
    }

    @Test
    public void testTransform_PositionChange() {
        position = new Position3D(-2, 3, -1);
        View view = new View(rotation, position);
        Face face = createTestFace();
        Face transformedFace = view.transform(face);

        TranslateTransform translateTransform = new TranslateTransform(new Vector((Position3D) position).scaledBy(-1));
        Face expectedTransformedFace = translateTransform.transform(face);
        assertEquals(expectedTransformedFace, transformedFace);
    }

    @Test
    public void testTransform_RotationChange() {
        rotation = new RelativeRotation(45, 30, 60);
        View view = new View(rotation, position);
        Face face = createTestFace();
        Face transformedFace = view.transform(face);

        RotateTransform rotateTransform = new RotateTransform(rotation.getNegRotation(), true);
        Face expectedTransformedFace = rotateTransform.transform(face);
        assertEquals(expectedTransformedFace, transformedFace);
    }

    @Test
    public void testTransform_BothChanges() {
        rotation = new RelativeRotation(45, 30, 60);
        position = new Position3D(-2, 3, -1);
        View view = new View(rotation, position);
        Face face = createTestFace();
        Face transformedFace = view.transform(face);

        RotateTransform rotateTransform = new RotateTransform(rotation.getNegRotation(), true);
        TranslateTransform translateTransform = new TranslateTransform(new Vector((Position3D) position).scaledBy(-1));
        Face expectedTransformedFace = rotateTransform.transform(translateTransform.transform(face));
        assertEquals(expectedTransformedFace, transformedFace);
    }
}
