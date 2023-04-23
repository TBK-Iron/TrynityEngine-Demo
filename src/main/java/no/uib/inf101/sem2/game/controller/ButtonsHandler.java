package no.uib.inf101.sem2.game.controller;

import java.util.ArrayList;

import no.uib.inf101.sem2.game.model.levels.GrassWorld;
import no.uib.inf101.sem2.game.model.levels.HordeZ;
import no.uib.inf101.sem2.game.model.levels.LegendOfTheBeast;
import no.uib.inf101.sem2.game.model.levels.Level;
import no.uib.inf101.sem2.game.model.resourceLoaders.SoundPlayer;
import no.uib.inf101.sem2.gameEngine.config.Config;

/**
 * Handles the creation and management of menu buttons in the game.
 */
public class ButtonsHandler {

    private ArrayList<Button> mainMenuButtons;
    private ArrayList<Button> levelMenuButtons;
    private ArrayList<Button> pauseMenuButtons;
    private ArrayList<Button> settingsMenuButtons;

    private Config config;
    private ArrayList<Level> levels;

    /**
     * Constructs a ButtonsHandler using the provided configuration.
     * 
     * @param config The game configuration.
     */
    public ButtonsHandler(Config config) {
        this.config = config;
        

        this.levels = new ArrayList<>();
        this.levels.add(new LegendOfTheBeast());
        this.levels.add(new HordeZ());
        this.levels.add(new GrassWorld());

        resetButtonHandler();

  
    }

    /**
     * Resets the button handler and re-creates all menu buttons.
     * This should be run when the screen size changes so that the buttons are correctly positioned and scaled.
     */
    public void resetButtonHandler(){
        createMainMenuButtons();
        createLevelMenuButtons();
        createPauseMenuButtons();
        createSettingsMenuButtons();
    }

    /**
     * Creates the main menu buttons.
     */
    private void createMainMenuButtons(){
        
        int sWidth = this.config.screenWidth();
        int sHeight = this.config.screenHeight();

        if(this.mainMenuButtons != null){
            this.mainMenuButtons.clear();
        } else {
            this.mainMenuButtons = new ArrayList<>();
        }
        this.mainMenuButtons.add(new Button(sWidth/2, sHeight/4, sWidth/2, sHeight/8, "Play"));
        this.mainMenuButtons.add(new Button(sWidth/2, sHeight/2, sWidth/2, sHeight/8, "Settings"));
        this.mainMenuButtons.add(new Button(sWidth/2, sHeight*3/4, sWidth/2, sHeight/8, "Quit"));
    }

    /**
     * Creates the level menu buttons.
     */
    private void createLevelMenuButtons(){
        
        int sWidth = this.config.screenWidth();
        int sHeight = this.config.screenHeight();

        if(this.levelMenuButtons != null){
            this.levelMenuButtons.clear();
        } else {
            this.levelMenuButtons = new ArrayList<>();
        }

        for(int i = 0; i < Math.min(this.levels.size(), 6); i++){
            if(i >= 3){
                this.levelMenuButtons.add(new Button(sWidth*3/4, sHeight*(i-2)/4, sWidth/2, sHeight/8, this.levels.get(i).getLevelName()));
            } else {
                this.levelMenuButtons.add(new Button(sWidth/4, sHeight*(i+1)/4, sWidth*3/7, sHeight/8, this.levels.get(i).getLevelName()));
            }
            
        }

        this.levelMenuButtons.add(new Button(sWidth/2, sHeight*29/32, sWidth*3/7, sHeight/8, "Back"));
    }
    
    /**
     * Creates the pause menu buttons.
     */
    private void createPauseMenuButtons(){
        
        int sWidth = this.config.screenWidth();
        int sHeight = this.config.screenHeight();

        if(this.pauseMenuButtons != null){
            this.pauseMenuButtons.clear();
        } else {
            this.pauseMenuButtons = new ArrayList<>();
        }

        this.pauseMenuButtons.add(new Button(sWidth/2, sHeight/4, sWidth/2, sHeight/8, "Resume"));
        this.pauseMenuButtons.add(new Button(sWidth/2, sHeight/2, sWidth/2, sHeight/8, "Settings"));
        this.pauseMenuButtons.add(new Button(sWidth/2, sHeight*3/4, sWidth/2, sHeight/8, "Main Menu"));    
    }

    /**
     * Creates the settings menu buttons.
     */
    private void createSettingsMenuButtons(){
        
        int sWidth = this.config.screenWidth();
        int sHeight = this.config.screenHeight();

        if(this.settingsMenuButtons == null){
            this.settingsMenuButtons = new ArrayList<>();

            this.settingsMenuButtons.add(new Button(sWidth/4, sHeight/4, sWidth*3/7, sHeight/8, "Noclip: OFF"));
            this.settingsMenuButtons.add(new Button(sWidth/4, sHeight/2, sWidth*3/7, sHeight/8, "Render Distance: MEDIUM"));
            this.settingsMenuButtons.add(new Button(sWidth/4, sHeight*3/4, sWidth*3/7, sHeight/8, "Move Speed: MEDIUM"));
            this.settingsMenuButtons.add(new Button(sWidth*3/4, sHeight/4, sWidth*3/7, sHeight/8, "FPS Counter: OFF"));

            this.settingsMenuButtons.add(new Button(sWidth/2, sHeight*29/32, sWidth*3/7, sHeight/8, "Back"));

        } else {
            this.settingsMenuButtons.set(0, new Button(sWidth/4, sHeight/4, sWidth*3/7, sHeight/8, this.settingsMenuButtons.get(0).getText()));
            this.settingsMenuButtons.set(1, new Button(sWidth/4, sHeight/2, sWidth*3/7, sHeight/8, this.settingsMenuButtons.get(1).getText()));
            this.settingsMenuButtons.set(2, new Button(sWidth/4, sHeight*3/4, sWidth*3/7, sHeight/8, this.settingsMenuButtons.get(2).getText()));
            this.settingsMenuButtons.set(3, new Button(sWidth*3/4, sHeight/4, sWidth*3/7, sHeight/8, this.settingsMenuButtons.get(3).getText()));

            this.settingsMenuButtons.set(4, new Button(sWidth/2, sHeight*29/32, sWidth*3/7, sHeight/8, "Back"));
        }
    }

    /**
     * Returns the main menu buttons.
     * 
     * @return The main menu buttons.
     */
    public ArrayList<Button> getMainMenuButtons(){
        return this.mainMenuButtons;
    }

    /**
     * Returns the level menu buttons.
     * 
     * @return The level menu buttons.
     */
    public ArrayList<Button> getLevelMenuButtons(){
        return this.levelMenuButtons;
    }

    /**
     * Returns the pause menu buttons.
     * 
     * @return The pause menu buttons.
     */
    public ArrayList<Button> getPauseMenuButtons(){
        return this.pauseMenuButtons;
    }

    /**
     * Returns the settings menu buttons.
     * 
     * @return The settings menu buttons.
     */
    public ArrayList<Button> getSettingsMenuButtons(){
        return this.settingsMenuButtons;
    }

    /**
     * Returns the available levels.
     * 
     * @return The available levels.
     */
    public ArrayList<Level> getLevels(){
        return this.levels;
    }
}
