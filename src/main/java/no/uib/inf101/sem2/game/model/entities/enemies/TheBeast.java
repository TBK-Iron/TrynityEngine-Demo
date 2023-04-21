package no.uib.inf101.sem2.game.model.entities.enemies;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;


public class TheBeast implements Enemy{
    public static final float START_HEALTH = 200;
    private static final float MOVE_SPEED = 0.03f;
    private static final File BEAST_MODEL = new File("src/main/resources/shapes/the_beast.trym");
    private static final CollisionBox BEAST_COLLISION_BOX = new CollisionBox(new Position3D(5, 10, 5), new Position3D(-5, 0, -5));
    private static final float damageRadius = 6;
    private static final float damage = 1f; //Damage per tick

    private static final String ambientSound = null;
    private static final String hurtSound = null;
    private static final String deathSound = null;

    private Entity beastEntity;
    private float health;
    private float activationRadius;


    public TheBeast(GridPosition startPosition, RelativeRotation startRotation, float activationRadius){
        this.beastEntity = new Entity(new ShapeData(startPosition, startRotation, TheBeast.BEAST_MODEL));
        this.beastEntity.setCollision(TheBeast.BEAST_COLLISION_BOX);
        this.health = TheBeast.START_HEALTH;
        this.activationRadius = activationRadius;
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
        this.beastEntity.setTargetPosition(this.beastEntity.getPosition(), 0);
        this.beastEntity.setCollision(null);

        RelativeRotation startRotationSpeed = new RelativeRotation( 0, 0);
        RelativeRotation rotationAcceleration = new RelativeRotation( 0, 0.02f);

        this.beastEntity.setRotationDelta(startRotationSpeed, -1, rotationAcceleration);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            float x = this.beastEntity.getPosition().x();
            float y = this.beastEntity.getPosition().y() + 400;
            float z = this.beastEntity.getPosition().z();

            this.beastEntity.setTargetPosition(new Position3D(x, y, z), MOVE_SPEED*50);
        };

        executor.schedule(task, 3, TimeUnit.SECONDS);
    }

    @Override
    public void damage(float amount) {
        this.health -= amount;
        System.out.println(this.health);
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

    @Override
    public String getAmbientSound(){
        return TheBeast.ambientSound;
    }

    @Override
    public String getHurtSound(){
        return TheBeast.hurtSound;
    }

    @Override
    public String getDeathSound(){
        return TheBeast.deathSound;
    }

    @Override
    public float getNoiseVolumeRelativeTo(GridPosition pos){
        Vector distanceVector = Vector.getVector(pos, this.beastEntity.getPosition());
        float dist = distanceVector.magnitude();
        if(dist > 20){
            return 0;
        } else {
            float attenuationFactor = 1 - (dist/20);
            float volume = (float) (Math.log10(attenuationFactor) * 20);

            return volume;
        }
    }
}
