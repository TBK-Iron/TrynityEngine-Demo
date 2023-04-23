package no.uib.inf101.sem2.gameEngine.controller;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

public interface ControllableEngineModel {

    /**
     * Sets the movement delta for the camera.
     * 
     * @param relativeDelta The relative movement delta vector that gets applied to the camera.
     */
    void setMovementDelta(Vector deltaM);

    /**
     * Adds a relative rotation to the camera's current rotation.
     * 
     * @param cameraRotation The relative rotation to be added to the camera's current rotation.
    */
    void addToCameraRotation(RelativeRotation rot);
}
