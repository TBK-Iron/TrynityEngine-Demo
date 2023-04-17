/* package no.uib.inf101.sem2.gameEngine.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Canvas;
import java.awt.event.KeyEvent;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MoveHandlerTest {
    private MoveHandler moveHandler;

    @BeforeEach
    public void setUp() {
        Config config = new DefaultConfig();
        moveHandler = new MoveHandler(config);
    }

    @Test
    public void testKeyPressed() {
        Canvas source = new Canvas();
        KeyEvent press = new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        assertTrue(moveHandler.keyPressed(press));
        assertTrue(moveHandler.wKeyPressed);
    }

    @Test
    public void testKeyReleased() {
        Canvas source = new Canvas();
        KeyEvent release = new KeyEvent(source, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        moveHandler.wKeyPressed = true;
        assertTrue(moveHandler.keyReleased(release));
        assertFalse(moveHandler.wKeyPressed);
    }

    @Test
    public void testGetMovementDelta() {
        moveHandler.wKeyPressed = true;
        moveHandler.aKeyPressed = true;
        Vector movementDelta = moveHandler.getMovementDelta();
        assertEquals(0.0636396f, movementDelta.get(0), 0.0001);
        assertEquals(0, movementDelta.get(1), 0.0001);
        assertEquals(0.0636396f, movementDelta.get(2), 0.0001);
    }
} */
