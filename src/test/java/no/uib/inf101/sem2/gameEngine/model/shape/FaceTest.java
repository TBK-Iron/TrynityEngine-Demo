package no.uib.inf101.sem2.gameEngine.model.shape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FaceTest {
	@Test
	void testGetThreeVertexFaces_Triple() {
		ArrayList<GridPosition> points = new ArrayList<>(Arrays.asList(
				new Position3D(0, 0, 0),
				new Position3D(1, 0, 0),
				new Position3D(0, 1, 0)));
		FaceTexture texture = new FaceTexture("testTexture", new float[] { 0f, 0f, 1f, 0f, 0f, 1f });
		Face face = new Face(points, texture);

		ArrayList<Face> result = face.getThreeVertexFaces();
		assertEquals(1, result.size());
		assertEquals(face, result.get(0));
	}

	@Test
	void testGetThreeVertexFaces_Quad() {
		ArrayList<GridPosition> points = new ArrayList<>(Arrays.asList(
				new Position3D(0, 0, 0),
				new Position3D(1, 0, 0),
				new Position3D(1, 1, 0),
				new Position3D(0, 1, 0)));
		FaceTexture texture = new FaceTexture("testTexture", new float[] { 0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f });
		Face face = new Face(points, texture);

		ArrayList<Face> result = face.getThreeVertexFaces();
		assertEquals(2, result.size());

		Face expectedFace1 = new Face(new ArrayList<>(Arrays.asList(
				new Position3D(0, 0, 0),
				new Position3D(1, 0, 0),
				new Position3D(1, 1, 0))),
				new FaceTexture("testTexture", new float[] { 0f, 0f, 1f, 0f, 1f, 1f }));

		Face expectedFace2 = new Face(new ArrayList<>(Arrays.asList(
				new Position3D(0, 0, 0),
				new Position3D(1, 1, 0),
				new Position3D(0, 1, 0))),
				new FaceTexture("testTexture", new float[] { 0f, 0f, 1f, 1f, 0f, 1f }));

		// Uses the contains method for arraylist, which uses the equals method in the
		// face class to check if a face is in the list.
		assertTrue(result.contains(expectedFace1));
		assertTrue(result.contains(expectedFace2));
	}

	@Test
	void testGetThreeVertexFaces_Penta() {
		ArrayList<GridPosition> points = new ArrayList<>(Arrays.asList(
				new Position3D(0, 0, 0),
				new Position3D(1, 0, 0),
				new Position3D(1, 1, 0),
				new Position3D(0, 1, 0),
				new Position3D(-1, 0, 0)));
		FaceTexture texture = new FaceTexture("testTexture",
				new float[] { 0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f, -1f, 0f });
		Face face = new Face(points, texture);

		ArrayList<Face> result = face.getThreeVertexFaces();
		assertEquals(3, result.size());

		Face expectedFace1 = new Face(new ArrayList<>(Arrays.asList(
				new Position3D(0, 0, 0),
				new Position3D(1, 0, 0),
				new Position3D(1, 1, 0))),
				new FaceTexture("testTexture", new float[] { 0f, 0f, 1f, 0f, 1f, 1f }));

		Face expectedFace2 = new Face(new ArrayList<>(Arrays.asList(
				new Position3D(0, 0, 0),
				new Position3D(1, 1, 0),
				new Position3D(0, 1, 0))),
				new FaceTexture("testTexture", new float[] { 0f, 0f, 1f, 1f, 0f, 1f }));

		Face expectedFace3 = new Face(new ArrayList<>(Arrays.asList(
				new Position3D(0, 0, 0),
				new Position3D(0, 1, 0),
				new Position3D(-1, 0, 0))),
				new FaceTexture("testTexture", new float[] { 0f, 0f, 0f, 1f, -1f, 0f }));

		// Uses the contains method for arraylist, which uses the equals method in the
		// face class to check if a face is in the list.
		assertTrue(result.contains(expectedFace1));
		assertTrue(result.contains(expectedFace2));
		assertTrue(result.contains(expectedFace3));
	}

	private Face face;
    private List<GridPosition> points;
    private FaceTexture texture;

	@BeforeEach
	public void setUp() {
		points = new ArrayList<>();
		points.add(new Position3D(0, 0, 0));
		points.add(new Position3D(1, 0, 0));
		points.add(new Position3D(0, 1, 0));

		float[] uv = new float[] { 0, 0, 1, 0, 0, 1 };
		texture = new FaceTexture("test_texture", uv);

		face = new Face(points, texture);
	}

    @Test
    public void testGetPoints() {
        assertEquals(points, face.getPoints());
    }

    @Test
    public void testGetTexture() {
        assertEquals(texture, face.getTexture());
    }

    @Test
    public void testSize() {
        assertEquals(points.size(), face.size());
    }

    @Test
    public void testGetNormalVector() {
        Vector normal = face.getNormalVector();
        assertEquals(0, normal.get(0));
        assertEquals(0, normal.get(1));
        assertEquals(1, normal.get(2));
    }

    @Test
    public void testGetAABB_xy() {
        Vector[] aabb = face.getAABB_xy();
        Vector min = aabb[0];
        Vector max = aabb[1];

        assertEquals(0, min.get(0));
        assertEquals(0, min.get(1));
        assertEquals(1, max.get(0));
        assertEquals(1, max.get(1));
    }

    @Test
    public void testGetPointClosestToOrigin() {
        GridPosition closestPoint = face.getPointClosestToOrigin();
        assertEquals(points.get(0), closestPoint);
    }

}
