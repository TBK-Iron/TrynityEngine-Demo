package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;


public class Entity extends Shape3D {

    CollisionBox collisionBox;
    Vector movementVector;

    public Entity(ShapeData shapeData, CollisionBox collisionBox) {
        super(shapeData);
        this.movementVector = new Vector(new float[]{0, 0, 0});
        this.collisionBox = collisionBox;
    }

    
    public void setRotation(RelativeRotation newRotation){
        this.rotation = newRotation;
        
    }

    public void setPosition(GridPosition newPos){
        this.anchoredPos = newPos;
    }

    public CollisionBox getCollisionBox(){
        return this.collisionBox;
    }

    public void setMovementVector(Vector newVector){
        this.movementVector = newVector;
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
