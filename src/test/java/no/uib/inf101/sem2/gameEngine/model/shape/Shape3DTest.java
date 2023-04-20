package no.uib.inf101.sem2.gameEngine.model.shape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.collision.BoundingSphere;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

class Shape3DTest {
    private Shape3D shape;
    private ShapeData shapeData;

    @BeforeEach
    void setUp() {
        File file = new File("src/main/resources/shapes/brick_cube.trym");
        shapeData = new ShapeData(new Position3D(0, 0, 0), new RelativeRotation(0, 0), file);
        shape = new Shape3D(shapeData);
    }

    @Test
    void testFacesCount() {
        ArrayList<Face> faces = shape.getFaces();
        assertEquals(6, faces.size());
    }

    @Test
    void testBoundingSphere() {
        BoundingSphere boundingSphere = shape.getBoundingSphere();
        GridPosition center = boundingSphere.centerPos();
        float radius = boundingSphere.radius();

        assertEquals(0, center.x(), 0.0001);
        assertEquals(0, center.y(), 0.0001);
        assertEquals(0, center.z(), 0.0001);
        assertEquals(0.25 * Math.sqrt(3), radius, 0.0001);
    }
}