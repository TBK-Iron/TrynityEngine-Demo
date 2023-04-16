package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.game.model.entities.EngineEntity;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;


public class Entity extends Shape3D implements EngineEntity {

    CollisionBox collisionBox;
    Vector movementVector;
    RelativeRotation rotationDelta;
    GridPosition targetPosition;

    public Entity(ShapeData shapeData, CollisionBox collisionBox) {
        super(shapeData);
        this.movementVector = new Vector(new float[]{0, 0, 0});
        this.collisionBox = collisionBox;
    }

    public Entity(ShapeData shapeData){
        this(shapeData, null);
    }

    public void setRotation(RelativeRotation rotation){
        this.rotation = rotation;
    }
    
    public void rotate(){
        this.rotation = this.rotation.add(this.rotationDelta);
    }

    public void setPosition(GridPosition position){
        this.anchoredPos = position;
    }

    public boolean targetReached(){
        this.anchoredPos = Vector.add(new Vector((Position3D) this.anchoredPos), this.movementVector).getPoint();
        Vector calibratedMovementVector = Vector.getVector(anchoredPos, targetPosition);
        if(Vector.dotProduct(this.movementVector, calibratedMovementVector) < 0){
            return true;
        } else {
            return false;
        }
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
        newMovementVector = newMovementVector.normalized().scaledBy(speed);
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

    public void setRotationDelta(RelativeRotation delta){
        this.rotation = this.rotation.add(delta);
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
