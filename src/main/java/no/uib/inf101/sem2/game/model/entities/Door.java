package no.uib.inf101.sem2.game.model.entities;

import java.io.File;

import no.uib.inf101.sem2.gameEngine.model.EngineModel;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

public class Door {

    private static final float OPEN_SPEED = 0.1f;
    public static final File DOOR_MODEL = new File("src/main/resources/shapes/door.trym");

    private Entity doorEntity;
    private boolean isOpen;
    private final GridPosition startPos;
    private final float activationRadius;

    public Door(GridPosition position, RelativeRotation rotation, float activationRadius){
        ShapeData doorShape = new ShapeData(position, rotation, DOOR_MODEL);
        this.doorEntity = new Entity(doorShape);
        this.isOpen = false;

        this.startPos = position;
        this.activationRadius = activationRadius;
    }

    public boolean isWithinRadius(GridPosition camPos){
        Vector distanceVector = Vector.getVector(camPos, startPos);
        float dist = distanceVector.magnitude();
        if(dist > 2.5f){
            return false;
        } else {
            return true;
        }
    }

    public Entity getEntity(){
        return (Entity) this.doorEntity;
    }

    public void cycleState(){
        this.isOpen = !this.isOpen;
        GridPosition targetPos;
        if(this.isOpen){
            targetPos = new Position3D(this.startPos.x(), this.startPos.y() + 2.4f, this.startPos.z());
        } else {
            targetPos = startPos;
        }
        this.doorEntity.setTargetPosition(targetPos, Door.OPEN_SPEED);
    }

    public void open(){
        this.isOpen = true;
        GridPosition targetPos = new Position3D(this.startPos.x(), this.startPos.y() + 2.4f, this.startPos.z());
        this.doorEntity.setTargetPosition(targetPos, Door.OPEN_SPEED);
    }

    public void close(){
        this.isOpen = false;
        GridPosition targetPos = startPos;
        this.doorEntity.setTargetPosition(targetPos, Door.OPEN_SPEED);
    }
}
