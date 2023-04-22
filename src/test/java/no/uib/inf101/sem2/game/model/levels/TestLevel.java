package no.uib.inf101.sem2.game.model.levels;

import java.io.File;
import java.util.ArrayList;

import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.Player;
import no.uib.inf101.sem2.game.model.entities.SpinningCube;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemySpawner;
import no.uib.inf101.sem2.game.model.entities.enemies.Zombie;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

public class TestLevel implements Level {

    @Override
    public String getLevelName() {
        return "Test Level";
    }

    @Override
    public String getLevelMusic() {
        return null;
    }

    @Override
    public Player getPlayer() {
        GridPosition startPos = new Position3D(0, 2f, 0);
        RelativeRotation startRot = new RelativeRotation(0, 0);
        CollisionBox playerBox = new CollisionBox(new Position3D(-0.5f, -1.999f, -0.5f), new Position3D(0.5f, 0.5f, 0.5f));
        
        return new Player(startPos, startRot, playerBox);
    }

    @Override
    public ArrayList<ShapeData> loadShapes() {
        ArrayList<ShapeData> shapes = new ArrayList<>();
        
        shapes.add(new ShapeData(new Position3D(0, 10, 0), new RelativeRotation(0, 0, 0), new File("src/main/resources/shapes/brick_cube.trym")));

        return shapes;
    }

    @Override
    public ArrayList<Entity> loadEntities() {
        ArrayList<Entity> entities = new ArrayList<>();
        
        SpinningCube spinningCube = new SpinningCube(new Position3D(0, 0, 5), new RelativeRotation(0, 0, 0), new RelativeRotation(0, 0, 1));
        entities.add(spinningCube.getEntity());

        return entities;
    }

    @Override
    public ArrayList<CollisionBox> loadCollisionBoxes() {
        ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();
        
        collisionBoxes.add(new CollisionBox(new Position3D(0, -1, 0), new Position3D(1, -2, 1)));

        return collisionBoxes;
    }

    @Override
    public ArrayList<CollisionBox> loadKillBoxes() {
        ArrayList<CollisionBox> killBoxes = new ArrayList<>();
        
        killBoxes.add(new CollisionBox(new Position3D(5, -1, 5), new Position3D(6, -2, 6)));

        return killBoxes;
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        
        enemies.add(new Zombie(new Position3D(0, 0, 0), new RelativeRotation(0, 0), 7));

        return enemies;
    }

    @Override
    public ArrayList<EnemySpawner> loadEnemySpawners() {
        ArrayList<EnemySpawner> enemySpawners = new ArrayList<>();
        
        Enemy initZombie = new Zombie(new Position3D(0, 0, 5), new RelativeRotation(0, 0), 7);

        enemySpawners.add(new EnemySpawner(100, 10, initZombie));

        return enemySpawners;
    }

    @Override
    public ArrayList<Door> loadDoors() {
        ArrayList<Door> doors = new ArrayList<>();
        
        doors.add(new Door(new Position3D(5, 0, 0), new RelativeRotation(0, 2), 5));

        return doors;
    }
}