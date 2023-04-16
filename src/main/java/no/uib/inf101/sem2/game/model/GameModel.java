package no.uib.inf101.sem2.game.model;

import no.uib.inf101.sem2.game.model.levels.Level;

import java.util.ArrayList;

import no.uib.inf101.sem2.game.controller.ControllableGameModel;
import no.uib.inf101.sem2.game.view.ViewableGameModel;
import no.uib.inf101.sem2.gameEngine.model.EngineModel;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

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
    
    //TODO: Implement this method
    private void loadMainMenu(){

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
        ArrayList<CollisionBox> entityCollision = this.map.loadEntityCollision();
        if(entityData.size() != entityCollision.size()){
            throw new Error("Every entity must have a corresponding collision shape");
        }
        for(int i = 0; i < entityData.size(); i++){
            this.engineModel.createEntity(entityData.get(i), entityCollision.get(i));
        }

        CollisionBox cameraCollisionBox = new CollisionBox(new Position3D(-0.5f, 0.5f, -0.5f), new Position3D(0.5f, -1.999f, 0.5f));
        this.engineModel.setCameraCollision(cameraCollisionBox);
    }

    public GameState getGameState(){
        return this.currentState;
    }

    public void setGameState(GameState state){
        this.currentState = state;
    }


}
