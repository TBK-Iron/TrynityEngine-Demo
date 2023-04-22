package no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

    private Matrix matrix1;
    private Matrix matrix2;
    private Matrix matrix4;
    private Matrix matrix5;

    @BeforeEach
    public void setUp() {
        float[][] values1 = {
                {1, 2},
                {3, 4}
        };
        float[][] values2 = {
                {5, 6},
                {7, 8}
        };
        float[][] values4 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        float[][] values5 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };

        matrix1 = new Matrix(values1);
        matrix2 = new Matrix(values2);
        matrix4 = new Matrix(values4);
        matrix5 = new Matrix(values5);
    }

    @Test
    public void testGet() {
        assertEquals(1, matrix1.get(0, 0));
        assertEquals(2, matrix1.get(0, 1));
        assertEquals(3, matrix1.get(1, 0));
        assertEquals(4, matrix1.get(1, 1));

        assertEquals(1, matrix4.get(0, 0));
        assertEquals(2, matrix4.get(0, 1));
        assertEquals(3, matrix4.get(0, 2));
        assertEquals(4, matrix4.get(1, 0));
        assertEquals(5, matrix4.get(1, 1));
        assertEquals(6, matrix4.get(1, 2));
        assertEquals(7, matrix4.get(2, 0));
        assertEquals(8, matrix4.get(2, 1));
        assertEquals(9, matrix4.get(2, 2));
    }

    @Test
    public void testMultiply() {
        Matrix result1 = Matrix.multiply(matrix1, matrix2);
        float[][] expectedValues1 = {
                {19, 22},
                {43, 50}
        };
        Matrix expected1 = new Matrix(expectedValues1);
        assertEquals(expected1, result1);

        Matrix result2 = Matrix.multiply(matrix4, matrix5);
        float[][] expectedValues2 = {
                {30, 24, 18},
                {84, 69, 54},
                {138, 114, 90}
        };
        Matrix expected2 = new Matrix(expectedValues2);
        assertEquals(expected2, result2);
    }

    @Test
    public void testMultiplyWithVector() {
        Vector vector1 = new Vector(new float[]{2, 3});
        Vector result1 = matrix1.multiply(vector1);
        Vector expected1 = new Vector(new float[]{8, 18});
        assertEquals(expected1, result1);

        Vector vector2 = new Vector(new float[]{1, 2, 3});
        Vector result2 = matrix4.multiply(vector2);
        Vector expected2 = new Vector(new float[]{14, 32, 50});
        assertEquals(expected2, result2);
    }

    @Test
    public void testAdd() {
        Matrix result1 = Matrix.add(matrix1,matrix2);
        float[][] expectedValues1 = {
                {6, 8},
                {10, 12}
        };
        Matrix expected1 = new Matrix(expectedValues1);
        assertEquals(expected1, result1);

        Matrix result2 = Matrix.add(matrix4, matrix5);
        float[][] expectedValues2 = {
                {10, 10, 10},
                {10, 10, 10},
                {10, 10, 10}
        };
        Matrix expected2 = new Matrix(expectedValues2);
        assertEquals(expected2, result2);
    }

    @Test
    public void testScaledBy() {
        Matrix result1 = matrix1.scaledBy(2);
        float[][] expectedValues1 = {
                {2, 4},
                {6, 8}
        };
        Matrix expected1 = new Matrix(expectedValues1);
        assertEquals(expected1, result1);

        Matrix result2 = matrix4.scaledBy(3);
        float[][] expectedValues2 = {
                {3, 6, 9},
                {12, 15, 18},
                {21, 24, 27}
        };
        Matrix expected2 = new Matrix(expectedValues2);
        assertEquals(expected2, result2);
    }

    @Test
    public void testIdentityMatrix() {
        Matrix identity2 = Matrix.identityMatrix(2);
        float[][] expectedValues1 = {
                {1, 0},
                {0, 1}
        };
        Matrix expected1 = new Matrix(expectedValues1);
        assertEquals(expected1, identity2);

        Matrix identity3 = Matrix.identityMatrix(3);
        float[][] expectedValues2 = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        Matrix expected2 = new Matrix(expectedValues2);
        assertEquals(expected2, identity3);
    }
}

