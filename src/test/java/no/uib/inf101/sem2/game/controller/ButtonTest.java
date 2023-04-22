package no.uib.inf101.sem2.game.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.geom.Rectangle2D;

public class ButtonTest {

    @Test
    public void testButtonConstruction() {
        Button button = new Button(100, 100, 50, 30, "Test");
        
        assertEquals(100, button.getCenterX());
        assertEquals(100, button.getCenterY());
        assertEquals("Test", button.getText());
        assertEquals(new Rectangle2D.Double(75, 85, 50, 30), button.getRect());
    }

    @Test
    public void testIsClicked() {
        Button button = new Button(100, 100, 50, 30, "Test");
        
        assertTrue(button.isClicked(75, 86));
        assertTrue(button.isClicked(115, 90));
        assertTrue(button.isClicked(99, 99));
        
        assertFalse(button.isClicked(49, 70));
        assertFalse(button.isClicked(126, 100));
        assertFalse(button.isClicked(25, 85));
    }

    @Test
    public void testSetText() {
        Button button = new Button(100, 100, 50, 30, "Test");
        button.setText("New Text");
        assertEquals("New Text", button.getText());
    }
}
