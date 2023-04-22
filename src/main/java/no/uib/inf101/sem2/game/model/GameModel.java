package no.uib.inf101.sem2.game.model;

import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.Player;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemySpawner;
import no.uib.inf101.sem2.game.model.levels.Level;
import no.uib.inf101.sem2.game.model.resourceLoaders.SoundPlayer;

import java.util.ArrayList;

import no.uib.inf101.sem2.game.controller.ControllableGameModel;
import no.uib.inf101.sem2.game.view.ViewableGameModel;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.ConfigurableEngineModel;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

/**
 * Represents the game model that handles game logic, entities, and level data.
 * Implements both ViewableGameModel and ControllableGameModel interfaces.
 */
public class GameModel implements ViewableGameModel, ControllableGameModel{
    
    GameState currentState;
    ConfigurableEngineModel engineModel;
    CollisionDetector collisionDetector;
    CollisionDetector killDetector;
    SoundPlayer soundPlayer;

    Player player;

    ArrayList<Enemy> enemies;
    ArrayList<EnemySpawner> enemySpawners;
    ArrayList<Door> doors;

    Config config;

    String music;

    /**
     * Constructs a new GameModel.
     *
     * @param engineModel The EngineModel object handling the game engine.
     * @param collisionDetector The CollisionDetector object responsible for detecting collisions.
     * @param config The game and model configuration.
     */
    public GameModel(ConfigurableEngineModel engineModel, CollisionDetector collisionDetector,SoundPlayer soundPlayer, Config config){
        this.currentState = GameState.LOADING;

        this.collisionDetector = collisionDetector;
        this.engineModel = engineModel;
        this.soundPlayer = soundPlayer;

        this.killDetector = new CollisionDetector();
        this.config = config;
    }

    /**
     * Loads the game, including shapes, collision boxes, doors, enemies, and enemy spawners from the map.
     * 
     * @param map The Level object containing the map data.
     */
    @Override
    public void loadMap(Level map){
        this.engineModel.resetModel();

        ArrayList<ShapeData> shapesData = map.loadShapes();
        for(ShapeData shapeData : shapesData){
            this.engineModel.addShape(new Shape3D(shapeData));
        }
        ArrayList<CollisionBox> collisionBoxes = map.loadCollisionBoxes();
        for(CollisionBox box : collisionBoxes){
            this.collisionDetector.addCollisionBox(box);
        }

        this.doors = map.loadDoors();
        for(Door door : this.doors){
            this.engineModel.addEntity(door.getEntity());
        }

        this.enemies = map.loadEnemies();
        for(Enemy enemy : this.enemies){
            this.engineModel.addEntity(enemy.getEntity());
        }

        for(Entity entity : map.loadEntities()){
            this.engineModel.addEntity(entity);
        }

        this.enemySpawners = map.loadEnemySpawners();

        for(CollisionBox killbox : map.loadKillBoxes()){
            this.killDetector.addCollisionBox(killbox);
        }

        this.player = map.getPlayer();
        this.engineModel.setCamera(this.player.getCamera());

        this.music = map.getLevelMusic();
        this.soundPlayer.startSoundLoop(this.music, 3.5f);
    }

    /**
     * Updates the game state, handling door and enemy interactions, player damage, and enemy spawning.
     * 
     * This method should be run every frame the game is active
     */
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
                if(enemy.isAlive()){
                    float randomVal = (float) Math.random();
                    if(randomVal < 0.005){
                        float volume = enemy.getNoiseVolumeRelativeTo(playerPos);
                        this.soundPlayer.playSoundOnce(enemy.getAmbientSound(), volume);
                    }
                } 
            }
            if(!player.isAlive() || this.killDetector.getCollidingBox(this.player.getCamera().getCollisionBox(), this.player.getCamera().getPos()) != null){
                this.player.resetPlayer();
                this.soundPlayer.playSoundOnce(Player.PLAYER_DEATH_SOUND, 5);
            }
          
        }

        for(EnemySpawner spawner : this.enemySpawners){
            if(spawner.canSpawn()){
                Enemy newEnemy = spawner.getNextEnemy();
                this.enemies.add(newEnemy);
                this.engineModel.addEntity(newEnemy.getEntity());
            }
        }
    }

    /**
     * Shoots at the closest enemy within range, considering obstructions.
     */
    @Override
    public void shoot(){
        soundPlayer.playSoundOnce(Player.PLAYER_SHOOT_SOUND, 4.5f);

        Enemy closestHitEnemy = null;
        float distToClosestHitEnemy = Float.MAX_VALUE;
        for(Enemy enemy : this.enemies){
            if(enemy.isAlive()){
                CollisionBox enemyHitBox = enemy.getEntity().getCollisionBox().translatedBy(new Vector((Position3D) enemy.getPosition()));
                float dist = player.distanceToHit(enemyHitBox);
                if(dist < Math.min(1000, distToClosestHitEnemy)){
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
            float volume = closestHitEnemy.getNoiseVolumeRelativeTo(player.getCamera().getPos());
            if(!closestHitEnemy.isAlive()){
                closestHitEnemy.kill();
                soundPlayer.playSoundOnce(closestHitEnemy.getDeathSound(), volume);
            } else {
                soundPlayer.playSoundOnce(closestHitEnemy.getHurtSound(), volume);
            }
        }
    }

    /**
     * Returns the current game state.
     *
     * @return The current GameState.
     */
    @Override
    public GameState getGameState(){
        return this.currentState;
    }

    /**
     * Returns the player's health percentage.
     *
     * @return The player's health as a percentage of their maximum health.
     */
    @Override
    public float getPlayerHealthPercent(){
        return this.player.getHealthPercent();
    }

    /**
     * Sets the current game state.
     * 
     * @param state The new GameState.
     */
    @Override
    public void setGameState(GameState state){
        this.currentState = state;
    }

    /**
     * Stops the music that is playing on the currently active map.
     */
    @Override
    public void stopMusic(){
        this.soundPlayer.endSoundLoop(this.music);
    }


}
