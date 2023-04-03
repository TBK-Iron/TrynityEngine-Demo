package no.uib.inf101.sem2.gameEngine.controller;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

public interface ControllableEngineModel {

    public void setMovementDelta(Vector deltaM);

    public void addToCameraRotation(RelativeRotation rot);
}
