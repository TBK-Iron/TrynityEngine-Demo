package no.uib.inf101.sem2.game.model.levels;

import java.util.ArrayList;

import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.Player;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemySpawner;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;

/**
 * This interface defines the structure and methods for a Level in the game.
 */
public interface Level {

    /**
     * Returns the name of the level.
     *
     * @return the name of the level.
     */
    public String getLevelName();

    /**
     * Returns the name of the music for the level.
     * 
     * @return the name of the music for the level.
     */
    public String getLevelMusic();

    /**
     * Returns the Player object for the level.
     *
     * @return the Player object.
     */
    public Player getPlayer();

    /**
     * Loads and returns a list of ShapeData objects that define the geometry of the level.
     *
     * @return an ArrayList of ShapeData objects.
     */
    public ArrayList<ShapeData> loadShapes();

    /**
     * Loads and returns a list of ShapeData objects that define the entities in the level.
     *
     * @return an ArrayList of ShapeData objects.
     */
    public ArrayList<ShapeData> loadEntities();

    /**
     * Loads and returns a list of CollisionBox objects that define the collision boundaries in the level.
     *
     * @return an ArrayList of CollisionBox objects.
     */
    public ArrayList<CollisionBox> loadCollisionBoxes();

    /**
     * Loads and returns a list of CollisionBox objects that define the kill zones in the level.
     *
     * @return an ArrayList of CollisionBox objects.
     */
    public ArrayList<CollisionBox> loadKillBoxes();

    /**
     * Loads and returns a list of Enemy objects that define the enemies in the level.
     *
     * @return an ArrayList of Enemy objects.
     */
    public ArrayList<Enemy> loadEnemies();

    /**
     * Loads and returns a list of EnemySpawner objects that define the enemy spawn points in the level.
     *
     * @return an ArrayList of EnemySpawner objects.
     */
    public ArrayList<EnemySpawner> loadEnemySpawners();

    /**
     * Loads and returns a list of Door objects that define the doors in the level.
     *
     * @return an ArrayList of Door objects.
     */
    public ArrayList<Door> loadDoors();
}
