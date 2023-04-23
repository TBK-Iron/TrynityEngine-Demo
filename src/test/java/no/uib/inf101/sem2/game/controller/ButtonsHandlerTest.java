package no.uib.inf101.sem2.game.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.game.model.levels.GrassWorld;
import no.uib.inf101.sem2.game.model.levels.HordeZ;
import no.uib.inf101.sem2.game.model.levels.LegendOfTheBeast;
import no.uib.inf101.sem2.game.model.levels.Level;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;

import java.util.ArrayList;

public class ButtonsHandlerTest {

    private ButtonsHandler buttonsHandler;
    private Config config;

    @BeforeEach
    public void setUp() {
        config = new DefaultConfig();
        buttonsHandler = new ButtonsHandler(config);
    }

    @Test
    public void testMainMenuButtons() {
        ArrayList<Button> mainMenuButtons = buttonsHandler.getMainMenuButtons();
        
        assertEquals(3, mainMenuButtons.size());
        assertEquals("Play", mainMenuButtons.get(0).getText());
        assertEquals("Settings", mainMenuButtons.get(1).getText());
        assertEquals("Quit", mainMenuButtons.get(2).getText());
    }

    @Test
    public void testLevelMenuButtons() {
        ArrayList<Button> levelMenuButtons = buttonsHandler.getLevelMenuButtons();
        
        assertEquals(3, levelMenuButtons.size());
        assertEquals("The Legend of The Beast", levelMenuButtons.get(0).getText());
        assertEquals("HordeZ", levelMenuButtons.get(1).getText());
        assertEquals("Grass World (WIP)", levelMenuButtons.get(2).getText());
    }

    @Test
    public void testPauseMenuButtons() {
        ArrayList<Button> pauseMenuButtons = buttonsHandler.getPauseMenuButtons();
        
        assertEquals(3, pauseMenuButtons.size());
        assertEquals("Resume", pauseMenuButtons.get(0).getText());
        assertEquals("Settings", pauseMenuButtons.get(1).getText());
        assertEquals("Main Menu", pauseMenuButtons.get(2).getText());
    }

    @Test
    public void testSettingsMenuButtons() {
        ArrayList<Button> settingsMenuButtons = buttonsHandler.getSettingsMenuButtons();
        
        assertEquals(5, settingsMenuButtons.size());
        assertEquals("Noclip: OFF", settingsMenuButtons.get(0).getText());
        assertEquals("Render Distance: MEDIUM", settingsMenuButtons.get(1).getText());
        assertEquals("Move Speed: MEDIUM", settingsMenuButtons.get(2).getText());
        assertEquals("FPS Counter: OFF", settingsMenuButtons.get(3).getText());
        assertEquals("Back", settingsMenuButtons.get(4).getText());
    }

    @Test
    public void testGetLevels() {
        ArrayList<Level> levels = buttonsHandler.getLevels();
        
        assertEquals(3, levels.size());
        assertTrue(levels.get(0) instanceof LegendOfTheBeast);
        assertTrue(levels.get(1) instanceof HordeZ);
        assertTrue(levels.get(2) instanceof GrassWorld);
    }
}