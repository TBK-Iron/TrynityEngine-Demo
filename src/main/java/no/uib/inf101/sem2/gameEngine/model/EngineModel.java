package no.uib.inf101.sem2.gameEngine.model;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.controller.ControllableEngineModel;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
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
public class EngineModel implements ViewableEngineModel, ControllableEngineModel, ConfigurableEngineModel {
    
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
    @Override
    public void setCamera(Camera camera){
        this.camera = camera;
    }

    /**
     * Adds a shape to the list of shapes in the EngineModel.
     * 
     * @param shape The Shape3D object to be added.
     */
    @Override
    public void addShape(Shape3D shape){
        shapes.add(shape);
    }

    /**
     * Adds an entity to the list of entities in the EngineModel.
     * 
     * @param entity The Entity object to be added.
     */
    @Override
    public void addEntity(Entity entity){
        entities.add(entity);
    }

    @Override
    public void resetModel(){
        shapes = new ArrayList<>();
        entities = new ArrayList<>();
        this.collisionDetector.resetCollisionDetector();
    }

    /**
     * Returns a list of shapes to be rendered.
     * 
     * @return An ArrayList of Shape3D objects.
     */
    @Override
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
    @Override
    public ArrayList<Entity> getEntities(){
        return this.entities;
    }
    /**
     * Updates the camera position with regard to collisions, the movement vector, and gravity
     */
    @Override
    public void updateCameraPosition(){
       
        if(this.cameraMoveSpeed.magnitude() != 0){
            if(this.camera.getCollisionBox() == null || this.config.noclip()){
                this.camera.setPos(Vector.add(new Vector((Position3D) this.camera.getPos()), this.cameraMoveSpeed).getPoint());
            } else {
                GridPosition newPos = Vector.add(new Vector((Position3D) this.camera.getPos()), this.cameraMoveSpeed).getPoint();
                CollisionBox collidingBox = collisionDetector.getCollidingBox(this.camera.getCollisionBox(), newPos);
                
                int collisions = 0;
                while(collidingBox != null){
                    newPos = collidingBox.getCollisionPos(this.camera.getCollisionBox(), this.camera.getPos(), newPos);
                    collidingBox = collisionDetector.getCollidingBox(this.camera.getCollisionBox(), newPos);
                    collisions++;
                }
                this.camera.setPos(newPos);
                if(collisions != 0){
                    this.cameraMoveSpeed = new Vector(new float[]{this.cameraMoveSpeed.get(0), 0, this.cameraMoveSpeed.get(2)});
                } else {
                    this.cameraMoveSpeed = applyGravityToDelta(this.cameraMoveSpeed);
                }              
            }
        }
    }
    /**
     * Updates the entities' positions with regard to collisions, their movement vector, and gravity
     */
    @Override
    public void updateEntityPositions(){
        for(Entity entity : this.entities){
            if(entity.isMoving()){
                if(entity.getCollisionBox() == null){
                    if(entity.targetReached()){
                        entity.setPosition(entity.getTargetPosition());
                        entity.setMovementVector(new Vector(new float[]{0, 0, 0}));
                    } else {
                        entity.setPosition(Vector.add(new Vector((Position3D) entity.getPosition()), entity.getMovementVector()).getPoint());
                    }
                }else {
                    if(entity.targetReached()){
                        entity.setPosition(entity.getTargetPosition());
                    } else {
                        GridPosition newPos = Vector.add(new Vector((Position3D) entity.getPosition()), entity.getMovementVector()).getPoint();
                        CollisionBox collidingBox = collisionDetector.getCollidingBox(entity.getCollisionBox(), newPos);
                        
                        int collisions = 0;
                        while(collidingBox != null){
                            newPos = collidingBox.getCollisionPos(entity.getCollisionBox(), entity.getPosition(), newPos);
                            collidingBox = collisionDetector.getCollidingBox(entity.getCollisionBox(), newPos);
                            collisions++;
                        }
                        entity.setPosition(newPos);
                        if(collisions != 0){
                            entity.setMovementVector(new Vector(new float[]{entity.getMovementVector().get(0), 0, entity.getMovementVector().get(2)}));
                        } else {
                            entity.setMovementVector(applyGravityToDelta(entity.getMovementVector()));
                        }  
                    }
                    
                }
            }
        }
    }

    /**
     * Updates the rotations of all entities in the game.
     */
    @Override
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
    @Override
    public Camera getCamera(){
        return this.camera;
    }

    /**
     * Adds a relative rotation to the camera's current rotation.
     * 
     * @param cameraRotation The relative rotation to be added to the camera's current rotation.
    */
    @Override
    public void addToCameraRotation(RelativeRotation cameraRotation){
        this.camera.setRotation(this.camera.getRotation().add(cameraRotation));
    }

    /**
     * Sets the movement delta for the camera, accounting for the camera's current rotation and the configuration's noclip setting.
     * 
     * @param relativeDelta The relative movement delta vector.
     */
    @Override
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
