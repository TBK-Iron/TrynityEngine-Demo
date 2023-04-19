package no.uib.inf101.sem2.gameEngine.model.collision;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

/**
 * This class represents a collision box in 3D space for detecting and resolving collisions between game objects.
 * The collision box is defined by two opposite corners, minPos and maxPos.
 */
public final class CollisionBox {
    final GridPosition minPos;
    final GridPosition maxPos;
    static final float MARGIN = 0.001f;

    public CollisionBox(GridPosition pos1, GridPosition pos2){
        this.minPos = new Position3D(Math.min(pos1.x(), pos2.x()), Math.min(pos1.y(), pos2.y()), Math.min(pos1.z(), pos2.z()));
        this.maxPos = new Position3D(Math.max(pos1.x(), pos2.x()), Math.max(pos1.y(), pos2.y()), Math.max(pos1.z(), pos2.z()));
    }

    public CollisionBox translatedBy(Vector vector){
        GridPosition newMinPos = Vector.add(new Vector((Position3D) minPos), vector).getPoint();
        GridPosition newMaxPos = Vector.add(new Vector((Position3D) maxPos), vector).getPoint();
        return new CollisionBox(newMinPos, newMaxPos);
    }

    /**
     * Checks if this collision box is colliding with another collision box.
     *
     * @param otherBox The other collision box to check for collision.
     * @param dispPosOther The displacement position of the other collision box.
     * @return True if the collision boxes are colliding, false otherwise.
     */
    public boolean isColliding(CollisionBox otherBox, GridPosition dispPosOther) {

        GridPosition other1 = new Position3D(otherBox.minPos.x() + dispPosOther.x(), otherBox.minPos.y() + dispPosOther.y(), otherBox.minPos.z() + dispPosOther.z());
        GridPosition other2 = new Position3D(otherBox.maxPos.x() + dispPosOther.x(), otherBox.maxPos.y() + dispPosOther.y(), otherBox.maxPos.z() + dispPosOther.z());

        return isCollidingAxis(minPos.x(), maxPos.x(), other1.x(), other2.x()) &&
                isCollidingAxis(minPos.y(), maxPos.y(), other1.y(), other2.y()) &&
                isCollidingAxis(minPos.z(), maxPos.z(), other1.z(), other2.z());
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
        
        GridPosition otherminPosbef = new Position3D(otherBox.minPos.x() + beforePos.x(), otherBox.minPos.y() + beforePos.y(), otherBox.minPos.z() + beforePos.z());
        GridPosition othermaxPosbef = new Position3D(otherBox.maxPos.x() + beforePos.x(), otherBox.maxPos.y() + beforePos.y(), otherBox.maxPos.z() + beforePos.z());

        GridPosition otherminPosAft = new Position3D(otherBox.minPos.x() + afterPos.x(), otherBox.minPos.y() + afterPos.y(), otherBox.minPos.z() + afterPos.z());
        GridPosition othermaxPosAft = new Position3D(otherBox.maxPos.x() + afterPos.x(), otherBox.maxPos.y() + afterPos.y(), otherBox.maxPos.z() + afterPos.z());

        GridPosition collisionPos;

        if(isCollidingAxis(minPos.x(), maxPos.x(), otherminPosbef.x(), othermaxPosbef.x()) != isCollidingAxis(minPos.x(), maxPos.x(), otherminPosAft.x(), othermaxPosAft.x())){
            float collX;
            if(beforePos.x() < afterPos.x()){
                collX = afterPos.x() - Math.abs(Math.max(otherminPosAft.x(), othermaxPosAft.x()) - Math.min(this.minPos.x(), this.maxPos.x())) - CollisionBox.MARGIN;
            } else {
                collX = afterPos.x() + Math.abs(Math.min(otherminPosAft.x(), othermaxPosAft.x()) - Math.max(this.minPos.x(), this.maxPos.x())) + CollisionBox.MARGIN;
                
            }
            collisionPos = new Position3D(collX, afterPos.y(), afterPos.z());
        } else if(isCollidingAxis(minPos.y(), maxPos.y(), otherminPosbef.y(), othermaxPosbef.y()) != isCollidingAxis(minPos.y(), maxPos.y(), otherminPosAft.y(), othermaxPosAft.y())){
            float collY;
            if(beforePos.y() < afterPos.y()){
                collY = afterPos.y() - Math.abs(Math.max(otherminPosAft.y(), othermaxPosAft.y()) - Math.min(this.minPos.y(), this.maxPos.y())) - CollisionBox.MARGIN;
            } else {
                collY = afterPos.y() + Math.abs(Math.min(otherminPosAft.y(), othermaxPosAft.y()) - Math.max(this.minPos.y(), this.maxPos.y())) + CollisionBox.MARGIN;
                
            }
            collisionPos = new Position3D(afterPos.x(), collY, afterPos.z());
        } else if(isCollidingAxis(minPos.z(), maxPos.z(), otherminPosbef.z(), othermaxPosbef.z()) != isCollidingAxis(minPos.z(), maxPos.z(), otherminPosAft.z(), othermaxPosAft.z())){
            float collZ;
            if(beforePos.z() < afterPos.z()){
                collZ = afterPos.z() - Math.abs(Math.max(otherminPosAft.z(), othermaxPosAft.z()) - Math.min(this.minPos.z(), this.maxPos.z())) - CollisionBox.MARGIN;
            } else {
                collZ = afterPos.z() + Math.abs(Math.min(otherminPosAft.z(), othermaxPosAft.z()) - Math.max(this.minPos.z(), this.maxPos.z())) + CollisionBox.MARGIN;
                
            }
            collisionPos = new Position3D(afterPos.x(), afterPos.y(), collZ);
        } else {
            collisionPos = beforePos;
        }

        return collisionPos;
    }
    
    /**
     * Tests if a ray intersects with an axis-aligned bounding box (AABB) defined by the object's minPos and maxPos.
     *
     * @param rayOrigin           The starting point of the ray, represented as a GridPosition.
     * @param normalizedDirectionVector The normalized direction vector of the ray, pointing in the camera's direction.
     * @return true if the ray intersects with the AABB, false otherwise.
     * @author Partially ChatGPT
     */

    //TODO: Fix this method
    public boolean rayIntersection(GridPosition rayOrigin, Vector normalizedDirectionVector){
        Vector minVectorBox = new Vector((Position3D) minPos);
        Vector maxVectorBox = new Vector((Position3D) maxPos);
        
        Vector vectorOrigin = new Vector((Position3D) rayOrigin);

        Vector tMin = Vector.divide(Vector.subtract(minVectorBox, vectorOrigin), normalizedDirectionVector);
        Vector tMax = Vector.divide(Vector.subtract(maxVectorBox, vectorOrigin), normalizedDirectionVector);

        Vector t1 = Vector.minVector(tMin, tMax);
        Vector t2 = Vector.maxVector(tMin, tMax);

        float tNear = Math.max(Math.max(t1.get(0), t1.get(1)), t1.get(2));
        float tFar = Math.min(Math.min(t2.get(0), t2.get(1)), t2.get(2));

        if(tFar >= tNear && tFar > 0){
            return false;
        } else {
            return true;
        }
    }
}

