package no.uib.inf101.sem2.game.model.entities.enemies;

import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

public interface Enemy {
    public float getHealth();

    public void damage(float amount);

    public void setTargetPosition(GridPosition pos);

    public GridPosition getPosition();
}
