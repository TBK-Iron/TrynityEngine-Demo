package no.uib.inf101.sem2.gameEngine.model.collision;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

public class CollisionDetectorTest {

    private CollisionDetector detector;
    private CollisionBox box1;
    private CollisionBox box2;
    private CollisionBox box3;

    @BeforeEach
    public void setUp() {
        detector = new CollisionDetector();

        GridPosition pos1A = new Position3D(0, 0, 0);
        GridPosition pos1B = new Position3D(2, 2, 2);
        box1 = new CollisionBox(pos1A, pos1B);

        GridPosition pos2A = new Position3D(3, 3, 3);
        GridPosition pos2B = new Position3D(5, 5, 5);
        box2 = new CollisionBox(pos2A, pos2B);

        GridPosition pos3A = new Position3D(1, 1, 1);
        GridPosition pos3B = new Position3D(4, 4, 4);
        box3 = new CollisionBox(pos3A, pos3B);

        detector.addCollisionBox(box1);
        detector.addCollisionBox(box2);
    }

    @Test
    public void testGetCollidingBox_NoCollision() {
        GridPosition anchoredPos = new Position3D(6, 6, 6);
        assertNull(detector.getCollidingBox(box3, anchoredPos));
    }

    @Test
    public void testGetCollidingBox_CollisionWithBox1() {
        GridPosition anchoredPos = new Position3D(1, 1, 1);
        assertEquals(box1, detector.getCollidingBox(box3, anchoredPos));
    }

    @Test
    public void testGetCollidingBox_CollisionWithBox2() {
        GridPosition anchoredPos = new Position3D(2, 2, 2);
        assertEquals(box2, detector.getCollidingBox(box3, anchoredPos));
    }
}

