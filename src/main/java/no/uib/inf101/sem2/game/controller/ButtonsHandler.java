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

    private Config config;
    private ArrayList<Level> levels;

    public ButtonsHandler(Config config) {
        this.config = config;
        

        this.levels = new ArrayList<>();
        this.levels.add(new LegendOfTheBeast());
        this.levels.add(new HordeZ());
        this.levels.add(new GrassWorld());

        this.mainMenuButtons = new ArrayList<>();
        this.levelMenuButtons = new ArrayList<>();
        this.pauseMenuButtons = new ArrayList<>();

        createLevelMenuButtons();
        createMainMenuButtons();
        createPauseMenuButtons();
    }

    public void resetButtonHandler(){
        mainMenuButtons.clear();
        createMainMenuButtons();
        levelMenuButtons.clear();
        createLevelMenuButtons();
        pauseMenuButtons.clear();
        createPauseMenuButtons();
    }

    private void createMainMenuButtons(){
        
        int sWidth = this.config.screenWidth();
        int sHeight = this.config.screenHeight();

        this.mainMenuButtons = new ArrayList<>();
        this.mainMenuButtons.add(new Button(sWidth/2, sHeight/4, sWidth/2, sHeight/8, "Play"));
        this.mainMenuButtons.add(new Button(sWidth/2, sHeight/2, sWidth/2, sHeight/8, "Settings"));
        this.mainMenuButtons.add(new Button(sWidth/2, sHeight*3/4, sWidth/2, sHeight/8, "Quit"));
    }

    private void createLevelMenuButtons(){
        
        int sWidth = this.config.screenWidth();
        int sHeight = this.config.screenHeight();

        for(int i = 0; i < this.levels.size(); i++){
            this.levelMenuButtons.add(new Button(sWidth/2, sHeight*(i+1)/4, sWidth/2, sHeight/8, this.levels.get(i).getLevelName()));
        }
    }
    
    private void createPauseMenuButtons(){
        
        int sWidth = this.config.screenWidth();
        int sHeight = this.config.screenHeight();

        this.pauseMenuButtons = new ArrayList<>();
        this.pauseMenuButtons.add(new Button(sWidth/2, sHeight/4, sWidth/2, sHeight/8, "Resume"));
        this.pauseMenuButtons.add(new Button(sWidth/2, sHeight/2, sWidth/2, sHeight/8, "Settings"));
        this.pauseMenuButtons.add(new Button(sWidth/2, sHeight*3/4, sWidth/2, sHeight/8, "Main Menu"));    
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

    public ArrayList<Level> getLevels(){
        return this.levels;
    }
}
