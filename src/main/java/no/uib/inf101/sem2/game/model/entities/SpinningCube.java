package no.uib.inf101.sem2.game.model.entities;

import java.io.File;

import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public class SpinningCube {
    private static final File CUBE_MODEL = new File("src/main/resources/shapes/brick_cube.trym");

    Entity spinningCubeEntity;

    public SpinningCube(Position3D pos, RelativeRotation startRotation, RelativeRotation rotationDelta){
        ShapeData cubeShape = new ShapeData(pos, startRotation, CUBE_MODEL);
        this.spinningCubeEntity = new Entity(cubeShape);

        this.spinningCubeEntity.setRotationDelta(rotationDelta, -1);
    }

    public Entity getEntity(){
        return spinningCubeEntity;
    }
}
