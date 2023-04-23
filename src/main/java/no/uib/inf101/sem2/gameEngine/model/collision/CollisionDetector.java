package no.uib.inf101.sem2.gameEngine.model.collision;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

/**
 * The CollisionDetector class is responsible for detecting collisions between
 * CollisionBox objects.
 */
public class CollisionDetector {

    private ArrayList<CollisionBox> fixedCollisionBoxes;

    /**
     * Initializes a new CollisionDetector instance.
     */
    public CollisionDetector(){
        fixedCollisionBoxes = new ArrayList<>();
    }

    /**
     * Adds a CollisionBox to the list of fixed collision boxes.
     *
     * @param box The CollisionBox to add.
     */
    public void addCollisionBox(CollisionBox box){
        fixedCollisionBoxes.add(box);
    }

    /**
     * Checks if a given CollisionBox is colliding with any of the fixed collision boxes.
     *
     * @param colBox      The CollisionBox to check for collisions.
     * @param anchoredPos The GridPosition of the collision box.
     * @return The first fixed CollisionBox that is colliding with the given collision box,
     *         or null if there are no collisions.
     */
    public CollisionBox getCollidingBox(CollisionBox colBox, GridPosition anchoredPos){
        for(CollisionBox box : fixedCollisionBoxes){
            if(box.isColliding(colBox, anchoredPos)){
                return box;
            }
        }
        return null;
    }

    public void resetCollisionDetector(){
        this.fixedCollisionBoxes = new ArrayList<>();
    }

    public ArrayList<CollisionBox> getFixedCollisionBoxes(){
        return fixedCollisionBoxes;
    }
}
