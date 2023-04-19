package no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest {
    @Test
    void testAdd() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        Vector v2 = new Vector(new float[]{4, 5, 6});
        Vector expectedResult = new Vector(new float[]{5, 7, 9});
        assertEquals(expectedResult, Vector.add(v1, v2));
    }

    @Test
    void testSubtract() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        Vector v2 = new Vector(new float[]{4, 5, 6});
        Vector expectedResult = new Vector(new float[]{-3, -3, -3});
        assertEquals(expectedResult, Vector.subtract(v1, v2));
    }

    @Test
    void testMultiply() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        Vector v2 = new Vector(new float[]{4, 5, 6});
        Vector expectedResult = new Vector(new float[]{4, 10, 18});
        assertEquals(expectedResult, Vector.multiply(v1, v2));
    }

    @Test
    void testDivide() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        Vector v2 = new Vector(new float[]{4, 5, 6});
        Vector expectedResult = new Vector(new float[]{0.25f, 0.4f, 0.5f});
        assertEquals(expectedResult, Vector.divide(v1, v2));
    }

    @Test
    void testMinVector() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        Vector v2 = new Vector(new float[]{4, 5, 6});
        Vector expectedResult = new Vector(new float[]{1, 2, 3});
        assertEquals(expectedResult, Vector.minVector(v1, v2));
    }

    @Test
    void testMaxVector() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        Vector v2 = new Vector(new float[]{4, 5, 6});
        Vector expectedResult = new Vector(new float[]{4, 5, 6});
        assertEquals(expectedResult, Vector.maxVector(v1, v2));
    }

    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        Vector v2 = new Vector(new float[]{4, 5, 6});
        Vector expectedResult = new Vector(new float[]{-3, 6, -3});
        assertEquals(expectedResult, Vector.crossProduct(v1, v2));
    }

    @Test
    void testDotProduct() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        Vector v2 = new Vector(new float[]{4, 5, 6});
        float expectedResult = 32;
        assertEquals(expectedResult, Vector.dotProduct(v1, v2), 0.001);
    }

    @Test
    void testScaledBy() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        float scalar = 2;
        Vector expectedResult = new Vector(new float[]{2, 4, 6});
        assertEquals(expectedResult, v1.scaledBy(scalar));
    }

    @Test
    void testMagnitude() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        float expectedResult = (float) Math.sqrt(14);
        assertEquals(expectedResult, v1.magnitude(), 0.001);
    }

    @Test
    void testNormalized() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        float magnitude = v1.magnitude();
        Vector expectedResult = new Vector(new float[]{1 / magnitude, 2 / magnitude, 3 / magnitude});
        assertEquals(expectedResult, v1.normalized());
    }

    @Test
    void testGetPoint2D() {
        Vector v1 = new Vector(new float[]{1, 2});
        GridPosition expectedResult = new Position2D(1, 2);
        assertEquals(expectedResult, v1.getPoint());
    }

    @Test
    void testGetPoint3D() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        GridPosition expectedResult = new Position3D(1, 2, 3);
        assertEquals(expectedResult, v1.getPoint());
    }

    @Test
    void testGetPoint4D() {
        Vector v1 = new Vector(new float[]{1, 2, 3, 4});
        GridPosition expectedResult = new Position4D(1, 2, 3, 4);
        assertEquals(expectedResult, v1.getPoint());
    }

    @Test
    void testEquals() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        Vector v2 = new Vector(new float[]{1, 2, 3});
        assertTrue(v1.equals(v2));
    }

    @Test
    void testNotEquals() {
        Vector v1 = new Vector(new float[]{1, 2, 3});
        Vector v2 = new Vector(new float[]{1, 2, 4});
        assertFalse(v1.equals(v2));
    }
}

