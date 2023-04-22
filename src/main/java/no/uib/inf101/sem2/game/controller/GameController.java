package no.uib.inf101.sem2.game.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import no.uib.inf101.sem2.game.model.GameState;
import no.uib.inf101.sem2.game.settings.DefaultSettings;
import no.uib.inf101.sem2.game.settings.Settings;
import no.uib.inf101.sem2.game.view.GameView;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.controller.EngineController;

public class GameController implements java.awt.event.MouseMotionListener, java.awt.event.KeyListener, java.awt.event.MouseListener{
    ControllableGameModel model;
    GameView view;
    Settings settings;
    EngineController engineController;


    public GameController(ControllableGameModel model, GameView view, Settings settings, EngineController engineController) {
        this.model = model;
        this.settings = settings;
        this.view = view;
        this.engineController = engineController;

        this.view.addMouseMotionListener(this);
        this.view.addMouseListener(this);
        this.view.addKeyListener(this);

    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        //Do nothing
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        if(this.model.getGameState() == GameState.ACTIVE){
            this.engineController.mouseMoved(arg0);
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (this.model.getGameState() == GameState.LEVEL_MENU){
            if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
                this.model.setGameState(GameState.MAIN_MENU);
            }
        } else if (this.model.getGameState() == GameState.SETTINGS_MENU){
            if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
                this.model.setGameState(GameState.MAIN_MENU);
            }
        } else if(this.model.getGameState() == GameState.ACTIVE){
            this.engineController.keyPressed(arg0);
            if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
                this.model.setGameState(GameState.PAUSED);
            }
        } else if (this.model.getGameState() == GameState.PAUSED){
            if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
                this.model.setGameState(GameState.ACTIVE);
            }
        } else if(this.model.getGameState() == GameState.SETTINGS_GAME){
            if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
                this.model.setGameState(GameState.ACTIVE);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        this.engineController.keyReleased(arg0);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        //Do nothing
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if(this.model.getGameState() == GameState.MAIN_MENU){
            Button clickedButton = clickedButton(this.view.getButtons().getMainMenuButtons(), arg0.getX(), arg0.getY());
            if(clickedButton != null){
                if(clickedButton.getText().equals("Play")){
                    this.model.setGameState(GameState.LEVEL_MENU);
                } else if(clickedButton.getText().equals("Settings")){
                    this.model.setGameState(GameState.SETTINGS_MENU);
                } else if(clickedButton.getText().equals("Exit")){
                    System.exit(0);
                }
            }
        } else if(this.model.getGameState() == GameState.LEVEL_MENU){
            Button clickedButton = clickedButton(this.view.getButtons().getLevelMenuButtons(), arg0.getX(), arg0.getY());
            if(clickedButton != null){
                this.model.setGameState(GameState.LOADING);

                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                final int levelNr = this.view.getButtons().getLevelMenuButtons().indexOf(clickedButton);
                Runnable task = () -> {
                    model.loadMap(this.view.getButtons().getLevels().get(levelNr));
                    model.setGameState(GameState.ACTIVE);
                };

                // Setting a delay on this so that the loading screen gets displayed 
                // while the game is being loaded and rendering the first frame
                executor.schedule(task, 30, TimeUnit.MILLISECONDS);
            }
        } else if(this.model.getGameState() == GameState.SETTINGS_MENU){   
            Button clickedButton = clickedButton(this.view.getButtons().getSettingsMenuButtons(), arg0.getX(), arg0.getY());

            if(clickedButton != null){
                if(clickedButton.getText().equals("Back")){
                    this.model.setGameState(GameState.MAIN_MENU);
                } else {
                    clickedSettingsButton(clickedButton);
                }
            }
        } else if(this.model.getGameState() == GameState.PAUSED){
            Button clickedButton = clickedButton(this.view.getButtons().getPauseMenuButtons(), arg0.getX(), arg0.getY());
            if(clickedButton != null){
                if(clickedButton.getText().equals("Resume")){
                    this.model.setGameState(GameState.ACTIVE);
                } else if(clickedButton.getText().equals("Settings")){
                    this.model.setGameState(GameState.SETTINGS_GAME);
                } else if(clickedButton.getText().equals("Main menu")){
                    this.model.setGameState(GameState.MAIN_MENU);
                }
            }
        } else if(this.model.getGameState() == GameState.SETTINGS_GAME){
            Button clickedButton = clickedButton(this.view.getButtons().getSettingsMenuButtons(), arg0.getX(), arg0.getY());
            if(clickedButton != null){
                if(clickedButton.getText().equals("Back")){
                    this.model.setGameState(GameState.PAUSED);
                } else {
                    clickedSettingsButton(clickedButton);
                }
            }
        }
    }

    private void clickedSettingsButton(Button button){
        if(button.getText().equals("Noclip: OFF")){
            button.setText("Noclip: ON");
            this.settings.setNoclip(true);
        } else if(button.getText().equals("Noclip: ON")){
            button.setText("Noclip: OFF");
            this.settings.setNoclip(false);
        } else if(button.getText().equals("Render Distance: SHORT")){
            button.setText("Render Distance: MEDIUM");
            this.settings.setRenderDistance(DefaultSettings.renderDistanceMEDIUM);
        } else if(button.getText().equals("Render Distance: MEDIUM")){
            button.setText("Render Distance: FAR");
            this.settings.setRenderDistance(DefaultSettings.renderDistanceFAR);
        } else if(button.getText().equals("Render Distance: FAR")){
            button.setText("Render Distance: SHORT");
            this.settings.setRenderDistance(DefaultSettings.renderDistanceSHORT);
        } else if(button.getText().equals("Move Speed: SLOW")){
            button.setText("Move Speed: MEDIUM");
            this.settings.setWalkingSpeed(DefaultSettings.walkingSpeedMEDIUM);
            this.settings.setSprintSpeed(DefaultSettings.sprintSpeedMEDIUM);
        } else if(button.getText().equals("Move Speed: MEDIUM")){
            button.setText("Move Speed: FAST");
            this.settings.setWalkingSpeed(DefaultSettings.walkingSpeedFAST);
            this.settings.setSprintSpeed(DefaultSettings.sprintSpeedFAST);
        } else if(button.getText().equals("Move Speed: FAST")){
            button.setText("Move Speed: SLOW");
            this.settings.setWalkingSpeed(DefaultSettings.walkingSpeedSLOW);
            this.settings.setSprintSpeed(DefaultSettings.sprintSpeedSLOW);
        }
    }

    private static Button clickedButton(ArrayList<Button> buttons, int x, int y){
        for(int i = 0; i < buttons.size(); i++){
            if(buttons.get(i).isClicked(x, y)){
                return buttons.get(i);
            }
        }
        return null;
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        //Do nothing
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        //Do nothing
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        if(this.model.getGameState() == GameState.ACTIVE){
            this.model.shoot();
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        //Do nothing
    }
}
