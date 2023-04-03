package no.uib.inf101.sem2.game.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import no.uib.inf101.sem2.game.view.GameView;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.controller.EngineController;

public class GameController implements java.awt.event.MouseMotionListener, java.awt.event.KeyListener{
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
        this.view.addKeyListener(this);

    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub
        //sthrow new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        this.engineController.mouseMoved(arg0);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        this.engineController.keyPressed(arg0);
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        this.engineController.keyReleased(arg0);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }
}
