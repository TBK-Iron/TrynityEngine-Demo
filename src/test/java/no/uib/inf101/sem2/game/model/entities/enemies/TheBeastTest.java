package no.uib.inf101.sem2.game.model.entities.enemies;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public class TheBeastTest {

    @Test
    public void testIsWithinRadius() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Enemy beast = new TheBeast(startPosition, startRotation, 10);

        GridPosition posInsideRadius = new Position3D(5, 0, 5);
        GridPosition posOutsideRadius = new Position3D(20, 0, 20);

        assertTrue(beast.isWithinRadius(posInsideRadius));
        assertFalse(beast.isWithinRadius(posOutsideRadius));
    }

    @Test
    public void testIsAliveAndDamage() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Enemy beast = new TheBeast(startPosition, startRotation, 10);

        assertTrue(beast.isAlive());
        beast.damage(10);
        assertTrue(beast.isAlive());

        beast.damage(TheBeast.START_HEALTH);
        assertFalse(beast.isAlive());
    }

    @Test
    public void testDamageTo() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Enemy beast = new TheBeast(startPosition, startRotation, 10);

        GridPosition entityPosInsideRadius = new Position3D(0, 0, 5);
        GridPosition entityPosOutsideRadius = new Position3D(0, 0, 10);

        assertTrue(beast.damageTo(entityPosInsideRadius) > 0);
        assertTrue(beast.damageTo(entityPosOutsideRadius) == 0);
    }

    @Test
    public void testSetTargetPosition() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Enemy beast = new TheBeast(startPosition, startRotation, 10);

        GridPosition targetPos = new Position3D(5, 0, 5);
        beast.setTargetPosition(targetPos);

        assertEquals(targetPos, beast.getEntity().getTargetPosition());
    }
}
