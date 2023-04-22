package no.uib.inf101.sem2.gameEngine.controller;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MouseHandlerTest {

    private MouseHandler mouseHandler;
    private Config config;

    @BeforeEach
    public void setUp() {
        // Initialize a Config object with default values
        config = new DefaultConfig();
        mouseHandler = new MouseHandler(config);
    }

    @Test
    public void testGetRotation() {
        Component dummyComponent = new Component() {};
        MouseEvent mouseEvent = new MouseEvent(dummyComponent, MouseEvent.MOUSE_MOVED, 0, 0,
                100, 100, 100, 100, 1, false, 0);

        RelativeRotation rotation = mouseHandler.getRotation(mouseEvent);
        float expectedUpDownRot = -0.29166666f;
        float expectedLeftRightRot = 0.382904f;

        assertEquals(expectedUpDownRot, rotation.getUpDown());
        assertEquals(expectedLeftRightRot, rotation.getLeftRight());
    }
}