package no.uib.inf101.sem2.game.model.entities;

import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.gameEngine.model.Camera;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

public class Player {
    private static final float MAX_HEALTH = 100;
    private static final float DAMAGE = 5; 

    private final GridPosition startPos;
    private final RelativeRotation startRot;

    private Camera playerCam;
    private float health;

    public Player(GridPosition startPos, RelativeRotation startRot, CollisionBox hitBox){
        this.startPos = startPos;
        this.startRot = startRot;

        this.playerCam = new Camera(startPos, startRot);
        if(hitBox != null){
            this.playerCam.setCollision(hitBox);
        }
        this.health = MAX_HEALTH;
    }

    public void resetPlayer(){
        this.health = MAX_HEALTH;
        this.playerCam.setPos(startPos);
        this.playerCam.setRotation(startRot);
    }

    public Camera getCamera(){
        return this.playerCam;
    }

    public void takeDamage(float damage){
        this.health -= damage;
    }

    public float getHealthPercent(){
        return this.health/MAX_HEALTH;
    }

    public boolean isAlive(){
        if(this.health > 0){
            return true;
        } else {
            return false;
        }
    }

    public float distanceToHit(CollisionBox hitBox){
        float dist = hitBox.rayIntersection(playerCam.getPos(), playerCam.getRotation().getVector().normalized());
        return dist;
    }

    public void giveDamageTo(Enemy enemy){
        enemy.damage(DAMAGE);
    }
}
