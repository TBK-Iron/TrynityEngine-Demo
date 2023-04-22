package no.uib.inf101.sem2.game.model.levels;

import java.io.File;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.Player;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemySpawner;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

public class TestLevel1 implements Level {

    @Override
    public String getLevelName() {
        return "Test Level 1";
    }

    @Override
    public String getLevelMusic(){
        return null;
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
        shapes.add(new ShapeData(new Position3D(0, 0, 0), new RelativeRotation(0, 0), new File("src/main/resources/shapes/tunnel.trym")));
        shapes.add(new ShapeData(new Position3D(0, 0, 2.5f), new RelativeRotation((float) (Math.PI/10), (float) (Math.PI/4 - 0.2)), new File("src/main/resources/shapes/brick_cube.trym")));
        //shapes.add(new ShapeData(new Position3D(0, 0, 0), new RelativeRotation(0, 0), new File("src/main/resources/shapes/triangle.trym")));
        return shapes;
    }

    @Override
    public ArrayList<Entity> loadEntities() {
        ArrayList<Entity> entities = new ArrayList<>();

        return entities;
    }

    @Override
    public ArrayList<CollisionBox> loadCollisionBoxes() {
        ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();

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
