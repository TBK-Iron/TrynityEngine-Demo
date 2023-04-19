package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;


public class Entity extends Shape3D {

    private final CollisionBox collisionBox;
    private Vector movementVector;
    private RelativeRotation rotationDelta;
    private RelativeRotation rotationDeltaDelta;
    private int rotationFrameCount;
   
    private GridPosition targetPosition;

    public Entity(ShapeData shapeData, CollisionBox collisionBox) {
        super(shapeData);
        this.movementVector = new Vector(new float[]{0, 0, 0});
        this.targetPosition = this.anchoredPos;
        System.out.println(this.movementVector);
        this.collisionBox = collisionBox;

        this.rotationDelta = new RelativeRotation(0, 0, 0);
        this.rotationDeltaDelta = new RelativeRotation(0, 0, 0);
        this.rotationFrameCount = 0;
    }

    public Entity(ShapeData shapeData){
        this(shapeData, null);
    }

    public Entity(Entity primaryEntity){
        super(primaryEntity);
    
        this.movementVector = primaryEntity.movementVector;
        this.targetPosition = primaryEntity.targetPosition;
        this.collisionBox = primaryEntity.collisionBox;

        this.rotationDelta = primaryEntity.rotationDelta;
        this.rotationDeltaDelta = primaryEntity.rotationDeltaDelta;
        this.rotationFrameCount = primaryEntity.rotationFrameCount;
    }

    public void setRotation(RelativeRotation rotation){
        this.rotation = rotation;
    }
    
    public void rotate(){
        if(rotationFrameCount >= 1){
            this.rotation = this.rotation.add(this.rotationDelta);
            rotationFrameCount--;
        } else if(this.rotationFrameCount == -1){
            this.rotation = this.rotation.add(this.rotationDelta);
            this.rotationDelta = this.rotationDelta.add(this.rotationDeltaDelta);
        }
    }

    public void setPosition(GridPosition position){
        this.anchoredPos = position;
    }

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

    public GridPosition getTargetPosition(){
        return this.targetPosition;
    }

    public CollisionBox getCollisionBox(){
        return this.collisionBox;
    }

    public void setMovementVector(Vector newVector){
        this.movementVector = newVector;
    }

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

    public void setRotationDelta(RelativeRotation delta, int frames, RelativeRotation acceleration){
       this.rotationDelta = delta;
       this.rotationFrameCount = frames;

       if(acceleration == null){
           this.rotationDeltaDelta = new RelativeRotation(0, 0, 0);
       } else {
           this.rotationDeltaDelta = acceleration;
       }
    }

    public Vector getMovementVector(){
        return this.movementVector;
    }

    public boolean isMoving(){
        float magnitude = this.movementVector.magnitude();
        if(magnitude == 0){
            return false;
        } else {
            return true;
        }
    }
}
