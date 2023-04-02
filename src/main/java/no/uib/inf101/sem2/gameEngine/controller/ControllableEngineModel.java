package no.uib.inf101.sem2.gameEngine.controller;

import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public interface ControllableEngineModel {

    public void setCameraPosition(GridPosition pos);
    
    public void setCameraRotation(RelativeRotation rot);
}
