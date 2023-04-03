package no.uib.inf101.sem2.gameEngine.controller;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public interface ControllableEngineModel {

    public void addToCameraPosition(GridPosition pos);

    public void addToCameraRotation(RelativeRotation rot);
}
