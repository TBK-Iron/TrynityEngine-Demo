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
    private static final CollisionBox ZOMBIE_COLLISION_BOX = new CollisionBox(new Position3D(0.5f, 2, 0.5f), new Position3D(-0.5f, 0, -0.5f));

    private Entity zombieEntity;
    private float health;
    private final float activationRadius;

    public Zombie(GridPosition startPosition, RelativeRotation startRotation, float activationRadius){
        ShapeData shapeData = new ShapeData(startPosition, startRotation, Zombie.ZOMBIE_MODEL);
        zombieEntity = new Entity(shapeData, Zombie.ZOMBIE_COLLISION_BOX);
        //zombieEntity = new Entity(shapeData);
        this.health = Zombie.START_HEALTH;
        this.activationRadius = activationRadius;
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
        this.zombieEntity.setRotationDelta(new RelativeRotation((float) Math.PI/90, 0), 90);
    }

    @Override
    public void damage(float amount) {
        this.health -= amount;
    }

    @Override
    public void setTargetPosition(GridPosition targetPos) {
        this.zombieEntity.setTargetPosition(targetPos, Zombie.MOVE_SPEED);

        RelativeRotation rotation =new RelativeRotation(0, -Vector.getVectorRotation(this.zombieEntity.getMovementVector()).getLeftRight());
        this.zombieEntity.setRotation(rotation);
    }

    @Override
    public GridPosition getPosition() {
        return this.zombieEntity.getPosition();
    }
}
