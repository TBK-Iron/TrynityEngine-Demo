package no.uib.inf101.sem2.game.controller;

import java.util.ArrayList;

import no.uib.inf101.sem2.game.model.levels.GrassWorld;
import no.uib.inf101.sem2.game.model.levels.HordeZ;
import no.uib.inf101.sem2.game.model.levels.LegendOfTheBeast;
import no.uib.inf101.sem2.game.model.levels.Level;
import no.uib.inf101.sem2.gameEngine.config.Config;

public class ButtonsHandler {

    private ArrayList<Button> mainMenuButtons;
    private ArrayList<Button> levelMenuButtons;
    private ArrayList<Button> pauseMenuButtons;
    private ArrayList<Button> settingsMenuButtons;

    private Config config;
    private ArrayList<Level> levels;

    public ButtonsHandler(Config config) {
        this.config = config;
        

        this.levels = new ArrayList<>();
        this.levels.add(new LegendOfTheBeast());
        this.levels.add(new HordeZ());
        this.levels.add(new GrassWorld());

        resetButtonHandler();
    }

    public void resetButtonHandler(){
        createMainMenuButtons();
        createLevelMenuButtons();
        createPauseMenuButtons();
        createSettingsMenuButtons();
    }

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

    private void createLevelMenuButtons(){
        
        int sWidth = this.config.screenWidth();
        int sHeight = this.config.screenHeight();

        if(this.levelMenuButtons != null){
            this.levelMenuButtons.clear();
        } else {
            this.levelMenuButtons = new ArrayList<>();
        }

        for(int i = 0; i < this.levels.size(); i++){
            this.levelMenuButtons.add(new Button(sWidth/2, sHeight*(i+1)/4, sWidth/2, sHeight/8, this.levels.get(i).getLevelName()));
        }
    }
    
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

    private void createSettingsMenuButtons(){
        
        int sWidth = this.config.screenWidth();
        int sHeight = this.config.screenHeight();

        if(this.settingsMenuButtons == null){
            this.settingsMenuButtons = new ArrayList<>();

            this.settingsMenuButtons.add(new Button(sWidth/2, sHeight/4, sWidth/2, sHeight/8, "Noclip: OFF"));
            this.settingsMenuButtons.add(new Button(sWidth/2, sHeight/2, sWidth/2, sHeight/8, "Render Distance: MEDIUM"));
            this.settingsMenuButtons.add(new Button(sWidth/2, sHeight*3/4, sWidth/2, sHeight/8, "Move Speed: MEDIUM"));
            this.settingsMenuButtons.add(new Button(sWidth/2, sHeight*7/8, sWidth/2, sHeight/8, "Back"));

        } else {
            this.settingsMenuButtons.set(0, new Button(sWidth/2, sHeight/4, sWidth/2, sHeight/8, this.settingsMenuButtons.get(0).getText()));
            this.settingsMenuButtons.set(1, new Button(sWidth/2, sHeight/2, sWidth/2, sHeight/8, this.settingsMenuButtons.get(1).getText()));
            this.settingsMenuButtons.set(2, new Button(sWidth/2, sHeight*3/4, sWidth/2, sHeight/8, this.settingsMenuButtons.get(2).getText()));
            this.settingsMenuButtons.set(3, new Button(sWidth/2, sHeight*7/8, sWidth/2, sHeight/8, this.settingsMenuButtons.get(3).getText()));
        }
    }

    public ArrayList<Button> getMainMenuButtons(){
        return this.mainMenuButtons;
    }

    public ArrayList<Button> getLevelMenuButtons(){
        return this.levelMenuButtons;
    }

    public ArrayList<Button> getPauseMenuButtons(){
        return this.pauseMenuButtons;
    }

    public ArrayList<Button> getSettingsMenuButtons(){
        return this.settingsMenuButtons;
    }

    public ArrayList<Level> getLevels(){
        return this.levels;
    }
}
