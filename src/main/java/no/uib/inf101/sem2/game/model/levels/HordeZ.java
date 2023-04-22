package no.uib.inf101.sem2.game.model.levels;

import java.io.File;
import java.util.ArrayList;

import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.Player;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemySpawner;
import no.uib.inf101.sem2.game.model.entities.enemies.Zombie;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public class HordeZ implements Level {
    @Override
    public String getLevelName() {
        return "HordeZ";
    }

    @Override
    public String getLevelMusic(){
        return "ravenholm_reprise";
    }

    @Override
    public Player getPlayer(){
        GridPosition startPos = new Position3D(0, 2f, 0);
        RelativeRotation startRot = new RelativeRotation(0, 0);
        CollisionBox playerBox = new CollisionBox(new Position3D(-0.5f, -1.999f, -0.5f), new Position3D(0.5f, 0.5f, 0.5f));
        
        return new Player(startPos, startRot, playerBox);
    }

    @Override
    public ArrayList<ShapeData> loadShapes() {
        ArrayList<ShapeData> shapes = new ArrayList<>();
        
        shapes.add(new ShapeData(new Position3D(-20, 0, -20), new RelativeRotation(0, 0), new File("src/main/resources/shapes/octagonal_arena.trym")));
        
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

        collisionBoxes.add(new CollisionBox(new Position3D(-20, 0, -20), new Position3D(20, -4, 20)));

        collisionBoxes.add(new CollisionBox(new Position3D(-20, 0, -20), new Position3D(20, 8, -24)));
        collisionBoxes.add(new CollisionBox(new Position3D(20, 0, -20), new Position3D(24, 8, 20)));
        collisionBoxes.add(new CollisionBox(new Position3D(20, 0, 20), new Position3D(-20, 8, 24)));
        collisionBoxes.add(new CollisionBox(new Position3D(-20, 0, 20), new Position3D(-24, 8, -20)));

        collisionBoxes.add(new CollisionBox(new Position3D(-20, 0, -20), new Position3D(-14.142f, 8, -14.142f)));
        collisionBoxes.add(new CollisionBox(new Position3D(20, 0, -20), new Position3D(14.142f, 8, -14.142f)));
        collisionBoxes.add(new CollisionBox(new Position3D(20, 0, 20), new Position3D(14.142f, 8, 14.142f)));
        collisionBoxes.add(new CollisionBox(new Position3D(-20, 0, 20), new Position3D(-14.142f, 8, 14.142f)));

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

        Zombie initZombie1 = new Zombie(new Position3D(10, 0.001f, 10), new RelativeRotation(0, (float) -Math.PI*3/4), 200);
        Zombie initZombie2 = new Zombie(new Position3D(10, 0.001f, -10), new RelativeRotation(0, (float) -Math.PI/4), 200);
        Zombie initZombie3 = new Zombie(new Position3D(-10, 0.001f, 10), new RelativeRotation(0, (float) Math.PI*3/4), 200);
        Zombie initZombie4 = new Zombie(new Position3D(-10, 0.001f, -10), new RelativeRotation(0, (float) -Math.PI*4), 200);
        
        enemySpawners.add(new EnemySpawner(100, -1, initZombie1));
        enemySpawners.add(new EnemySpawner(100, -1, initZombie2));
        enemySpawners.add(new EnemySpawner(100, -1, initZombie3));
        enemySpawners.add(new EnemySpawner(100, -1, initZombie4));

        return enemySpawners;
    }

    @Override
    public ArrayList<Door> loadDoors() {
        ArrayList<Door> doors = new ArrayList<>();

        return doors;
    }
}

