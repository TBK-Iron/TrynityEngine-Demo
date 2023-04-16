package no.uib.inf101.sem2.game.model.entities;

import java.io.File;

import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

//TODO: Implement this class
public class Door {

    private static final float OPEN_SPEED = 0.1f;
    private static final File DOOR_MODEL = new File("src/main/resources/shapes/door.trym");

    private EngineEntity doorEntity;
    private boolean isOpen;
    private GridPosition startPos;

    public Door(GridPosition position, RelativeRotation rotation, boolean isOpen){
        ShapeData doorData = new ShapeData(position, rotation, Door.DOOR_MODEL);
        this.doorEntity = new Entity(doorData);
        this.isOpen = isOpen;

        if(isOpen){
            this.startPos = new Position3D(position.x(), position.y() - 2.5f, position.z());
        } else {
            this.startPos = position;
        }
    }

    public void cycleState(){
        this.isOpen = !this.isOpen;
        GridPosition targetPos;
        if(this.isOpen){
            targetPos = new Position3D(this.startPos.x(), this.startPos.y() + 2.5f, this.startPos.z());
        } else {
            targetPos = startPos;
        }
        this.doorEntity.setTargetPosition(targetPos, Door.OPEN_SPEED);
    }
}
