package no.uib.inf101.sem2.game.model.entities;

import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.gameEngine.model.Camera;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

/**
 * Represents the player in the game.
 */
public class Player {
    private static final float MAX_HEALTH = 100;
    private static final float DAMAGE = 15; 

    private final GridPosition startPos;
    private final RelativeRotation startRot;

    private Camera playerCam;
    private float health;

    /**
     * Constructs a Player with a specified starting position, rotation, and hitbox.
     *
     * @param startPos The starting position of the player.
     * @param startRot The starting rotation of the player.
     * @param hitBox The hitbox for the player's camera.
     */
    public Player(GridPosition startPos, RelativeRotation startRot, CollisionBox hitBox){
        this.startPos = startPos;
        this.startRot = startRot;

        this.playerCam = new Camera(startPos, startRot);
        if(hitBox != null){
            this.playerCam.setCollision(hitBox);
        }
        this.health = MAX_HEALTH;
    }

    /**
     * Resets the player's health, position, and rotation to their initial values.
     * This should be called when the player dies.
     */
    public void resetPlayer(){
        this.health = MAX_HEALTH;
        this.playerCam.setPos(startPos);
        this.playerCam.setRotation(startRot);
    }

    /**
     * Returns the player's camera object.
     *
     * @return The Camera object associated with the player.
     */
    public Camera getCamera(){
        return this.playerCam;
    }

    /**
     * Decreases the player's health by a specified amount.
     *
     * @param damage The amount of damage taken by the player.
     */
    public void takeDamage(float damage){
        this.health -= damage;
    }

    /**
     * Returns the player's health as a percentage of their maximum health.
     *
     * @return The percentage of the player's health.
     */
    public float getHealthPercent(){
        return this.health/MAX_HEALTH;
    }

    /**
     * Returns whether the player is alive or not based on their current health.
     *
     * @return True if the player is alive, false otherwise.
     */
    public boolean isAlive(){
        if(this.health > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the distance from the player to a specified hitbox along the direction
     * of the player's camera.
     *
     * @param hitBox The hitbox to check the distance to.
     * @return The distance between the player and the hitbox.
     * @return Float.MAX_VALUE if the hitbox is not in the player's line of sight ray.
     */
    public float distanceToHit(CollisionBox hitBox){
        float dist = hitBox.rayIntersection(playerCam.getPos(), playerCam.getRotation().getVector().normalized());
        return dist;
    }

    /**
     * Applies damage to a specified enemy.
     *
     * @param enemy The enemy to apply damage to.
     */
    public void giveDamageTo(Enemy enemy){
        enemy.damage(DAMAGE);
    }
}
