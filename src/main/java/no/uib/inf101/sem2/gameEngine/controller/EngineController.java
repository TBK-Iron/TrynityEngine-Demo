package no.uib.inf101.sem2.gameEngine.controller;

import no.uib.inf101.sem2.gameEngine.config.Config;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class EngineController {
    private ControllableEngineModel model;
    private MouseHandler mouseHandler;
    private MoveHandler moveHandler;

    public EngineController(ControllableEngineModel model, Config config) {
        this.model = model;
        this.mouseHandler = new MouseHandler(config);
        this.moveHandler = new MoveHandler(config);
    }

    public void mouseMoved(MouseEvent arg0) {    
        this.model.addToCameraRotation(this.mouseHandler.getRotation(arg0));
        this.mouseHandler.resetMousePosition();
        this.model.setMovementDelta(this.moveHandler.getMovementDelta());
    }

    public void keyPressed(KeyEvent k) {
        if(this.moveHandler.keyPressed(k)){
            this.model.setMovementDelta(this.moveHandler.getMovementDelta());
        }
    }

    public void keyReleased(KeyEvent k) {
        if(this.moveHandler.keyReleased(k)){
            this.model.setMovementDelta(this.moveHandler.getMovementDelta());
        }
    }
}
