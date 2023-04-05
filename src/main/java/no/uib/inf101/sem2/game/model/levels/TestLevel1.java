package no.uib.inf101.sem2.game.model.levels;

import java.io.File;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.model.shape.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

public class TestLevel1 implements Level {

    public TestLevel1() {}

    @Override
    public ArrayList<ShapeData> loadShapes() {
        ArrayList<ShapeData> shapes = new ArrayList<>();
        shapes.add(new ShapeData(new Position3D(0, 0, 0), new RelativeRotation(0, 0), new File("src/main/resources/tunnel.trym")));
        shapes.add(new ShapeData(new Position3D(0, 0, 2.5f), new RelativeRotation((float) (Math.PI/10), (float) (Math.PI/4 - 0.2)), new File("src/main/resources/cube.trym")));

        return shapes;
    }

    @Override
    public ArrayList<ShapeData> loadEntities() {
        ArrayList<ShapeData> entities = new ArrayList<>();

        return entities;
    }

    @Override
    public ArrayList<ShapeData> loadEntityCollision() {
        ArrayList<ShapeData> entityCollisionShapes = new ArrayList<>();

        return entityCollisionShapes;
    }

    @Override
    public ArrayList<CollisionBox> loadCollisionBoxes() {
        ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();

        return collisionBoxes;
    }
}
