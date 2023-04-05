package no.uib.inf101.sem2.gameEngine.model;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.grid3D.Grid;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public class Camera {
    GridPosition position;
    RelativeRotation rotation;
    ArrayList<GridPosition> collisionShapePoints;

    public Camera(GridPosition startPos, RelativeRotation startRot){
        this.position = startPos;
        this.rotation = startRot;

        this.collisionShapePoints = new ArrayList<>();
    }

    public void setCollision(ArrayList<GridPosition> points){
        this.collisionShapePoints = points;
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

    public ArrayList<GridPosition> getCollisionPoints(){
        return this.collisionShapePoints;
    }
}
