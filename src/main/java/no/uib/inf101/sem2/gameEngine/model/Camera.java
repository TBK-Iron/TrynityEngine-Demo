package no.uib.inf101.sem2.gameEngine.model;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

/**
 * The Camera class represents a virtual camera in the 3D game world.
 * It includes position, rotation, and an optional collision box.
 * The camera's position and rotation can be used to transform the game world
 * for rendering from the camera's perspective.
 */
public class Camera {
    private GridPosition position;
    private RelativeRotation rotation;
    private CollisionBox collisionBox;

    /**
     * Creates a new Camera with the given starting position and rotation.
     *
     * @param startPos the starting position of the camera.
     * @param startRot the starting rotation of the camera.
     */
    public Camera(GridPosition startPos, RelativeRotation startRot){
        this.position = startPos;
        this.rotation = startRot;
    }

    /**
     * Sets a collision box for the camera.
     *
     * @param box the collision box to be set.
     */
    public void setCollision(CollisionBox box){
        this.collisionBox = box;
    }

    /**
     * Sets the position of the camera.
     *
     * @param pos the new position of the camera.
     */
    public void setPos(GridPosition pos){
        this.position = pos;
    }

    /**
     * Sets the rotation of the camera.
     *
     * @param rotation the new rotation of the camera.
     */
    public void setRotation(RelativeRotation rotation){
        this.rotation = rotation;
    }

    /**
     * Gets the position of the camera.
     *
     * @return the current position of the camera.
     */
    public GridPosition getPos(){
        return this.position;
    }

    /**
     * Gets the rotation of the camera.
     *
     * @return the current rotation of the camera.
     */
    public RelativeRotation getRotation(){
        return this.rotation;
    }

    /**
     * Gets the collision box of the camera, if set.
     *
     * @return the collision box of the camera, or null if not set.
     */
    public CollisionBox getCollisionBox(){
        return this.collisionBox;
    }
}
