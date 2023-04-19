package no.uib.inf101.sem2.game.model.entities.enemies;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

//TODO: Implement this class
public class EnemySpawner {

    private final int minSpawnInterval;
    private int spawnAmount;
    private final Enemy enemytype;

    private CollisionBox translatedSpawnerHitbox;
    private Entity previousEnemyEntity;

    private int framecounter;

    public EnemySpawner(int minSpawnInterval, int spawnAmount, Enemy initialEnemy){
        this.minSpawnInterval = minSpawnInterval;
        this.spawnAmount = spawnAmount;
        this.enemytype = initialEnemy;

        this.previousEnemyEntity = null;

        this.framecounter = this.minSpawnInterval;

        this.translatedSpawnerHitbox = enemytype.getEntity().getCollisionBox().translatedBy(new Vector((Position3D) initialEnemy.getPosition()));
    }

    public boolean canSpawn() {
        if (spawnAmount == 0) {
            return false;
        }
    
        if (previousEnemyEntity == null) {
            spawnAmount--;
            return true;
        }
    
        CollisionBox previousCollisionBox = previousEnemyEntity.getCollisionBox();
        GridPosition previousPosition = previousEnemyEntity.getPosition();
    
        if (translatedSpawnerHitbox.isColliding(previousCollisionBox, previousPosition)) {
            return false;
        }
    
        if (framecounter > 0) {
            framecounter--;
            return false;
        }
    
        framecounter = minSpawnInterval;
        spawnAmount--;
        return true;
    }

    public Enemy getNextEnemy(){
        Enemy nextEnemy = this.enemytype.clone();
        this.previousEnemyEntity = nextEnemy.getEntity();
        return nextEnemy;
    }
}
