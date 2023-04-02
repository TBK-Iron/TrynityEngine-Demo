package no.uib.inf101.sem2.gameEngine.controller;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.view.GameView;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class EngineController implements java.awt.event.MouseMotionListener {
    ControllableEngineModel model;
    GameView view;
    Config config;
    MouseHandler mouseHandler;

    public EngineController(ControllableEngineModel model, GameView view, Config config) {
        this.model = model;
        this.config = config;
        this.view = view;
        this.mouseHandler = new MouseHandler(config.screenWidth(), config.screenHeight());

        this.view.setFocusable(true);
        this.view.addMouseMotionListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub
        //sthrow new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        this.model.addToCameraRotation(this.mouseHandler.getRotation(arg0));
        this.mouseHandler.resetMousePosition();
    }
}
