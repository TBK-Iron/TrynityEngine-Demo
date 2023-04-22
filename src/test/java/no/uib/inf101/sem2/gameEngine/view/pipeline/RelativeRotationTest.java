package no.uib.inf101.sem2.gameEngine.view.pipeline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelativeRotationTest {

    @Test
    void testConstructor() {
        RelativeRotation rotation = new RelativeRotation(0.5f, 0.6f, 0.7f);
        assertEquals(0.5f, rotation.getPivot(), 1e-4);
        assertEquals(0.6f, rotation.getUpDown(), 1e-4);
        assertEquals(0.7f, rotation.getLeftRight(), 1e-4);
    }

    @Test
    void testConstructorWithBounds() {
        RelativeRotation rotation = new RelativeRotation(0.5f, (float) Math.PI, 0.7f);
        assertEquals(0.5f, rotation.getPivot(), 1e-4);
        assertEquals((float) Math.PI / 2, rotation.getUpDown(), 1e-4);
        assertEquals(0.7f, rotation.getLeftRight(), 1e-4);
    }

    @Test
    void testAdd() {
        RelativeRotation rotation1 = new RelativeRotation(0.5f, 0.6f, 0.7f);
        RelativeRotation rotation2 = new RelativeRotation(0.3f, 0.2f, 0.1f);
        RelativeRotation sum = rotation1.add(rotation2);

        assertEquals(0.8f, sum.getPivot(), 1e-4);
        assertEquals(0.4f, sum.getUpDown(), 1e-4);
        assertEquals(0.8f, sum.getLeftRight(), 1e-4);
    }

    @Test
    void testGetNegRotation() {
        RelativeRotation rotation = new RelativeRotation(0.5f, 0.6f, 0.7f);
        RelativeRotation negRotation = rotation.getNegRotation();

        assertEquals(-0.5f, negRotation.getPivot(), 1e-4);
        assertEquals(-0.6f, negRotation.getUpDown(), 1e-4);
        assertEquals(-0.7f, negRotation.getLeftRight(), 1e-4);
    }

    @Test
    void testEquals() {
        RelativeRotation rotation1 = new RelativeRotation(0.5f, 0.6f, 0.7f);
        RelativeRotation rotation2 = new RelativeRotation(0.5f, 0.6f, 0.7f);
        assertTrue(rotation1.equals(rotation2));
    }

    @Test
    void testNotEquals() {
        RelativeRotation rotation1 = new RelativeRotation(0.5f, 0.6f, 0.7f);
        RelativeRotation rotation2 = new RelativeRotation(0.3f, 0.6f, 0.7f);
        assertFalse(rotation1.equals(rotation2));
    }
}
