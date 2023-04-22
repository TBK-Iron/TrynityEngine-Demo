package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.FaceTexture;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class RotateTransformTest {

    @Test
    public void testGetRotationMatrix(){
        Vector testV = new Vector(new float[]{1, 1, 1});

        //Test no rotation
        RotateTransform rotTrans1 = new RotateTransform(new RelativeRotation(0, 0), false);
        Matrix expectedMatrix1 = new Matrix(new float[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        });

        assertEquals(expectedMatrix1, rotTrans1.getMatrix());
        assertEquals(expectedMatrix1.multiply(testV), rotTrans1.getMatrix().multiply(testV));

        //Test 90 degrees rotation
        RotateTransform rotTrans2 = new RotateTransform(new RelativeRotation(0, (float) Math.PI/2), false);
        Matrix expectedMatrix2 = new Matrix(new float[][]{
            {0, 0, 1},
            {0, 1, 0},
            {-1, 0, 0}
        });

        assertEquals(expectedMatrix2, rotTrans2.getMatrix());
        assertEquals(expectedMatrix2.multiply(testV), rotTrans2.getMatrix().multiply(testV));

        //Test 90 degrees up rotation
        RotateTransform rotTrans3 = new RotateTransform(new RelativeRotation((float) Math.PI/2, 0), false);
        Matrix expectedMatrix3 = new Matrix(new float[][]{
            {1, 0, 0},
            {0, 0, 1},
            {0, -1, 0}
        });

        assertEquals(expectedMatrix3, rotTrans3.getMatrix());
        assertEquals(expectedMatrix3.multiply(testV), rotTrans3.getMatrix().multiply(testV));


        //Test 45 degree leftright and 45 degree updown rotation
        RotateTransform rotTrans4 = new RotateTransform(new RelativeRotation((float) Math.PI/4, (float) Math.PI/4), false);
        //Fix this matrix
        Matrix expectedMatrix4 = new Matrix(new float[][]{
            {0.70710677f, -0.49999997f, 0.49999997f},
            {0.0f, 0.70710677f, 0.70710677f},
            {-0.70710677f, -0.49999997f, 0.49999997f}
        });

        assertEquals(expectedMatrix4, rotTrans4.getMatrix());
        assertEquals(expectedMatrix4.multiply(testV), rotTrans4.getMatrix().multiply(testV));
    }

    @Test
    public void testTransform() {
        RelativeRotation rotation = new RelativeRotation(0.5f, 0.5f, 0.5f);
        boolean inverse = false;
        RotateTransform rotateTransform = new RotateTransform(rotation, inverse);

        GridPosition p1 = new Position3D(1, 1, 1);
        GridPosition p2 = new Position3D(1, -1, 1);
        GridPosition p3 = new Position3D(-1, -1, 1);
        GridPosition p4 = new Position3D(-1, 1, 1);

        ArrayList<GridPosition> vertices = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
        FaceTexture texture = new FaceTexture("testTexture", new float[]{0, 0, 0, 1, 1, 1, 1, 0});
        Face face = new Face(vertices, texture);

        Face transformedFace = rotateTransform.transform(face);

        GridPosition t1 = new Position3D(0.45824435f, 1.6703122f, 0.008322954f);
        GridPosition t2 = new Position3D(1.7031381f, 0.13000992f, 0.28708553f);
        GridPosition t3 = new Position3D(0.38322666f, -0.71146107f, 1.5319793f);
        GridPosition t4 = new Position3D(-0.86166716f, 0.8288412f, 1.2532167f);

        ArrayList<GridPosition> transformedVertices = new ArrayList<>(Arrays.asList(t1, t2, t3, t4));

        Face expectedTransformedFace = new Face(transformedVertices, face.getTexture());
        assertEquals(expectedTransformedFace, transformedFace);
    }
}
