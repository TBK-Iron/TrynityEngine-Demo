package no.uib.inf101.sem2.gameEngine.model;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.controller.ControllableEngineModel;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.ViewableEngineModel;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.RotateTransform;

/**
 * EngineModel is responsible for managing the game's state, including shapes, entities, and the camera.
 * It also handles the update logic for positions and rotations of the camera and entities in the game.
 */
public class EngineModel implements ViewableEngineModel, ControllableEngineModel {
    
    ArrayList<Shape3D> shapes;
    ArrayList<Entity> entities;
    Camera camera;
    CollisionDetector collisionDetector;
    Vector cameraMoveSpeed;
    Config config;
    
    
    /**
     * Constructs an EngineModel with the provided configuration and collision detector.
     * 
     * @param config The configuration object containing various game settings.
     * @param collisionDetector The collision detector used for handling collisions.
     */
    public EngineModel(Config config, CollisionDetector collisionDetector){
        this.shapes = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.camera = new Camera(new Position3D(0, 0, 0), new RelativeRotation(0, 0, 0));
        this.cameraMoveSpeed = new Vector(new float[]{0, 0, 0});
        this.config = config;
        this.collisionDetector = collisionDetector;
    }

    /**
     * Sets the camera for this EngineModel.
     * 
     * @param camera The new camera to be used.
     */
    public void setCamera(Camera camera){
        this.camera = camera;
    }

    /**
     * Adds a shape to the list of shapes in the EngineModel.
     * 
     * @param shape The Shape3D object to be added.
     */
    public void addShape(Shape3D shape){
        shapes.add(shape);
    }

    /**
     * Adds an entity to the list of entities in the EngineModel.
     * 
     * @param entity The Entity object to be added.
     */
    public void addEntity(Entity entity){
        entities.add(entity);
    }

    /**
     * Returns a list of shapes to be rendered.
     * 
     * @return An ArrayList of Shape3D objects.
     */
    public ArrayList<Shape3D> getRenderShapes(){
        ArrayList<Shape3D> allShapes = new ArrayList<>();

        for(Shape3D shape : this.shapes){
            allShapes.add(shape);
        }
        for(Entity entity : this.entities){
            allShapes.add((Shape3D) entity);
        }

        return allShapes;
    }

    /**
     * Returns a list of entities in the EngineModel.
     * 
     * @return An ArrayList of Entity objects.
     */
    public ArrayList<Entity> getEntities(){
        return this.entities;
    }

    /**
     * Calculates the maximum distance between a point and any point on a face.
     * 
     * @param pos1 The reference point (GridPosition).
     * @param face The face to compare against.
     * @return The maximum distance as a float.
     */
    public float maxDistFromPointToFace(GridPosition pos1, Face face){
        float maxDistance = 0.0f;
        for(GridPosition pos2 : face.getPoints()){
            float dist = (float) Math.sqrt(Math.pow(pos2.x() - pos1.x(), 2) + Math.pow(pos2.x() - pos1.x(), 2) + Math.pow(pos2.x() - pos1.x(), 2));
            if(dist > maxDistance){
                maxDistance = dist;
            }
        }
        return maxDistance;
    }

    /**
     * Method to update the position of either the camera or an entity, considering move speed and collision detection.
     * 
     * @param currentPosition The current position of the camera or entity.
     * @param moveSpeed The movement speed vector of the camera or entity.
     * @param collisionBox The collision box of the camera or entity, can be null if no collision should be considered.
     * @param applyGravity A boolean indicating if gravity should be applied to the new position.
     * @return The updated GridPosition.
     */
    private GridPosition updatePosition(GridPosition currentPosition, Vector moveSpeed, CollisionBox collisionBox, boolean applyGravity) {
        if (moveSpeed.magnitude() != 0) {
            if (collisionBox == null || config.noclip()) {
                return Vector.add(new Vector((Position3D) currentPosition), moveSpeed).getPoint();
            } else {
                GridPosition newPos = Vector.add(new Vector((Position3D) currentPosition), moveSpeed).getPoint();
                CollisionBox collidingBox = collisionDetector.getCollidingBox(collisionBox, newPos);
    
                int collisions = 0;
                while (collidingBox != null) {
                    newPos = collidingBox.getCollisionPos(collisionBox, currentPosition, newPos);
                    collidingBox = collisionDetector.getCollidingBox(collisionBox, newPos);
                    collisions++;
                }
    
                if (collisions != 0) {
                    moveSpeed = new Vector(new float[]{moveSpeed.get(0), 0, moveSpeed.get(2)});
                } else if (applyGravity) {
                    moveSpeed = applyGravityToDelta(moveSpeed);
                }
    
                return newPos;
            }
        }
    
        return currentPosition;
    }
    
    /**
     * Updates the camera's position based on its movement speed and handles collision detection.
     */
    public void updateCameraPosition() {
        camera.setPos(updatePosition(camera.getPos(), cameraMoveSpeed, camera.getCollisionBox(), true));
    }
    
    /**
     * Updates the positions of all moving entities in the game.
     * Handles collision detection and ensures entities reach their target positions.
     */
    public void updateEntityPositions() {
        for (Entity entity : this.entities) {
            if (entity.isMoving()) {
                if (entity.targetReached()) {
                    entity.setPosition(entity.getTargetPosition());
                    entity.setMovementVector(new Vector(new float[]{0, 0, 0}));
                } else {
                    entity.setPosition(updatePosition(entity.getPosition(), entity.getMovementVector(), entity.getCollisionBox(), true));
                }
            }
        }
    }

    /**
     * Updates the rotations of all entities in the game.
     */
    public void updateEntityRotations(){
        for(Entity entity : this.entities){
            entity.rotate();
        }
    }

    /**
     * Returns the current camera of the EngineModel.
     * 
     * @return The Camera object.
     */
    public Camera getCamera(){
        return this.camera;
    }

    /**
     * Adds a relative rotation to the camera's current rotation.
     * 
     * @param cameraRotation The relative rotation to be added to the camera's current rotation.
    */
    public void addToCameraRotation(RelativeRotation cameraRotation){
        this.camera.setRotation(this.camera.getRotation().add(cameraRotation));
    }

    /**
     * Sets the movement delta for the camera, accounting for the camera's current rotation and the configuration's noclip setting.
     * 
     * @param relativeDelta The relative movement delta vector.
     */
    public void setMovementDelta(Vector relativeDelta){
        Vector delta;
        if(relativeDelta.magnitude() != 0){
            Matrix rotationMatrix = new RotateTransform(new RelativeRotation(0, this.camera.getRotation().getLeftRight()), true).getMatrix();
            delta = rotationMatrix.multiply(relativeDelta);
        } else {
            
            delta = relativeDelta;
        }
        if(this.config.noclip()){
            this.cameraMoveSpeed = delta;
        } else if(this.cameraMoveSpeed.get(1) == 0) {
            this.cameraMoveSpeed = new Vector(new float[]{delta.get(0), delta.get(1) + this.cameraMoveSpeed.get(1), delta.get(2)});
        } else {
            this.cameraMoveSpeed = new Vector(new float[]{delta.get(0), this.cameraMoveSpeed.get(1), delta.get(2)});
        }
    }

    /**
     * Applies gravity to the given movement delta vector.
     * 
     * @param delta The movement delta vector to apply gravity to.
     * @return The new movement delta vector with gravity applied.
     */
    private Vector applyGravityToDelta(Vector delta){
        Vector newDelta = new Vector(new float[]{delta.get(0), delta.get(1) - this.config.gravityAcceleration(), delta.get(2)});
        
        return newDelta;
    }
}
