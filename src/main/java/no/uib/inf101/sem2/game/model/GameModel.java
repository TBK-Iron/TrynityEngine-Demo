package no.uib.inf101.sem2.game.model;

import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.Player;
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
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

public class GameModel implements ViewableGameModel, ControllableGameModel{
    
    GameState currentState;
    EngineModel engineModel;
    CollisionDetector collisionDetector;
    CollisionDetector killDetector;
    Level map;

    Player player;

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

        this.player = this.map.getPlayer();
        this.engineModel.setCamera(this.player.getCamera());
    }

    @Override
    public void updateGame(){
        if(!this.config.noclip()){
            GridPosition camPos = this.player.getCamera().getPos();
            GridPosition playerPos = new Position3D(camPos.x(), camPos.y() - 1.999f, camPos.z());
            for(Door door : this.doors){
                if(door.isWithinRadius(playerPos)){
                    door.open();
                } else {
                    door.close();
                }
            }
            for(Enemy enemy : this.enemies){
                if(enemy.isWithinRadius(playerPos)){
                    enemy.setTargetPosition(playerPos);
                    this.player.takeDamage(enemy.damageTo(playerPos));
                }
            }
            if(this.killDetector.getCollidingBox(this.player.getCamera().getCollisionBox(), this.player.getCamera().getPos()) != null){
                this.player.resetPlayer();
            } else if(!player.isAlive()){
                this.player.resetPlayer();
            }
        }
    }

    @Override
    public void shoot(){
        Enemy closestHitEnemy = null;
        float distToClosestHitEnemy = Float.MAX_VALUE;
        for(Enemy enemy : this.enemies){
            if(enemy.isAlive()){
                CollisionBox enemyHitBox = enemy.getEntity().getCollisionBox().translatedBy(new Vector((Position3D) enemy.getPosition()));
                float dist = player.distanceToHit(enemyHitBox);
                if(dist < distToClosestHitEnemy){
                    distToClosestHitEnemy = dist;
                    closestHitEnemy = enemy;
                }
            }
        }
        if(closestHitEnemy != null){
            for(CollisionBox obstruction : this.collisionDetector.getFixedCollisionBoxes()){
                float dist = player.distanceToHit(obstruction);
                if(dist < distToClosestHitEnemy){
                    return;
                }
            }
            player.giveDamageTo(closestHitEnemy);
            if(!closestHitEnemy.isAlive()){
                closestHitEnemy.kill();
            }
        }
    }

    @Override
    public GameState getGameState(){
        return this.currentState;
    }

    @Override
    public float getPlayerHealthPercent(){
        return this.player.getHealthPercent();
    }

    @Override
    public void setGameState(GameState state){
        this.currentState = state;
    }


}
