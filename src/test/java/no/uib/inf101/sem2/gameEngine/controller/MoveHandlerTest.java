package no.uib.inf101.sem2.gameEngine.controller;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Component;
import java.awt.event.KeyEvent;
import static org.junit.jupiter.api.Assertions.*;

public class MoveHandlerTest {

    private MoveHandler moveHandler;
    private Config config;
    private Component dummyComponent;

    @BeforeEach
    public void setUp() {
        // Initialize a Config object with default values
        config = new DefaultConfig();
        moveHandler = new MoveHandler(config);
        dummyComponent = new Component() {};
    }

    @Test
    public void testKeyPressedAndKeyReleased() {
        KeyEvent wKey = new KeyEvent(dummyComponent,  KeyEvent.KEY_PRESSED, (long) 0, 0, KeyEvent.VK_W, 'W');

        assertTrue(moveHandler.keyPressed(wKey));
        assertFalse(moveHandler.keyPressed(wKey));
        assertTrue(moveHandler.keyReleased(wKey));
    }

    @Test
    public void testGetMovementDelta() {
        KeyEvent pressW = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, (long) 0, 0, KeyEvent.VK_W, 'W');
        KeyEvent pressD = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, (long) 0, 0, KeyEvent.VK_D, 'D');

        moveHandler.keyPressed(pressW);

        //Test straight movement
        Vector expected = new Vector(new float[]{0, 0, config.cameraMoveSpeed()});
        assertEquals(expected, moveHandler.getMovementDelta());

        moveHandler.keyPressed(pressD);

        float diagonalSpeedComponent = (float) (config.cameraMoveSpeed() / Math.sqrt(2));

        //Test diagonal movement
        expected = new Vector(new float[]{-diagonalSpeedComponent, 0, diagonalSpeedComponent});
        assertEquals(expected, moveHandler.getMovementDelta());
    }
}