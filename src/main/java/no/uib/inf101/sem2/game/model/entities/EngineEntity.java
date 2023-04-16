package no.uib.inf101.sem2.game.model.entities;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

public interface EngineEntity {

    public void setRotation(RelativeRotation newRotation);

    public void setTargetPosition(GridPosition targetPos, float speed);

    public void setRotationDelta(RelativeRotation newRotation);

    public GridPosition getPosition();

    public Vector getMovementVector();
}
