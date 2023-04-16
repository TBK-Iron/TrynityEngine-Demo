package no.uib.inf101.sem2.game.model.entities.enemies;

import java.io.File;

import no.uib.inf101.sem2.game.model.entities.EngineEntity;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

public class Zombie implements Enemy{

    private static final float START_HEALTH = 50;
    private static final float MOVE_SPEED = 0.1f;
    private static final File ZOMBIE_MODEL = new File("src/main/resources/shapes/zombie_A.trym");
    private static final CollisionBox ZOMBIE_COLLISION_BOX = new CollisionBox(new Position3D(0.5f, 2, 0.5f), new Position3D(-0.5f, 0, -0.5f));

    private EngineEntity zombieEntity;
    private float health;

    public Zombie(GridPosition startPosition, RelativeRotation startRotation){
        ShapeData shapeData = new ShapeData(startPosition, startRotation, Zombie.ZOMBIE_MODEL);
        zombieEntity = new Entity(shapeData, Zombie.ZOMBIE_COLLISION_BOX);
        
        this.health = Zombie.START_HEALTH;
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public void damage(float amount) {
        this.health -= amount;
    }

    @Override
    public void setTargetPosition(GridPosition targetPos) {
        this.zombieEntity.setTargetPosition(targetPos, Zombie.MOVE_SPEED);

        RelativeRotation rotation =new RelativeRotation(0, Vector.getVectorRotation(this.zombieEntity.getMovementVector()).getLeftRight());
        this.zombieEntity.setRotation(rotation);
    }

    @Override
    public GridPosition getPosition() {
        return this.zombieEntity.getPosition();
    }
}
