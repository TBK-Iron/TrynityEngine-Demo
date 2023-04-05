package no.uib.inf101.sem2.gameEngine.model;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

public class CollisionDetector {

    ArrayList<CollisionBox> collisionBoxes;
    public CollisionDetector(){
        collisionBoxes = new ArrayList<>();
    }

    public void addCollisionBox(CollisionBox box){
        collisionBoxes.add(box);
    }

    protected boolean isColliding(ArrayList<GridPosition> shapePoints, GridPosition anchoredPos){
        for(CollisionBox box : collisionBoxes){
            if(box.isColliding(shapePoints, anchoredPos)){
                return true;
            }
        }
        return false;
    }
}
