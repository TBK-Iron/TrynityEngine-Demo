package no.uib.inf101.sem2.game.model.entities;

import java.io.File;

import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

/**
 * Class representing a spinning cube in the game world.
 * The cube is created as an Entity with a specified position and rotation.
 */
public class SpinningCube {
    private static final File CUBE_MODEL = new File("src/main/resources/shapes/brick_cube.trym");

    private Entity spinningCubeEntity;

    /**
     * Constructs a new SpinningCube with the given position, initial rotation, and rotation delta.
     *
     * @param pos The position of the spinning cube in the game world.
     * @param startRotation The initial rotation of the spinning cube.
     * @param rotationDelta The change in rotation per frame of the spinning cube.
     */
    public SpinningCube(Position3D pos, RelativeRotation startRotation, RelativeRotation rotationDelta){
        ShapeData cubeShape = new ShapeData(pos, startRotation, CUBE_MODEL);
        this.spinningCubeEntity = new Entity(cubeShape);

        this.spinningCubeEntity.setRotationDelta(rotationDelta, -1);
    }

    /**
     * Returns the spinning cube as an Entity object.
     *
     * @return The spinning cube Entity.
     */
    public Entity getEntity(){
        return spinningCubeEntity;
    }
}
