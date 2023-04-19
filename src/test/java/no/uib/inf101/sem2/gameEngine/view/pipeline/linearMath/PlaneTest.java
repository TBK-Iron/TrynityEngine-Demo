package no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void testNormalize() {
        Vector normal = new Vector(new float[]{3, 4, 0});
        Plane plane = new Plane(normal, 5);
        plane.normalize();
        Vector expectedNormal = new Vector(new float[]{0.6f, 0.8f, 0});
        assertEquals(expectedNormal, plane.normal);
    }

    @Test
    void testIsVertexWithinPlane() {
        Vector normal = new Vector(new float[]{0, 1, 0});
        Plane plane = new Plane(normal, 3);
        GridPosition pointInside = new Position3D(2, 1, 4);
        GridPosition pointOutside = new Position3D(2, 4, 4);

        assertTrue(plane.isVertexWithinPlane(pointInside));
        assertFalse(plane.isVertexWithinPlane(pointOutside));
    }

    @Test
    void testCalculateT() {
        Vector normal = new Vector(new float[]{0, 1, 0});
        Plane plane = new Plane(normal, 2);
        GridPosition p1 = new Position3D(2, 1, 4);
        GridPosition p2 = new Position3D(2, 4, 4);

        float t = plane.calculateT(p1, p2);
        float expectedT = 1 / 3f;
        assertEquals(expectedT, t, 1e-6);
    }

    @Test
    void testToString() {
        Vector normal = new Vector(new float[]{0, 1, 0});
        Plane plane = new Plane(normal, 2);
        String expectedString = "Plane{normal=" + normal + ", dist=2.0}";
        assertEquals(expectedString, plane.toString());
    }
}

