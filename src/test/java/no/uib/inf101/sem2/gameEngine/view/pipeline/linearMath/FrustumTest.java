package no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath;

import no.uib.inf101.sem2.gameEngine.model.collision.BoundingSphere;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.FaceTexture;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Projection;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.TranslateTransform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FrustumTest {

    private Frustum frustum;
    private Matrix projectionMatrix;
    private float near = 0.1f;
    private float far = 1000.0f;

    @BeforeEach
    void setUp() {
        projectionMatrix = new Projection(60, 16.0f / 9.0f, near, far).getMatrix();
        frustum = new Frustum(projectionMatrix, near, far);
    }

    @Test
    public void testIsShapeVisible() {

        // Create vertices for a cube
        ArrayList<GridPosition> vertices = new ArrayList<>();
        vertices.add(new Position3D(-1, -1, -1));
        vertices.add(new Position3D(1, -1, -1));
        vertices.add(new Position3D(1, 1, -1));
        vertices.add(new Position3D(-1, 1, -1));
        vertices.add(new Position3D(-1, -1, 1));
        vertices.add(new Position3D(1, -1, 1));
        vertices.add(new Position3D(1, 1, 1));
        vertices.add(new Position3D(-1, 1, 1));

        // Create faces for the cube
        ArrayList<Face> faces = new ArrayList<>();
        float[] uv = new float[]{0, 0, 1, 0, 1, 1, 0, 1};
        FaceTexture texture = new FaceTexture("COLOR", uv);

        faces.add(new Face(Arrays.asList(vertices.get(0), vertices.get(1), vertices.get(2), vertices.get(3)), texture));
        faces.add(new Face(Arrays.asList(vertices.get(4), vertices.get(5), vertices.get(6), vertices.get(7)), texture));
        faces.add(new Face(Arrays.asList(vertices.get(0), vertices.get(1), vertices.get(5), vertices.get(4)), texture));
        faces.add(new Face(Arrays.asList(vertices.get(2), vertices.get(3), vertices.get(7), vertices.get(6)), texture));
        faces.add(new Face(Arrays.asList(vertices.get(0), vertices.get(3), vertices.get(7), vertices.get(4)), texture));
        faces.add(new Face(Arrays.asList(vertices.get(1), vertices.get(2), vertices.get(6), vertices.get(5)), texture));

        // Create a Shape3D object
        Shape3D cube = new Shape3D(faces);

        // Test if a visible shape is reported as visible
        assertTrue(frustum.isShapeVisible(cube));
    }


    @Test
    void testClipFace() {
        FaceTexture textureInside = new FaceTexture("test", new float[]{0, 0, 1, 0, 1, 1, 0, 1});

        // Test face entirely outside the frustum
        ArrayList<GridPosition> facePointsInside = new ArrayList<>();
        facePointsInside.add(new Position3D(-1, -1, -5));
        facePointsInside.add(new Position3D(1, -1, -5));
        facePointsInside.add(new Position3D(1, 1, -5));
        facePointsInside.add(new Position3D(-1, 1, -5));
        Face faceInside = new Face(facePointsInside, textureInside);
        Face clippedFaceInside = frustum.clipFace(faceInside);
        ArrayList<GridPosition> expected = new ArrayList<>();
        assertEquals(expected, clippedFaceInside.getPoints());

        // Test face partially within the frustum
        ArrayList<GridPosition> facePointsPartiallyInside = new ArrayList<>();
        facePointsPartiallyInside.add(new Position3D(-1, -1, -5));
        facePointsPartiallyInside.add(new Position3D(1, -1, -5));
        facePointsPartiallyInside.add(new Position3D(10, 1, -5));
        facePointsPartiallyInside.add(new Position3D(-1, 1, -5));
        Face facePartiallyInside = new Face(facePointsPartiallyInside, textureInside);
        Face clippedFacePartiallyInside = frustum.clipFace(facePartiallyInside);
        assertFalse(clippedFacePartiallyInside.getPoints().size() > faceInside.getPoints().size());

        // Test face partially within the frustum
        ArrayList<GridPosition> facePointsOutside = new ArrayList<>();
        facePointsOutside.add(new Position3D(-1, -1, 1));
        facePointsOutside.add(new Position3D(1, -1, 1));
        facePointsOutside.add(new Position3D(1, 1, 1));
        facePointsOutside.add(new Position3D(-1, 1, 1));
        FaceTexture textureOutside = new FaceTexture("test", new float[]{0, 0, 1, 0, 1, 1, 0, 1});
        Face faceOutside = new Face(facePointsOutside, textureOutside);
        Face clippedFaceOutside = frustum.clipFace(faceOutside);
        assertFalse(clippedFaceOutside.getPoints().isEmpty());
    }
}

