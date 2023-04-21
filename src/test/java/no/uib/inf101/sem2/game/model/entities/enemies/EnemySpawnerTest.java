package no.uib.inf101.sem2.game.model.entities.enemies;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public class EnemySpawnerTest {

    @Test
    public void testCanSpawn() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Zombie zombie = new Zombie(startPosition, startRotation);
        EnemySpawner spawner = new EnemySpawner(10, 5, zombie);

        assertTrue(spawner.canSpawn());
        assertEquals(4, spawner.getSpawnAmount());
    }

    @Test
    public void testGetNextEnemy() {
        GridPosition startPosition = new Position3D(0, 0, 0);
        RelativeRotation startRotation = new RelativeRotation(0, 0);
        Zombie zombie = new Zombie(startPosition, startRotation);
        EnemySpawner spawner = new EnemySpawner(10, 1, zombie);

        Enemy nextEnemy = spawner.getNextEnemy();

        assertNotNull(nextEnemy);
        assertTrue(nextEnemy instanceof Zombie);
        assertEquals(0, spawner.getSpawnAmount());
    }
}
