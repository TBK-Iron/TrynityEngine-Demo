package no.uib.inf101.sem2.gameEngine.view.raycaster;

import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CameraTest {
    private Camera camera;

    @BeforeEach
    void setUp() {
        // Initialize the camera with appropriate parameters.
        Position3D pos = new Position3D(0, 0, 0);
        RelativeRotation rot = new RelativeRotation(0.0, 0.0);
        double width = 800;
        double height = 600;
        double fov = Math.toRadians(60);

        //camera = new Camera(width, height, fov, pos, rot);
    }

    @Test
    public void testIsRendered() {
        // Test case 1: A vertex within the camera's field of view.
        Position3D vertex1 = new Position3D(0, 0, 5);
        //assertTrue(camera.isRendered(vertex1));

        // Test case 2: A vertex inside the camera's field of view
        Position3D vertex2 = new Position3D(-1, 1, 20);
        //assertTrue(camera.isRendered(vertex2));


        // Test case 3: A vertex outside the camera's field of view.
        Position3D vertex3 = new Position3D(20, 2, -1);
        //assertFalse(camera.isRendered(vertex3));

        //TODO: Add tests to check after camera has been rotated
    }
}