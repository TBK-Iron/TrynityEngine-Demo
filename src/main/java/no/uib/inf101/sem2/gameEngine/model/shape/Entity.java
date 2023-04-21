package no.uib.inf101.sem2.gameEngine.model.shape;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

/**
 * Entity is a subclass of Shape3D representing an object in the game world
 * that can move, rotate, and collide with other objects unlike Shape3D objects. It has additional
 * properties like movement vectors, rotation deltas, and collision boxes.
 */
public class Entity extends Shape3D {

    private CollisionBox collisionBox;
    private Vector movementVector;
    private RelativeRotation rotationDelta;
    private RelativeRotation rotationDeltaDelta;
    private int rotationFrameCount;
   
    private GridPosition targetPosition;

    /**
     * Constructs an Entity instance from the provided ShapeData.
     * @param shapeData The ShapeData object containing the data necessary to create the entity.
     */
    public Entity(ShapeData shapeData) {
        super(shapeData);
        this.movementVector = new Vector(new float[]{0, 0, 0});
        this.targetPosition = this.anchoredPos;
        this.collisionBox = null;

        this.rotationDelta = new RelativeRotation(0, 0, 0);
        this.rotationDeltaDelta = new RelativeRotation(0, 0, 0);
        this.rotationFrameCount = 0;
    }

    /**
     * Sets the collision box for the entity.
     * @param collisionBox The CollisionBox object to associate with the entity.
     */
    public void setCollision(CollisionBox collisionBox){
        this.collisionBox = collisionBox;
    }

    /**
     * Constructs a new Entity instance based on the specified primaryEntity.
     * @param primaryEntity The primary Entity object to copy.
     */
    public Entity(Entity primaryEntity){
        super(primaryEntity);
    
        this.movementVector = primaryEntity.movementVector;
        this.targetPosition = primaryEntity.targetPosition;
        this.collisionBox = primaryEntity.collisionBox;

        this.rotationDelta = primaryEntity.rotationDelta;
        this.rotationDeltaDelta = primaryEntity.rotationDeltaDelta;
        this.rotationFrameCount = primaryEntity.rotationFrameCount;
    }

    /**
     * Sets the rotation of the entity.
     * @param rotation The RelativeRotation object representing the new rotation.
     */
    public void setRotation(RelativeRotation rotation){
        this.rotation = rotation;
    }
    
    /**
     * Rotates the entity based on the rotation delta and frame count.
     */
    public void rotate(){
        if(rotationFrameCount >= 1){
            this.rotation = this.rotation.add(this.rotationDelta);
            rotationFrameCount--;
        } else if(this.rotationFrameCount == -1){
            this.rotation = this.rotation.add(this.rotationDelta);
            this.rotationDelta = this.rotationDelta.add(this.rotationDeltaDelta);
        }
    }

    /**
     * Changes the position of the entity.
     * @param position The GridPosition object representing the new position.
     */
    public void setPosition(GridPosition position){
        this.anchoredPos = position;
    }

    /**
     * Checks if the target position has been reached.
     * @return true if the target position is reached, false otherwise.
     */
    public boolean targetReached(){
        GridPosition target = this.targetPosition;
        GridPosition current = this.anchoredPos;

        if(Math.abs(target.x() - current.x()) < 0.001f){
            if(Math.abs(target.y() - current.y()) < 0.001f){
                if(Math.abs(target.z() - current.z()) < 0.001f){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the target position of the entity.
     * @return The GridPosition object representing the target position.
     */
    public GridPosition getTargetPosition(){
        return this.targetPosition;
    }

    /**
     * Gets the collision box of the entity.
     * @return The CollisionBox object associated with the entity.
     */
    public CollisionBox getCollisionBox(){
        return this.collisionBox;
    }

    /**
     * Sets the movement vector for the entity.
     * @param newVector The new movement Vector object to be assigned to the entity.
     */
    public void setMovementVector(Vector newVector){
        this.movementVector = newVector;
    }

    
    /**
     * Sets the target position and speed for the entity.
     * @param targetPos The target GridPosition object.
     * @param speed The speed value to reach the target position.
     */
    public void setTargetPosition(GridPosition targetPos, float speed){
        Vector newMovementVector = Vector.getVector(this.getPosition(), targetPos);
        if(newMovementVector.magnitude() == 0){
            newMovementVector = new Vector(new float[]{0, 0, 0});
        } else {
            newMovementVector = newMovementVector.normalized().scaledBy(speed);
        }
        if(this.collisionBox == null){
            this.movementVector = newMovementVector;
            this.targetPosition = targetPos;
        } else {
            float x = newMovementVector.get(0);
            float y = this.movementVector.get(1);
            float z = newMovementVector.get(2);
            this.movementVector = new Vector(new float[]{x, y, z});
            this.targetPosition = targetPos;
        }
    }

    /**
     * Sets the rotation delta, frame count, and acceleration for the entity.
     * @param delta The RelativeRotation object representing the rotation delta.
     * @param frames The number of frames for the rotation, 
     * if it is -1, the rotation will continue indefinitely.
     * @param acceleration The RelativeRotation object representing the rotation acceleration.
     */
    public void setRotationDelta(RelativeRotation delta, int frames, RelativeRotation acceleration){
       this.rotationDelta = delta;
       this.rotationFrameCount = frames;

       if(acceleration == null){
           this.rotationDeltaDelta = new RelativeRotation(0, 0, 0);
       } else {
           this.rotationDeltaDelta = acceleration;
       }
    }

    /**
     * Gets the movement vector of the entity.
     * @return The Vector object representing the movement vector.
     */
    public Vector getMovementVector(){
        return this.movementVector;
    }

    /**
     * Checks if the entity is moving.
     * @return true if the entity is moving, false otherwise.
     */
    public boolean isMoving(){
        float magnitude = this.movementVector.magnitude();
        if(magnitude == 0){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof Entity)){
            return false;
        } else {
            Entity otherEntity = (Entity) (object);
            if(this.anchoredPos.equals(otherEntity.anchoredPos)){
                if(this.rotation.equals(otherEntity.rotation)){
                    if(this.movementVector.equals(otherEntity.movementVector)){
                        if(this.targetPosition.equals(otherEntity.targetPosition)){
                            if(this.collisionBox.equals(otherEntity.collisionBox)){
                                if(this.rotationDelta.equals(otherEntity.rotationDelta)){
                                    if(this.rotationDeltaDelta.equals(otherEntity.rotationDeltaDelta)){
                                        if(this.rotationFrameCount == otherEntity.rotationFrameCount){
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
    }
}
