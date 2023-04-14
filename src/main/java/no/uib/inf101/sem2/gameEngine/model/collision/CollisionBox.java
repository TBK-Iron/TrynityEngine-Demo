package no.uib.inf101.sem2.gameEngine.model.collision;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

/**
 * This class represents a collision box in 3D space for detecting and resolving collisions between game objects.
 * The collision box is defined by two opposite corners, pos1 and pos2.
 */
public final class CollisionBox {
    final GridPosition pos1;
    final GridPosition pos2;
    static final float MARGIN = 0.001f;

    public CollisionBox(GridPosition pos1, GridPosition pos2){
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    /**
     * Checks if this collision box is colliding with another collision box.
     *
     * @param otherBox The other collision box to check for collision.
     * @param dispPosOther The displacement position of the other collision box.
     * @return True if the collision boxes are colliding, false otherwise.
     */
    public boolean isColliding(CollisionBox otherBox, GridPosition dispPosOther) {

        GridPosition other1 = new Position3D(otherBox.pos1.x() + dispPosOther.x(), otherBox.pos1.y() + dispPosOther.y(), otherBox.pos1.z() + dispPosOther.z());
        GridPosition other2 = new Position3D(otherBox.pos2.x() + dispPosOther.x(), otherBox.pos2.y() + dispPosOther.y(), otherBox.pos2.z() + dispPosOther.z());

        return isCollidingAxis(pos1.x(), pos2.x(), other1.x(), other2.x()) &&
                isCollidingAxis(pos1.y(), pos2.y(), other1.y(), other2.y()) &&
                isCollidingAxis(pos1.z(), pos2.z(), other1.z(), other2.z());
    }

    /**
     * Helper method to check if two intervals on a single axis are colliding.
     *
     * @param a1 The first value of the first interval.
     * @param a2 The second value of the first interval.
     * @param b1 The first value of the second interval.
     * @param b2 The second value of the second interval.
     * @return True if the intervals are colliding, false otherwise.
     */
    private static boolean isCollidingAxis(float a1, float a2, float b1, float b2) {
        float minA = Math.min(a1, a2);
        float maxA = Math.max(a1, a2);
        float minB = Math.min(b1, b2);
        float maxB = Math.max(b1, b2);

        return !(maxA < minB || minA > maxB);
    }

    /**
     * Calculates the collision position between this collision box and another collision box.
     *
     * @param otherBox The other collision box to calculate the collision position with.
     * @param beforePos The position of the other collision box before the collision.
     * @param afterPos The position of the other collision box after the collision.
     * @return The position of otherBox that is the closest to thisBox without colliding. With a small margin
     */
    public GridPosition getCollisionPos(CollisionBox otherBox, GridPosition beforePos, GridPosition afterPos){
        
        GridPosition otherPos1bef = new Position3D(otherBox.pos1.x() + beforePos.x(), otherBox.pos1.y() + beforePos.y(), otherBox.pos1.z() + beforePos.z());
        GridPosition otherPos2bef = new Position3D(otherBox.pos2.x() + beforePos.x(), otherBox.pos2.y() + beforePos.y(), otherBox.pos2.z() + beforePos.z());

        GridPosition otherPos1Aft = new Position3D(otherBox.pos1.x() + afterPos.x(), otherBox.pos1.y() + afterPos.y(), otherBox.pos1.z() + afterPos.z());
        GridPosition otherPos2Aft = new Position3D(otherBox.pos2.x() + afterPos.x(), otherBox.pos2.y() + afterPos.y(), otherBox.pos2.z() + afterPos.z());

        GridPosition collisionPos;

        if(isCollidingAxis(pos1.x(), pos2.x(), otherPos1bef.x(), otherPos2bef.x()) != isCollidingAxis(pos1.x(), pos2.x(), otherPos1Aft.x(), otherPos2Aft.x())){
            float collX;
            if(beforePos.x() < afterPos.x()){
                collX = afterPos.x() - Math.abs(Math.max(otherPos1Aft.x(), otherPos2Aft.x()) - Math.min(this.pos1.x(), this.pos2.x())) - CollisionBox.MARGIN;
            } else {
                collX = afterPos.x() + Math.abs(Math.min(otherPos1Aft.x(), otherPos2Aft.x()) - Math.max(this.pos1.x(), this.pos2.x())) + CollisionBox.MARGIN;
                
            }
            collisionPos = new Position3D(collX, afterPos.y(), afterPos.z());
        } else if(isCollidingAxis(pos1.y(), pos2.y(), otherPos1bef.y(), otherPos2bef.y()) != isCollidingAxis(pos1.y(), pos2.y(), otherPos1Aft.y(), otherPos2Aft.y())){
            float collY;
            if(beforePos.y() < afterPos.y()){
                collY = afterPos.y() - Math.abs(Math.max(otherPos1Aft.y(), otherPos2Aft.y()) - Math.min(this.pos1.y(), this.pos2.y())) - CollisionBox.MARGIN;
            } else {
                collY = afterPos.y() + Math.abs(Math.min(otherPos1Aft.y(), otherPos2Aft.y()) - Math.max(this.pos1.y(), this.pos2.y())) + CollisionBox.MARGIN;
                
            }
            collisionPos = new Position3D(afterPos.x(), collY, afterPos.z());
        } else if(isCollidingAxis(pos1.z(), pos2.z(), otherPos1bef.z(), otherPos2bef.z()) != isCollidingAxis(pos1.z(), pos2.z(), otherPos1Aft.z(), otherPos2Aft.z())){
            float collZ;
            if(beforePos.z() < afterPos.z()){
                collZ = afterPos.z() - Math.abs(Math.max(otherPos1Aft.z(), otherPos2Aft.z()) - Math.min(this.pos1.z(), this.pos2.z())) - CollisionBox.MARGIN;
            } else {
                collZ = afterPos.z() + Math.abs(Math.min(otherPos1Aft.z(), otherPos2Aft.z()) - Math.max(this.pos1.z(), this.pos2.z())) + CollisionBox.MARGIN;
                
            }
            collisionPos = new Position3D(afterPos.x(), afterPos.y(), collZ);
        } else {
            collisionPos = beforePos;
        }

        return collisionPos;
    }
    
}
