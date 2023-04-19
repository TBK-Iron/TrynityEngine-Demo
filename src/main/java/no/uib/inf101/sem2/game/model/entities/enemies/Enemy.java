package no.uib.inf101.sem2.game.model.entities.enemies;

import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

public interface Enemy {

    public boolean isWithinRadius(GridPosition pos);

    public Entity getEntity();

    public boolean isAlive();

    public void kill();

    public void damage(float amount);

    public float damageTo(GridPosition entityPos);

    public void setTargetPosition(GridPosition pos);

    public GridPosition getPosition();

    public Enemy clone();
}
