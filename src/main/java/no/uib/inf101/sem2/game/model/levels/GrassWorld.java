package no.uib.inf101.sem2.game.model.levels;

import java.io.File;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.Player;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemySpawner;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

public class GrassWorld implements Level {

    @Override
    public String getLevelName() {
        return "Grass World";
    }

    @Override
    public Player getPlayer(){
        GridPosition startPos = new Position3D(0, 0, 0);
        RelativeRotation startRot = new RelativeRotation(0, 0);
        CollisionBox playerBox = new CollisionBox(new Position3D(-0.5f, 0.5f, -0.5f), new Position3D(0.5f, -1.999f, 0.5f));
        
        return new Player(startPos, startRot, playerBox);
    }

    @Override
    public ArrayList<ShapeData> loadShapes() {
        ArrayList<ShapeData> shapes = new ArrayList<>();

        shapes.add(new ShapeData(new Position3D(-25, -2, -25), new RelativeRotation(0, 0), new File("src/main/resources/shapes/grass_groundplane.trym")));
        shapes.add(new ShapeData(new Position3D(5, 0, 5), new RelativeRotation(0, 0), new File("src/main/resources/shapes/building_A.trym")));
        shapes.add(new ShapeData(new Position3D(20, 0, 5), new RelativeRotation(0, 0), new File("src/main/resources/shapes/building_B.trym")));
        shapes.add(new ShapeData(new Position3D(-5, -2, 0), new RelativeRotation(0, 0), new File("src/main/resources/shapes/tree.trym")));
        shapes.add(new ShapeData(new Position3D(-8, -2, 10), new RelativeRotation(0, (float) -Math.PI/8), new File("src/main/resources/shapes/tree.trym")));
        shapes.add(new ShapeData(new Position3D(1, -2, -7), new RelativeRotation((float) -Math.PI/4, 0), new File("src/main/resources/shapes/tree.trym")));
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
    public ArrayList<CollisionBox> loadCollisionBoxes() {
        ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();

        collisionBoxes.add(new CollisionBox(new Position3D(-25, -2, -25), new Position3D(25, -20, 25)));
        collisionBoxes.add(new CollisionBox(new Position3D(7, -2, 11), new Position3D(3, 2, 7)));
        return collisionBoxes;
    }

    @Override
    public ArrayList<CollisionBox> loadKillBoxes() {
        ArrayList<CollisionBox> killBoxes = new ArrayList<>();

        return killBoxes;
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        return enemies;
    }

    @Override
    public ArrayList<EnemySpawner> loadEnemySpawners() {
        ArrayList<EnemySpawner> enemySpawners = new ArrayList<>();

        return enemySpawners;
    }

    @Override
    public ArrayList<Door> loadDoors() {
        ArrayList<Door> doors = new ArrayList<>();

        return doors;
    }
}
