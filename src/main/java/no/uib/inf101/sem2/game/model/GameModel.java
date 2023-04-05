package no.uib.inf101.sem2.game.model;

import no.uib.inf101.sem2.game.model.levels.Level;

import java.util.ArrayList;

import no.uib.inf101.sem2.game.controller.ControllableGameModel;
import no.uib.inf101.sem2.game.view.ViewableGameModel;
import no.uib.inf101.sem2.gameEngine.model.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.model.EngineModel;
import no.uib.inf101.sem2.gameEngine.model.shape.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;

public class GameModel implements ViewableGameModel, ControllableGameModel{
    
    GameState currentState;
    EngineModel engineModel;
    CollisionDetector collisionDetector;
    Level map;

    public GameModel(Level map, EngineModel engineModel, CollisionDetector collisionDetector){
        this.currentState = GameState.ACTIVE;
        this.map = map;

        this.collisionDetector = collisionDetector;
        this.engineModel = engineModel;

        loadGame();
    }

    private void loadGame(){
        ArrayList<ShapeData> shapesData = this.map.loadShapes();
        for(ShapeData shapeData : shapesData){
            this.engineModel.createShape(shapeData);
        }
        ArrayList<CollisionBox> collisionBoxes = this.map.loadCollisionBoxes();
        for(CollisionBox box : collisionBoxes){
            this.collisionDetector.addCollisionBox(box);
        }


        ArrayList<ShapeData> entityData = this.map.loadEntities();
        ArrayList<ShapeData> entityCollision = this.map.loadEntityCollision();
        if(entityData.size() != entityCollision.size()){
            throw new Error("Every entity must have a corresponding collision shape");
        }
        for(int i = 0; i < entityData.size(); i++){
            this.engineModel.createEntity(entityData.get(i), entityCollision.get(i));
        }

    }

    public GameState getCurrentState(){
        return this.currentState;
    }

}
