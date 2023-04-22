package no.uib.inf101.sem2.game.model.entities.enemies;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

/**
 * The EnemySpawner class is responsible for spawning enemies in the game.
 * It manages the spawn intervals, remaining spawn amounts, and enemy types.
 */
public class EnemySpawner {

    private final int minSpawnInterval;
    private int spawnAmount;
    private final Enemy enemytype;

    private CollisionBox translatedSpawnerHitbox;
    private Enemy previousEnemy;

    private int framecounter;

    /**
     * Creates a new EnemySpawner with the specified parameters.
     *
     * @param minSpawnInterval The minimum interval between enemy spawns.
     * @param spawnAmount The total number of enemies to spawn.
     * @param initialEnemy The initial enemy to be used as a template for spawning.
     */
    public EnemySpawner(int minSpawnInterval, int spawnAmount, Enemy initialEnemy){
        this.minSpawnInterval = minSpawnInterval;
        this.spawnAmount = spawnAmount;
        this.enemytype = initialEnemy;

        this.previousEnemy = null;

        this.framecounter = this.minSpawnInterval;

        this.translatedSpawnerHitbox = enemytype.getEntity().getCollisionBox().translatedBy(new Vector((Position3D) initialEnemy.getPosition()));
    }

    /**
     * Determines if an enemy can be spawned based on remaining spawn amounts,
     * minimum spawn interval, and collision with previously spawned enemies.
     *
     * @return true if an enemy can be spawned, false otherwise.
     */
    public boolean canSpawn() {
        if (spawnAmount == 0) {
            return false;
        }
    
        if (previousEnemy == null) {
            return true;
        }
    
        CollisionBox previousCollisionBox = previousEnemy.getEntity().getCollisionBox();
        GridPosition previousPosition = previousEnemy.getEntity().getPosition();
    
        if (translatedSpawnerHitbox.isColliding(previousCollisionBox, previousPosition) && previousEnemy.isAlive()) {
            return false;
        }
    
        if (framecounter > 0) {
            framecounter--;
            return false;
        }
    
        framecounter = minSpawnInterval;
        return true;
    }

    /**
     * Retrieves a copy of the inital enemy.
     *
     * @return The next enemy to be spawned.
     */
    public Enemy getNextEnemy(){
        Enemy nextEnemy = this.enemytype.clone();
        this.previousEnemy = nextEnemy;
        spawnAmount--;
        return nextEnemy;
    }

    /**
     * Retrieves the remaining number of enemies to be spawned.
     * 
     * @return The remaining number of enemies to be spawned.
     */
    public int getSpawnAmount(){
        return this.spawnAmount;
    }
}
