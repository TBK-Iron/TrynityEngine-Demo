package no.uib.inf101.sem2.gameEngine.model.collision;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

class CollisionBoxTest {

    @Test
    void testConstructor() {
        GridPosition pos1 = new Position3D(0, 0, 0);
        GridPosition pos2 = new Position3D(1, 1, 1);
        CollisionBox box = new CollisionBox(pos1, pos2);

        assertEquals(pos1, box.pos1);
        assertEquals(pos2, box.pos2);
    }

    @Test
    void testIsColliding() {
        GridPosition pos1 = new Position3D(0, 0, 0);
        GridPosition pos2 = new Position3D(1, 1, 1);
        CollisionBox box1 = new CollisionBox(pos1, pos2);

        GridPosition pos3 = new Position3D(0.5f, 0.5f, 0.5f);
        GridPosition pos4 = new Position3D(1.5f, 1.5f, 1.5f);
        CollisionBox box2 = new CollisionBox(pos3, pos4);

        GridPosition dispPos = new Position3D(0, 0, 0);

        assertTrue(box1.isColliding(box2, dispPos));

        dispPos = new Position3D(2, 2, 2);
        assertFalse(box1.isColliding(box2, dispPos));
    }

    @Test
    void testGetCollisionPos() {
        GridPosition pos1 = new Position3D(0, 0, 0);
        GridPosition pos2 = new Position3D(1, 1, 1);
        CollisionBox box1 = new CollisionBox(pos1, pos2);

        GridPosition pos3 = new Position3D(0, 0, 0);
        GridPosition pos4 = new Position3D(1, 1, 1);
        CollisionBox box2 = new CollisionBox(pos3, pos4);

        GridPosition beforePos = new Position3D(0, 2, 0);
        GridPosition afterPos = new Position3D(0, 0.5f, 0);
        GridPosition expectedCollisionPos = new Position3D(0, 1+CollisionBox.MARGIN, 0);

        assertEquals(expectedCollisionPos, box1.getCollisionPos(box2, beforePos, afterPos));
    }

}
