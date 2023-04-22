package no.uib.inf101.sem2.game.model.entities;

import static org.junit.jupiter.api.Assertions.*;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
    private Player player;
    private final GridPosition startPos = new Position3D(0, 0, 0);
    private final RelativeRotation startRot = new RelativeRotation(0, 0, 0);
    private final CollisionBox hitBox = new CollisionBox(new Position3D(-0.5f, 0.5f, -0.5f), new Position3D(0.5f, -1.999f, 0.5f));

    @BeforeEach
    void setUp() {
        player = new Player(startPos, startRot, hitBox);
    }

    @Test
    void testResetPlayer() {
        player.takeDamage(50);
        player.resetPlayer();
        assertEquals(1.0f, player.getHealthPercent(), "Player health should be reset to 100%");
        assertEquals(startPos, player.getCamera().getPos(), "Player position should be reset");
        assertEquals(startRot, player.getCamera().getRotation(), "Player rotation should be reset");
    }

    @Test
    void testTakeDamage() {
        player.takeDamage(30);
        assertEquals(0.7f, player.getHealthPercent(), "Player health should be 70%");
        assertTrue(player.isAlive(), "Player should be alive");
        player.takeDamage(80);
        assertEquals(-0.1f, player.getHealthPercent(), "Player health should be -10%");
        assertFalse(player.isAlive(), "Player should be dead");
    }

    @Test
    void testDistanceToHit() {
        // Player's position is (0, 0, 0) and looking straight along the z-axis.

        CollisionBox hitBox1 = new CollisionBox(new Position3D(-2, -1, 2), new Position3D(2, 3, 3));
        CollisionBox hitBox2 = new CollisionBox(new Position3D(5, 0, -2), new Position3D(9, 3, 2));

        float distance1 = player.distanceToHit(hitBox1);
        float distance2 = player.distanceToHit(hitBox2);

        // The player should be closer to hitBox1 than hitBox2.
        assertEquals(2.0f, distance1, 0.0001f);
        assertEquals(Float.MAX_VALUE, distance2);
    }
}