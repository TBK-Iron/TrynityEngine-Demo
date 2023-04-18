package no.uib.inf101.sem2.game.model;

import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.Zombie;
import no.uib.inf101.sem2.game.model.levels.Level;

import java.util.ArrayList;

import no.uib.inf101.sem2.game.controller.ControllableGameModel;
import no.uib.inf101.sem2.game.view.ViewableGameModel;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.Camera;
import no.uib.inf101.sem2.gameEngine.model.EngineModel;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

public class GameModel implements ViewableGameModel, ControllableGameModel{
    
    GameState currentState;
    EngineModel engineModel;
    CollisionDetector collisionDetector;
    CollisionDetector killDetector;
    Level map;

    Camera camera;

    ArrayList<Enemy> enemies;
    ArrayList<Door> doors;

    Config config;

    public GameModel(Level map, EngineModel engineModel, CollisionDetector collisionDetector, Config config){
        this.currentState = GameState.MAIN_MENU;
        this.map = map;

        this.collisionDetector = collisionDetector;
        this.engineModel = engineModel;

        this.killDetector = new CollisionDetector();
        this.config = config;

        loadGame();
    }

    private void loadGame(){
        ArrayList<ShapeData> shapesData = this.map.loadShapes();
        for(ShapeData shapeData : shapesData){
            this.engineModel.addShape(new Shape3D(shapeData));
        }
        ArrayList<CollisionBox> collisionBoxes = this.map.loadCollisionBoxes();
        for(CollisionBox box : collisionBoxes){
            this.collisionDetector.addCollisionBox(box);
        }

        this.doors = this.map.loadDoors();
        for(Door door : this.doors){
            this.engineModel.addEntity(door.getEntity());
        }

        this.enemies = this.map.loadEnemies();
        for(Enemy enemy : this.enemies){
            this.engineModel.addEntity(enemy.getEntity());
        }

        for(CollisionBox killbox : this.map.loadKillBoxes()){
            this.killDetector.addCollisionBox(killbox);
        }

        /* ArrayList<ShapeData> entityData = this.map.loadEntities();
        ArrayList<CollisionBox> entityCollision = null;
        if(entityData.size() != entityCollision.size()){
            throw new Error("Every entity must have a corresponding collision shape");
        }
        for(int i = 0; i < entityData.size(); i++){
            this.engineModel.createEntity(entityData.get(i), entityCollision.get(i));
        } */

        CollisionBox cameraCollisionBox = new CollisionBox(new Position3D(-0.5f, 0.5f, -0.5f), new Position3D(0.5f, -1.999f, 0.5f));
        this.camera = new Camera(this.map.startPosition(), this.map.startRotation());
        this.camera.setCollision(cameraCollisionBox);
        this.engineModel.setCamera(this.camera);
    }

    public void updateGame(){
        if(!this.config.noclip()){
            GridPosition camPos = this.camera.getPos();
            GridPosition playerPos = new Position3D(camPos.x(), camPos.y() - 1.999f, camPos.z());
            for(Door door : this.doors){
                if(door.isWithinRadius(playerPos)){
                    door.open();
                } else {
                    door.close();
                }
            }
            if(this.killDetector.getCollidingBox(this.camera.getCollisionBox(), this.camera.getPos()) != null){
                this.camera.setPos(this.map.startPosition());
            }
            for(Enemy enemy : this.enemies){
                if(enemy.isWithinRadius(playerPos)){
                    enemy.setTargetPosition(playerPos);
                }
            }
        }
    }

    public GameState getGameState(){
        return this.currentState;
    }

    public void setGameState(GameState state){
        this.currentState = state;
    }


}
