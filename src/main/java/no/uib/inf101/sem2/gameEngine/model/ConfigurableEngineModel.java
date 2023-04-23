package no.uib.inf101.sem2.gameEngine.model;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.*;

/**
 * The ConfigurableEngineModel interface defines methods for managing an engine model's state,
 * such as adding shapes and entities, setting the camera, and updating positions and rotations.
 */
public interface ConfigurableEngineModel {
    
    /**
     * Sets the camera for this engine model.
     *
     * @param camera The new camera to be used.
     */
    public void setCamera(Camera camera);

    /**
     * Adds a shape to the engine model.
     *
     * @param shape The Shape3D object to be added.
     */
    public void addShape(Shape3D shape);

    /**
     * Adds an entity to the engine model.
     *
     * @param entity The Entity object to be added.
     */
    public void addEntity(Entity entity);

    /**
     * Resets the engine model by removing all shapes and entities and resetting the collision detector.
     */
    public void resetModel();

    /**
     * Returns a list of entities in the engine model.
     *
     * @return An ArrayList of Entity objects.
     */
    public ArrayList<Entity> getEntities();

    /**
     * Updates the camera position with regard to collisions, movement vector, and gravity.
     */
    public void updateCameraPosition();

    /**
     * Updates the entities' positions with regard to collisions, movement vector, and gravity.
     */
    public void updateEntityPositions();

    /**
     * Updates the rotations of all entities in the engine model.
     */
    public void updateEntityRotations();
}
