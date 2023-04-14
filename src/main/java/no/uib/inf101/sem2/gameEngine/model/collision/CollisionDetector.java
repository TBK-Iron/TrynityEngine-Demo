package no.uib.inf101.sem2.gameEngine.model.collision;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

/**
 * The CollisionDetector class is responsible for detecting collisions between
 * {@link CollisionBox} objects.
 */
public class CollisionDetector {

    ArrayList<CollisionBox> fixedCollisionBoxes;

    /**
     * Initializes a new CollisionDetector instance.
     */
    public CollisionDetector(){
        fixedCollisionBoxes = new ArrayList<>();
    }

    /**
     * Adds a {@link CollisionBox} to the list of fixed collision boxes.
     *
     * @param box The {@link CollisionBox} to add.
     */
    public void addCollisionBox(CollisionBox box){
        fixedCollisionBoxes.add(box);
    }

    /**
     * Checks if a given {@link CollisionBox} is colliding with any of the fixed collision boxes.
     *
     * @param colBox      The {@link CollisionBox} to check for collisions.
     * @param anchoredPos The {@link GridPosition} of the collision box.
     * @return The first fixed {@link CollisionBox} that is colliding with the given collision box,
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
}
