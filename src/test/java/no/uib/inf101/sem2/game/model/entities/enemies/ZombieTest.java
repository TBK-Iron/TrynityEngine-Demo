package no.uib.inf101.sem2.game.model.entities.enemies;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public class ZombieTest {

    @Test
    public void testIsAlive() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Zombie zombie = new Zombie(startPosition, startRotation);

        assertTrue(zombie.isAlive());
        zombie.damage(Zombie.START_HEALTH);
        assertFalse(zombie.isAlive());
    }

    @Test
    public void testClone() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Zombie originalZombie = new Zombie(startPosition, startRotation);
        Zombie clonedZombie = (Zombie) originalZombie.clone();

        assertNotNull(clonedZombie);
        assertNotSame(originalZombie, clonedZombie);
        assertEquals(originalZombie.getPosition(), clonedZombie.getPosition());
        assertEquals(originalZombie.getEntity(), clonedZombie.getEntity());
    }

    @Test
    public void testIsWithinRadius() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Zombie zombie = new Zombie(startPosition, startRotation);

        GridPosition camPosInsideRadius = new Position3D(3, 0, 0);
        GridPosition camPosOutsideRadius = new Position3D(10, 0, 0);

        assertTrue(zombie.isWithinRadius(camPosInsideRadius));
        assertFalse(zombie.isWithinRadius(camPosOutsideRadius));
    }

    @Test
    public void testDamage() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Zombie zombie = new Zombie(startPosition, startRotation);

        zombie.damage(10);
        assertTrue(zombie.isAlive());

        zombie.damage(Zombie.START_HEALTH);
        assertFalse(zombie.isAlive());
    }

    @Test
    public void testDamageTo() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Zombie zombie = new Zombie(startPosition, startRotation);

        GridPosition entityPosInsideRadius = new Position3D(0, 0, 0.5f);
        GridPosition entityPosOutsideRadius = new Position3D(0, 0, 1);

        assertTrue(zombie.damageTo(entityPosInsideRadius) > 0);
        assertTrue(zombie.damageTo(entityPosOutsideRadius) == 0);
    }

    @Test
    public void testSetTargetPosition() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Zombie zombie = new Zombie(startPosition, startRotation);

        GridPosition targetPos = new Position3D(5, 0, 5);
        zombie.setTargetPosition(targetPos);

        assertEquals(targetPos, zombie.getEntity().getTargetPosition());
    }
}
