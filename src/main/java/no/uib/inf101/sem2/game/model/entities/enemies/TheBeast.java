package no.uib.inf101.sem2.game.model.entities.enemies;

import java.io.File;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;


public class TheBeast implements Enemy{
    private static final float START_HEALTH = 500;
    private static final float MOVE_SPEED = 0.04f;
    private static final File BEAST_MODEL = new File("src/main/resources/shapes/the_beast.trym");
    private static final CollisionBox BEAST_COLLISION_BOX = new CollisionBox(new Position3D(5, 10, 5), new Position3D(-5, 0, -5));
    private static final float activationRadius = 15;
    private static final float damageRadius = 6;
    private static final float damage = 1f; //Damage per tick

    private Entity beastEntity;
    private float health;


    public TheBeast(GridPosition startPosition, RelativeRotation startRotation, float activationRadius){
        this.beastEntity = new Entity(new ShapeData(startPosition, startRotation, TheBeast.BEAST_MODEL), TheBeast.BEAST_COLLISION_BOX);
        this.health = TheBeast.START_HEALTH;
    }

    private TheBeast(TheBeast primaryBeast){
        this.beastEntity = new Entity(primaryBeast.beastEntity);
        this.health = primaryBeast.health;
    }

    public Enemy clone(){
        return new TheBeast(this);
    }

    @Override
    public boolean isWithinRadius(GridPosition pos) {
        Vector distanceVector = Vector.getVector(pos, this.beastEntity.getPosition());
        float dist = distanceVector.magnitude();
        if(dist < activationRadius){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Entity getEntity() {
        return this.beastEntity;
    }

    @Override
    public boolean isAlive() {
        if(this.health > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void kill() {

        RelativeRotation startRotationSpeed = new RelativeRotation( 0, 0);
        RelativeRotation rotationAcceleration = new RelativeRotation( 0, 0.5f);

        this.beastEntity.setRotationDelta(startRotationSpeed, -1, rotationAcceleration);
    }

    @Override
    public void damage(float amount) {
        this.health -= amount;
    }

    @Override
    public float damageTo(GridPosition entityPos) {
        Vector distanceVector = Vector.getVector(entityPos, this.beastEntity.getPosition());
        float dist = distanceVector.magnitude();
        if(dist < damageRadius){
            return damage;
        } else {
            return 0;
        }
    }

    @Override
    public void setTargetPosition(GridPosition targetPos) {
        if(this.isAlive()){
            this.beastEntity.setTargetPosition(targetPos, TheBeast.MOVE_SPEED);

            RelativeRotation rotation = new RelativeRotation(0, Vector.getVectorRotation(this.beastEntity.getMovementVector()).getLeftRight());
            this.beastEntity.setRotation(rotation);
        }
    }

    @Override
    public GridPosition getPosition() {
        return this.beastEntity.getPosition();
    }
}
