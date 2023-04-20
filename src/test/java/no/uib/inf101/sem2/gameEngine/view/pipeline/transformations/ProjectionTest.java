package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.FaceTexture;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

class ProjectionTest {

    @Test
    void testProjectionTransformation() {
        // Create a face with three vertices
        List<GridPosition> points = new ArrayList<>();
        points.add(new Position3D(1, 1, 1));
        points.add(new Position3D(1, -1, 1));
        points.add(new Position3D(-1, -1, 1));
        FaceTexture texture = new FaceTexture("test", new float[]{0, 0, 1, 0, 1, 1});
        Face face = new Face(points, texture);

        // Create a projection transformation
        float fov = (float) Math.toRadians(60);
        float aspectRatio = 16f / 9f;
        float near = 0.1f;
        float far = 100f;
        Projection projection = new Projection(fov, aspectRatio, near, far);

        // Transform the face
        Face transformedFace = projection.transform(face);

        // Check if the transformed face has the correct number of vertices
        assertEquals(face.getPoints().size(), transformedFace.getPoints().size());

        //TODO: Add assertions for the transformed vertices
        // Add assertions for the transformed vertices if necessary, e.g.:
        // assertEquals(expectedX, transformedFace.getPoints().get(0).x(), 0.001);
        // assertEquals(expectedY, transformedFace.getPoints().get(0).y(), 0.001);
        // assertEquals(expectedZ, transformedFace.getPoints().get(0).z(), 0.001);
    }
}
