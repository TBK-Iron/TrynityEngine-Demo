package no.uib.inf101.sem2.gameEngine.model;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CameraTest {

    @Test
    public void testCameraInitialization() {
        GridPosition startPos = new Position3D(0, 0, 0);
        RelativeRotation startRot = new RelativeRotation(0, 0, 0);
        Camera camera = new Camera(startPos, startRot);

        assertEquals(startPos, camera.getPos());
        assertEquals(startRot, camera.getRotation());
        assertNull(camera.getCollisionBox());
    }

    @Test
    public void testSetPosition() {
        GridPosition startPos = new Position3D(0, 0, 0);
        RelativeRotation startRot = new RelativeRotation(0, 0, 0);
        Camera camera = new Camera(startPos, startRot);

        GridPosition newPos = new Position3D(1, 1, 1);
        camera.setPos(newPos);

        assertEquals(newPos, camera.getPos());
    }

    @Test
    public void testSetRotation() {
        GridPosition startPos = new Position3D(0, 0, 0);
        RelativeRotation startRot = new RelativeRotation(0, 0, 0);
        Camera camera = new Camera(startPos, startRot);

        RelativeRotation newRot = new RelativeRotation((float) Math.PI, (float) Math.PI / 2, 0);
        camera.setRotation(newRot);

        assertEquals(newRot, camera.getRotation());
    }

    @Test
    public void testSetCollisionBox() {
        GridPosition startPos = new Position3D(0, 0, 0);
        RelativeRotation startRot = new RelativeRotation(0, 0, 0);
        Camera camera = new Camera(startPos, startRot);

        GridPosition minPos = new Position3D(-1, -1, -1);
        GridPosition maxPos = new Position3D(1, 1, 1);
        CollisionBox box = new CollisionBox(minPos, maxPos);
        camera.setCollision(box);

        assertEquals(box, camera.getCollisionBox());
    }
}
