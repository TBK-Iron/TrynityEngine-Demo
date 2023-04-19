package no.uib.inf101.sem2.gameEngine.controller;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

public interface ControllableEngineModel {

    void setMovementDelta(Vector deltaM);

    void addToCameraRotation(RelativeRotation rot);
}
