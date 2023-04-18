package no.uib.inf101.sem2.game.model.levels;

import java.util.ArrayList;
import java.io.File;

import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemySpawner;
import no.uib.inf101.sem2.game.model.entities.enemies.Zombie;
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

        //First corridor
        shapes.add(new ShapeData(new Position3D(-14, -2, -2), new RelativeRotation(0, 0), new File("src/main/resources/shapes/hallway_L.trym")));
        shapes.add(new ShapeData(new Position3D(2, -2, -2), new RelativeRotation(0, (float) -Math.PI/2), new File("src/main/resources/shapes/hallway_cap.trym")));
        shapes.add(new ShapeData(new Position3D(-12, -2, 8), new RelativeRotation(0, 0), new File("src/main/resources/shapes/door_frame.trym")));

        //Parkour room
        shapes.add(new ShapeData(new Position3D(-20, -5, 9), new RelativeRotation(0, 0), new File("src/main/resources/shapes/large_room_A.trym")));

        //Second corridor
        shapes.add(new ShapeData(new Position3D(-12, -2, 30), new RelativeRotation(0, (float) Math.PI), new File("src/main/resources/shapes/door_frame.trym")));
        shapes.add(new ShapeData(new Position3D(-10, -2, 38), new RelativeRotation(0, (float) Math.PI), new File("src/main/resources/shapes/hallway_small_L.trym")));
        shapes.add(new ShapeData(new Position3D(-18, -2, 34), new RelativeRotation(0, 0), new File("src/main/resources/shapes/hallway_special.trym")));
        shapes.add(new ShapeData(new Position3D(-18, -2, 54), new RelativeRotation(0, (float) -Math.PI/2), new File("src/main/resources/shapes/hallway_small_L.trym")));
        shapes.add(new ShapeData(new Position3D(-10, -2, 52), new RelativeRotation(0, (float) -Math.PI/2), new File("src/main/resources/shapes/door_frame.trym")));
        
        //Boss room
        shapes.add(new ShapeData(new Position3D(-9, -2, 62), new RelativeRotation(0, (float) -Math.PI/2), new File("src/main/resources/shapes/large_room_B.trym")));

        return shapes;
    }

    @Override
    public ArrayList<CollisionBox> loadCollisionBoxes() {
        ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();

        //collisionBoxes.add(new CollisionBox(new Position3D(,,), new Position3D(,,)));

        //First corridor
        collisionBoxes.add(new CollisionBox(new Position3D(3,1.5f,-6), new Position3D(-15,-2,-2)));
        collisionBoxes.add(new CollisionBox(new Position3D(6,1.5f,3), new Position3D(2,-2,-3)));
        collisionBoxes.add(new CollisionBox(new Position3D(2,-2,-2), new Position3D(-14,-6,9)));
        collisionBoxes.add(new CollisionBox(new Position3D(2,1.5f,2), new Position3D(-10,-2,8)));
        collisionBoxes.add(new CollisionBox(new Position3D(-14,-2,-2), new Position3D(-18,1.5f,8)));
        collisionBoxes.add(new CollisionBox(new Position3D(2,1.5f,-2), new Position3D(-14,5.5f,9)));
        
        //Parkour room

        //Second corridor

        //Boss room

        return collisionBoxes;
    }

    @Override
    public ArrayList<Enemy> loadEnemies(){
        ArrayList<Enemy> enemies = new ArrayList<>();

        enemies.add(new Zombie(new Position3D(-12, -1.5f, 0), new RelativeRotation(0, (float) -Math.PI/2), 5));

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

        doors.add(new Door(new Position3D(-12, -2, 8), new RelativeRotation(0, 0), 5));
        doors.add(new Door(new Position3D(-12, -2, 30), new RelativeRotation(0, (float) Math.PI), 5));
        doors.add(new Door(new Position3D(-10, -2, 52), new RelativeRotation(0, (float) -Math.PI/2), 5));
        
        return doors;
    }

    @Override
    public ArrayList<ShapeData> loadEntities() {
        ArrayList<ShapeData> entities = new ArrayList<>();

        return entities;
    }
}
