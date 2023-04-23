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

    public static final float START_HEALTH = 50;
    private static final float MOVE_SPEED = 0.03f;
    private static final File ZOMBIE_MODEL = new File("src/main/resources/shapes/zombie_A.trym");
    private static final CollisionBox ZOMBIE_COLLISION_BOX = new CollisionBox(new Position3D(0.5f, 2.25f, 0.5f), new Position3D(-0.5f, 0, -0.5f));
    private static final float damageRadius = 0.75f;
    private static final float damage = 0.2f; //Damage per tick

    public static final String ambientSound = "zombie_ambient";
    public static final String hurtSound = "zombie_hurt";
    public static final String deathSound = "zombie_hurt";

    private Entity zombieEntity;
    private float health;
    private final float activationRadius;

    public Zombie(GridPosition startPosition, RelativeRotation startRotation, float activationRadius){
        ShapeData shapeData = new ShapeData(startPosition, startRotation, Zombie.ZOMBIE_MODEL);
        zombieEntity = new Entity(shapeData);
        zombieEntity.setCollision(ZOMBIE_COLLISION_BOX);
        this.health = Zombie.START_HEALTH;
        this.activationRadius = activationRadius;
    }

    private Zombie(Zombie primaryZombie){
        this.zombieEntity = new Entity(primaryZombie.zombieEntity);
        this.health = primaryZombie.health;
        this.activationRadius = primaryZombie.activationRadius;
    }

    public Enemy clone(){
        return new Zombie(this);
    }

    @Override
    public boolean isWithinRadius(GridPosition camPos){
        Vector distanceVector = Vector.getVector(camPos, this.zombieEntity.getPosition());
        float dist = distanceVector.magnitude();
        if(dist > this.activationRadius){
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
            this.zombieEntity.setRotationDelta(new RelativeRotation((float) Math.PI/50, 0), 25, null);
        } else {
            this.zombieEntity.setRotationDelta(new RelativeRotation((float) -Math.PI/50, 0), 25, null);
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
        if(dist < Zombie.damageRadius && isAlive()){
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

    @Override
    public String getAmbientSound() {
        return Zombie.ambientSound;
    }

    @Override
    public String getHurtSound() {
        return Zombie.hurtSound;
    }

    @Override
    public String getDeathSound() {
        return Zombie.deathSound;
    }

    @Override
    public float getNoiseVolumeRelativeTo(GridPosition pos) {
        Vector distanceVector = Vector.getVector(pos, this.zombieEntity.getPosition());
        float dist = distanceVector.magnitude();
        float maxDistance = 20;

        if (dist > maxDistance) {
            return 0;
        } else {
            float minVolume = 0; // Minimum volume when at max distance
            float maxVolume = 1.0f; // Maximum volume when at zero distance
            float referenceDistance = 3; // The distance at which the sound has the max volume

            // Calculate volume using the inverse square law
            float volume = maxVolume * (float) Math.pow(referenceDistance / dist, 2);

            // Clamp the volume between minVolume and maxVolume
            volume = Math.max(minVolume, Math.min(maxVolume, volume));

            return volume;
        }
    }
}
