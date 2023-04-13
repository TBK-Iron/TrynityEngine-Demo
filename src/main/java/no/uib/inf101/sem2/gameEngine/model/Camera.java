package no.uib.inf101.sem2.gameEngine.model;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public class Camera {
    GridPosition position;
    RelativeRotation rotation;
    CollisionBox collisionBox;

    public Camera(GridPosition startPos, RelativeRotation startRot){
        this.position = startPos;
        this.rotation = startRot;
    }

    public void setCollision(CollisionBox box){
        this.collisionBox = box;
    }

    public void setPos(GridPosition pos){
        this.position = pos;
    }

    public void setRotation(RelativeRotation rotation){
        this.rotation = rotation;
    }

    public GridPosition getPos(){
        return this.position;
    }

    public RelativeRotation getRotation(){
        return this.rotation;
    }

    public CollisionBox getCollisionBox(){
        return this.collisionBox;
    }
}
