package no.uib.inf101.sem2.gameEngine.model.collision;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

public class CollisionDetector {

    ArrayList<CollisionBox> fixedCollisionBoxes;
    public CollisionDetector(){
        fixedCollisionBoxes = new ArrayList<>();
    }

    public void addCollisionBox(CollisionBox box){
        fixedCollisionBoxes.add(box);
    }

    public CollisionBox getCollidingBox(CollisionBox colBox, GridPosition anchoredPos){
        for(CollisionBox box : fixedCollisionBoxes){
            if(box.isColliding(colBox, anchoredPos)){
                return box;
            }
        }
        return null;
    }
}
