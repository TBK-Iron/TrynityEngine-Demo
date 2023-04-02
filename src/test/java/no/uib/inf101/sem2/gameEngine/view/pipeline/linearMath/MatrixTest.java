package no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath;

import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

    @Test
    public void testMultiply() {
        Matrix m1 = new Matrix(new float[][] {{1, 2}, {3, 4}});
        Matrix m2 = new Matrix(new float[][] {{5, 6}, {7, 8}});
        Matrix result1 = Matrix.multiply(m1, m2);
        assertEquals(new Matrix(new float[][] {{19, 22}, {43, 50}}), result1);

        Matrix m3 = new Matrix(new float[][] {
            {5, 2, 7},
            {1, -8, 0.5f},
            {4, -1, 7}
        });
        Matrix m4 = new Matrix(new float[][] {
            {-1, -5, 3.68f},
            {1.5f, 5, -8},
            {4.5f, 2, -1}
        });

        Matrix result2 = new Matrix(new float[][] {
            {29.5f, -1, -4.6f},
            {-10.75f, -44, 67.18f},
            {26, -11, 15.72f}
        });

        assertEquals(Matrix.multiply(m3, m4), result2);
    }

    @Test
    public void testMatrixVectorMultiplication() {
        Matrix m = new Matrix(new float[][] {{1, 2, 3}, {4, 5, 6}});
        Vector v = new Vector(new float[] {7, 8, 9});
        Vector result = m.multiply(v);
        assertEquals(new Vector(new float[] {50, 122}), result);
    }

    /*@Test
    public void testGetRotationMatrix() {
        Rotation rot = new Rotation((float) Math.PI/2, 0, (float) Math.PI);
        Matrix rotMatrix = Matrix.getRotationMatrix(rot);
        Matrix expected = new Matrix(new float[][] {
            {-1, 0, 0},
            {0, 0, -1},
            {0, -1, 0}
        });
        assertEquals(expected, rotMatrix);
    }*/

    @Test
    public void testEquals() {
        Matrix m1 = new Matrix(new float[][] {{1, 2, 3}, {4, 5, 6}});
        Matrix m2 = new Matrix(new float[][] {{1, 2, 3}, {4, 5, 6}});
        assertEquals(m1, m2);
    }
}
