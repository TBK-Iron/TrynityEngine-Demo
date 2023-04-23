package no.uib.inf101.sem2.gameEngine.view;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.Camera;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;

/**
 * ViewableEngineModel is an interface for accessing engine model data needed for rendering purposes.
 */
public interface ViewableEngineModel {

    /**
     * Returns a list of Shape3D objects to be rendered in the game.
     *
     * @return An ArrayList of Shape3D objects for rendering.
     */
    ArrayList<Shape3D> getRenderShapes();

    /**
     * Returns the current camera of the game engine model.
     *
     * @return The Camera object representing the current camera in the game.
     */
    Camera getCamera();
}