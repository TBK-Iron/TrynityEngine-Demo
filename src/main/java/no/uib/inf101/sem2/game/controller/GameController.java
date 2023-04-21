package no.uib.inf101.sem2.game.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import no.uib.inf101.sem2.game.model.GameState;
import no.uib.inf101.sem2.game.view.GameView;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.controller.EngineController;

public class GameController implements java.awt.event.MouseMotionListener, java.awt.event.KeyListener, java.awt.event.MouseListener{
    ControllableGameModel model;
    GameView view;
    Config config;
    EngineController engineController;


    public GameController(ControllableGameModel model, GameView view, Config config, EngineController engineController) {
        this.model = model;
        this.config = config;
        this.view = view;
        this.engineController = engineController;

        this.view.addMouseMotionListener(this);
        this.view.addMouseListener(this);
        this.view.addKeyListener(this);

    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        if(this.model.getGameState() == GameState.ACTIVE){
            this.engineController.mouseMoved(arg0);
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if(this.model.getGameState() == GameState.ACTIVE){
            this.engineController.keyPressed(arg0);
            if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
                this.model.setGameState(GameState.PAUSED);
            }
        } else if (this.model.getGameState() == GameState.PAUSED){
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
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if(this.model.getGameState() == GameState.MAIN_MENU){
            ArrayList<Button> mainMenuButtons = this.view.getButtons().getMainMenuButtons();
            int x = arg0.getX();
            int y = arg0.getY();
            if(mainMenuButtons.get(0).isClicked(x, y)){
                this.model.setGameState(GameState.LEVEL_MENU);

            } else if(mainMenuButtons.get(1).isClicked(x, y)){
                //TODO: Add settings menu
            } else if(mainMenuButtons.get(2).isClicked(x, y)){
                System.exit(0);
            }
        } else if(this.model.getGameState() == GameState.LEVEL_MENU){
            ArrayList<Button> levelMenuButtons = this.view.getButtons().getLevelMenuButtons();
            int x = arg0.getX();
            int y = arg0.getY();

            for(int i = 0; i < levelMenuButtons.size(); i++){
                if(levelMenuButtons.get(i).isClicked(x, y)){
                    this.model.setGameState(GameState.LOADING);

                    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                    final int levelNr = i;
                    Runnable task = () -> {
                        model.loadMap(this.view.getButtons().getLevels().get(levelNr));
                        model.setGameState(GameState.ACTIVE);
                    };
                    executor.schedule(task, 30, TimeUnit.MILLISECONDS);
                }
            }
        } else if(this.model.getGameState() == GameState.PAUSED){
            ArrayList<Button> pauseMenuButtons = this.view.getButtons().getPauseMenuButtons();
            int x = arg0.getX();
            int y = arg0.getY();

            if(pauseMenuButtons.get(0).isClicked(x, y)){
                this.model.setGameState(GameState.ACTIVE);
            } else if(pauseMenuButtons.get(1).isClicked(x, y)){
                //TODO: Add settings menu
            } else if(pauseMenuButtons.get(2).isClicked(x, y)){
                this.model.setGameState(GameState.MAIN_MENU);
            }
        }
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
