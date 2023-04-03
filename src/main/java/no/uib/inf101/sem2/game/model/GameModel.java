package no.uib.inf101.sem2.game.model;

import no.uib.inf101.sem2.game.model.levels.Level;

import java.util.ArrayList;

import no.uib.inf101.sem2.game.controller.ControllableGameModel;
import no.uib.inf101.sem2.game.view.ViewableGameModel;
import no.uib.inf101.sem2.gameEngine.model.EngineModel;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;

public class GameModel implements ViewableGameModel, ControllableGameModel{
    
    GameState currentState;
    EngineModel engineModel;
    Level map;

    public GameModel(Level map, EngineModel engineModel){
        this.currentState = GameState.ACTIVE;
        this.map = map;

        this.engineModel = engineModel;

        loadGame();
    }

    private void loadGame(){
        ArrayList<ShapeData> shapesData = this.map.loadLevel();
        for(ShapeData shapeData : shapesData){
            this.engineModel.createShape(shapeData);
        }
    }

    public GameState getCurrentState(){
        return this.currentState;
    }

}
