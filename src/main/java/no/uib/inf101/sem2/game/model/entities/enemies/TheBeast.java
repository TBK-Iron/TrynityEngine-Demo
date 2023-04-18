package no.uib.inf101.sem2.game.model.entities.enemies;

import java.io.File;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;


public class TheBeast {
    private static final float START_HEALTH = 500;
    private static final float MOVE_SPEED = 0.04f;
    private static final File BEAST_MODEL = new File("src/main/resources/shapes/the_beast.trym");
    private static final CollisionBox BEAST_COLLISION_BOX = new CollisionBox(new Position3D(5, 10, 5), new Position3D(-5, 0, -5));

    public TheBeast(GridPosition startPosition, RelativeRotation startRotation, float activationRadius){

    }
}
