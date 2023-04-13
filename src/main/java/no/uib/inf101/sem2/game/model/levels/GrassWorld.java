package no.uib.inf101.sem2.game.model.levels;

import java.io.File;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.model.shape.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

public class GrassWorld implements Level {

    @Override
    public ArrayList<ShapeData> loadShapes() {
        ArrayList<ShapeData> shapes = new ArrayList<>();

        shapes.add(new ShapeData(new Position3D(-25, -2, -25), new RelativeRotation(0, 0), new File("src/main/resources/shapes/grass_groundplane.trym")));
        shapes.add(new ShapeData(new Position3D(5, 0, 5), new RelativeRotation(0, 0), new File("src/main/resources/shapes/building_A.trym")));
        shapes.add(new ShapeData(new Position3D(20, 0, 5), new RelativeRotation(0, 0), new File("src/main/resources/shapes/building_B.trym")));
        shapes.add(new ShapeData(new Position3D(-5, -2, 0), new RelativeRotation(0, 0), new File("src/main/resources/shapes/tree.trym")));
        shapes.add(new ShapeData(new Position3D(-8, -2, 10), new RelativeRotation(0, (float) Math.PI/8), new File("src/main/resources/shapes/tree.trym")));
        shapes.add(new ShapeData(new Position3D(1, -2, -7), new RelativeRotation((float) Math.PI/4, 0), new File("src/main/resources/shapes/tree.trym")));
        shapes.add(new ShapeData(new Position3D(4, -2, 15), new RelativeRotation(0, 0), new File("src/main/resources/shapes/tree.trym")));
        shapes.add(new ShapeData(new Position3D(4, -2, -5), new RelativeRotation(0, 0, 0), new File("src/main/resources/shapes/zombie_A.trym")));
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
