package no.uib.inf101.sem2.game.model.entities.enemies;

import java.io.File;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

public class Zombie implements Enemy{

    private static final float START_HEALTH = 50;
    private static final float MOVE_SPEED = 0.03f;
    private static final File ZOMBIE_MODEL = new File("src/main/resources/shapes/zombie_A.trym");
    private static final CollisionBox ZOMBIE_COLLISION_BOX = new CollisionBox(new Position3D(0.5f, 2.25f, 0.5f), new Position3D(-0.5f, 0, -0.5f));
    private static final float activationRadius = 7;
    private static final float damageRadius = 0.75f;
    private static final float damage = 0.2f; //Damage per tick


    private Entity zombieEntity;
    private float health;

    public Zombie(GridPosition startPosition, RelativeRotation startRotation){
        ShapeData shapeData = new ShapeData(startPosition, startRotation, Zombie.ZOMBIE_MODEL);
        zombieEntity = new Entity(shapeData, Zombie.ZOMBIE_COLLISION_BOX);
        //zombieEntity = new Entity(shapeData);
        this.health = Zombie.START_HEALTH;
    }

    @Override
    public boolean isWithinRadius(GridPosition camPos){
        Vector distanceVector = Vector.getVector(camPos, this.zombieEntity.getPosition());
        float dist = distanceVector.magnitude();
        if(dist > activationRadius){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Entity getEntity(){
        return this.zombieEntity;
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
    public void kill(){
        float randomVal = (float) Math.random();
        if(randomVal > 0.5f){
            this.zombieEntity.setRotationDelta(new RelativeRotation((float) Math.PI/50, 0), 25);
        } else {
            this.zombieEntity.setRotationDelta(new RelativeRotation((float) -Math.PI/50, 0), 25);
        }
        
        this.zombieEntity.setTargetPosition(getPosition(), Zombie.MOVE_SPEED);
    }

    @Override
    public void damage(float amount) {
        this.health -= amount;
        System.out.println("Remaining zombie health: " + this.health);
    }

    @Override
    public float damageTo(GridPosition entityPos){
        Vector distanceVector = Vector.getVector(entityPos, this.zombieEntity.getPosition());
        float dist = distanceVector.magnitude();
        if(dist < Zombie.damageRadius){
            return Zombie.damage;
        } else {
            return 0;
        }
    }

    @Override
    public void setTargetPosition(GridPosition targetPos) {
        if(this.isAlive()){
            this.zombieEntity.setTargetPosition(targetPos, Zombie.MOVE_SPEED);

            RelativeRotation rotation =new RelativeRotation(0, Vector.getVectorRotation(this.zombieEntity.getMovementVector()).getLeftRight());
            this.zombieEntity.setRotation(rotation);
        }
    }

    @Override
    public GridPosition getPosition() {
        return this.zombieEntity.getPosition();
    }
}
