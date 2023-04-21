package no.uib.inf101.sem2.game.model.entities.enemies;

import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

/**
 * The Enemy interface represents an enemy in the game, providing methods
 * to interact with and manipulate enemy attributes and behaviors.
 * Implementations of this interface should manage enemy movement, health,
 * and interactions with other game objects.
 */
public interface Enemy {

    /**
     * Determines if the given position is within a specific range
     * of the enemy.
     *
     * @param pos The position to check.
     * @return true if the given position is within the range, false otherwise.
     */
    public boolean isWithinRadius(GridPosition pos);

    /**
     * Retrieves the Entity representing this enemy.
     *
     * @return The Entity associated with this enemy.
     */
    public Entity getEntity();

    /**
     * Checks if the enemy is alive.
     *
     * @return true if the enemy is alive, false otherwise.
     */
    public boolean isAlive();

    /**
     * Kills the enemy and performs any required cleanup.
     */
    public void kill();

    /**
     * Damages the enemy by the specified amount.
     *
     * @param amount The amount of damage to deal to the enemy.
     */
    public void damage(float amount);

    /**
     * Calculates the damage to be dealt to an entity at the given position.
     *
     * @param entityPos The position of the entity to damage.
     * @return The amount of damage to be dealt.
     */
    public float damageTo(GridPosition entityPos);

    /**
     * Sets the target position for the enemy to move towards.
     *
     * @param pos The position to set as the target.
     */
    public void setTargetPosition(GridPosition pos);

    /**
     * Retrieves the current position of the enemy.
     *
     * @return The GridPosition representing the enemy's current position.
     */
    public GridPosition getPosition();

    /**
     * Creates a new Enemy instance with the same properties as the original.
     *
     * @return A new Enemy instance that is a clone of the original.
     */
    public Enemy clone();

    public String getAmbientSound();

    public String getHurtSound();

    public String getDeathSound();

    public float getNoiseVolumeRelativeTo(GridPosition pos);

}

