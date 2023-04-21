package no.uib.inf101.sem2.game.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

import static org.junit.jupiter.api.Assertions.*;

class DoorTest {
    private Door door;
    private GridPosition startPosition;
    private RelativeRotation startRotation;
    private float activationRadius;

    @BeforeEach
    void setUp() {
        startPosition = new Position3D(0, 0, 0);
        startRotation = new RelativeRotation(0, 0);
        activationRadius = 5;
        door = new Door(startPosition, startRotation, activationRadius);
    }

    @Test
    void isWithinRadius() {
        GridPosition camPosInRadius = new Position3D(3, 0, 3);
        GridPosition camPosOutsideRadius = new Position3D(10, 0, 10);

        assertTrue(door.isWithinRadius(camPosInRadius), "Door should be within activation radius");
        assertFalse(door.isWithinRadius(camPosOutsideRadius), "Door should be outside of activation radius");
    }

    @Test
    void getEntity() {
        assertNotNull(door.getEntity(), "Door entity should not be null");
    }

    @Test
    void cycleState() {
        door.cycleState();
        assertTrue(door.getEntity().getTargetPosition().y() > startPosition.y(), "Door should be open");

        door.cycleState();
        assertEquals(startPosition, door.getEntity().getTargetPosition(), "Door should be closed");
    }

    @Test
    void open() {
        door.open();
        assertTrue(door.getEntity().getTargetPosition().y() > startPosition.y(), "Door should be open");
    }

    @Test
    void close() {
        door.open();
        door.close();
        assertEquals(startPosition, door.getEntity().getTargetPosition(), "Door should be closed");
    }
}

