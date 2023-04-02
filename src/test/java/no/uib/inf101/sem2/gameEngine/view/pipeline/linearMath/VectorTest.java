package no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath;

import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {
    
    @Test
    public void testGetVectorRotation(){
        Vector v1 = new Vector(new float[] {0, 0, 1});
        RelativeRotation r1 = new RelativeRotation(0, 0);
        assertEquals(Vector.getVectorRotation(v1), r1);

        Vector v2 = new Vector(new float[] {0, 1, 0});
        RelativeRotation r2 = new RelativeRotation((float) Math.PI/2, 0);
        assertEquals(Vector.getVectorRotation(v2), r2);

        Vector v3 = new Vector(new float[] {0, 1, -1});
        RelativeRotation r3 = new RelativeRotation((float) Math.PI/4, (float) Math.PI);
        assertEquals(Vector.getVectorRotation(v3), r3);

    }

    @Test
    public void testAdd() {
        Vector v1 = new Vector(new float[] {1, 2, 3});
        Vector v2 = new Vector(new float[] {4, 5, 6});
        Vector v = Vector.add(v1, v2);
        assertEquals(new Vector(new float[] {5, 7, 9}), v);
    }

    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(new float[] {1, 2, 3});
        Vector v2 = new Vector(new float[] {4, 5, 6});
        Vector v = Vector.crossProduct(v1, v2);
        assertEquals(new Vector(new float[] {-3, 6, -3}), v);
    }

    @Test
    public void testDotProduct() {
        Vector v1 = new Vector(new float[] {1, 2, 3});
        Vector v2 = new Vector(new float[] {4, 5, 6});
        float result = Vector.dotProduct(v1, v2);
        assertEquals(32, result, 0.0001f);
    }

    @Test
    public void testNormalized() {
        Vector v = new Vector(new float[] {3, 4, 5});
        Vector normalized = v.normalized();
        assertEquals(1.0, normalized.magnitude(), 0.0001f);
    }

    @Test
    public void testScaledBy() {
        Vector v = new Vector(new float[] {1, 2, 3});
        Vector scaled = v.scaledBy(2);
        assertEquals(new Vector(new float[] {2, 4, 6}), scaled);
    }

    @Test
    public void testMagnitude() {
        Vector v = new Vector(new float[] {3, 4, 5});
        float magnitude = v.magnitude();
        assertEquals(Math.sqrt(50), magnitude, 0.0001);
    }

    @Test
    public void testGetPoint() {
        Vector v = new Vector(new float[] {1, 2, 3});
        GridPosition p = v.getPoint();
        assertEquals(new Position3D(1, 2, 3), p);
    }
}
