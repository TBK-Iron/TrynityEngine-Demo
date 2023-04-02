package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class RotateTransformTest {

    @Test
    public void testGetRotationMatrix(){
        Vector testV = new Vector(new float[]{1, 1, 1});

        //Test no rotation
        RotateTransform rotTrans1 = new RotateTransform(new RelativeRotation(0, 0));
        Matrix expectedMatrix1 = new Matrix(new float[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        });

        assertEquals(expectedMatrix1, rotTrans1.getMatrix());
        assertEquals(expectedMatrix1.multiply(testV), rotTrans1.getMatrix().multiply(testV));

        //Test 90 degrees rotation
        RotateTransform rotTrans2 = new RotateTransform(new RelativeRotation(0, (float) Math.PI/2));
        Matrix expectedMatrix2 = new Matrix(new float[][]{
            {0, 0, 1},
            {0, 1, 0},
            {-1, 0, 0}
        });

        assertEquals(expectedMatrix2, rotTrans2.getMatrix());
        assertEquals(expectedMatrix2.multiply(testV), rotTrans2.getMatrix().multiply(testV));

        //Test 90 degrees up rotation
        RotateTransform rotTrans3 = new RotateTransform(new RelativeRotation((float) Math.PI/2, 0));
        Matrix expectedMatrix3 = new Matrix(new float[][]{
            {1, 0, 0},
            {0, 0, 1},
            {0, -1, 0}
        });

        assertEquals(expectedMatrix3, rotTrans3.getMatrix());
        assertEquals(expectedMatrix3.multiply(testV), rotTrans3.getMatrix().multiply(testV));


        //Test 45 degree leftright and 45 degree updown rotation
        RotateTransform rotTrans4 = new RotateTransform(new RelativeRotation((float) Math.PI/4, (float) Math.PI/4));
        //Fix this matrix
        Matrix expectedMatrix4 = new Matrix(new float[][]{
            {0.70710677f, 0, 0.70710677f},
            {0.35355338f, 0.70710677f, -0.35355338f},
            {-0.6123724f, 0.70710677f, 0.6123724f}
        });

        assertEquals(expectedMatrix3, rotTrans3.getMatrix());
        assertEquals(expectedMatrix3.multiply(testV), rotTrans3.getMatrix().multiply(testV));
    }
}
