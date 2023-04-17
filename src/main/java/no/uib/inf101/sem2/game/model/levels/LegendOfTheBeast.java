package no.uib.inf101.sem2.game.model.levels;

import java.util.ArrayList;
import java.io.File;

import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemySpawner;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public class LegendOfTheBeast implements Level{

    @Override
    public RelativeRotation startRotation(){
        return new RelativeRotation(0, -(float) Math.PI/2);
    }

    @Override
    public Position3D startPosition(){
        return new Position3D(0, 0, 0);
    }

    @Override
    public ArrayList<ShapeData> loadShapes(){
        ArrayList<ShapeData> shapes = new ArrayList<>();

        shapes.add(new ShapeData(new Position3D(-14, -2, -2), new RelativeRotation(0, 0), new File("src/main/resources/shapes/hallway_L.trym")));
        shapes.add(new ShapeData(new Position3D(2, -2, -2), new RelativeRotation(0, (float) -Math.PI/2), new File("src/main/resources/shapes/hallway_cap.trym")));
        shapes.add(new ShapeData(new Position3D(-12, -2, 8), new RelativeRotation(0, 0), new File("src/main/resources/shapes/door_frame.trym")));

        return shapes;
    }

    @Override
    public ArrayList<CollisionBox> loadCollisionBoxes() {
        ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();

        return collisionBoxes;
    }

    @Override
    public ArrayList<Enemy> loadEnemies(){
        ArrayList<Enemy> enemies = new ArrayList<>();

        return enemies;
    }

    @Override
    public ArrayList<EnemySpawner> loadEnemySpawners(){
        ArrayList<EnemySpawner> enemySpawners = new ArrayList<>();

        return enemySpawners;
    }

    @Override
    public ArrayList<Door> loadDoors(){
        ArrayList<Door> doors = new ArrayList<>();

        doors.add(new Door(new Position3D(-12, -2, 8), new RelativeRotation(0, 0), 3f));

        return doors;
    }

    @Override
    public ArrayList<ShapeData> loadEntities() {
        ArrayList<ShapeData> entities = new ArrayList<>();

        return entities;
    }
}
