package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.FaceTexture;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position4D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectionTest {

    private Projection projection;
    private float fov;
    private float aspectRatio;
    private float near;
    private float far;

    @BeforeEach
    public void setUp() {
        fov = (float) Math.toRadians(90);
        aspectRatio = 16f / 9f;
        near = 0.1f;
        far = 100f;
        projection = new Projection(fov, aspectRatio, near, far);
    }

    @Test
    public void testProjectionMatrix() {
        float tanHalfFov = (float) Math.tan(fov / 2);

        Matrix expectedMatrix = new Matrix(new float[][]{
                {1 / (aspectRatio * tanHalfFov), 0, 0, 0},
                {0, 1 / tanHalfFov, 0, 0},
                {0, 0, -(far + near) / (far - near), -1},
                {0, 0, -(2 * far * near) / (far - near), 0}
        });

        assertEquals(expectedMatrix, projection.getMatrix());
    }

    @Test
    public void testTransform() {
        GridPosition p1 = new Position3D(1, 1, 5);
        GridPosition p2 = new Position3D(1, -1, 1);
        GridPosition p3 = new Position3D(-1, -1, 3);
        GridPosition p4 = new Position3D(-1, 1, 2);

        ArrayList<GridPosition> vertices = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
        FaceTexture texture = new FaceTexture("testTexture", new float[]{0, 0, 0, 1, 1, 1, 1, 0});
        Face face = new Face(vertices, texture);

        Face transformedFace = projection.transform(face);

        GridPosition t1 = new Position4D(0.5625f, 1, 5, -1.001001f);
        GridPosition t2 = new Position4D(0.5625f, -1, 1, -0.2002002f);
        GridPosition t3 = new Position4D(-0.5625f, -1, 3, -0.6006006f);
        GridPosition t4 = new Position4D(-0.5625f, 1, 2, -0.4004004f);

        ArrayList<GridPosition> transformedVertices = new ArrayList<>(Arrays.asList(t1, t2, t3, t4));

        Face expectedTransformedFace = new Face(transformedVertices, face.getTexture());
        assertEquals(expectedTransformedFace, transformedFace);
    }
}
