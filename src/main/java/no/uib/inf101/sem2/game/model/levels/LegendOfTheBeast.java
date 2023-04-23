package no.uib.inf101.sem2.game.model.levels;

import java.util.ArrayList;
import java.io.File;

import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.Player;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemySpawner;
import no.uib.inf101.sem2.game.model.entities.enemies.TheBeast;
import no.uib.inf101.sem2.game.model.entities.enemies.Zombie;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public class LegendOfTheBeast implements Level{

    @Override
    public String getLevelName() {
        return "The Legend of The Beast";
    }

    @Override
    public String getLevelMusic(){
        return "triage_at_dawn";
    }

    @Override
    public Player getPlayer(){
        GridPosition startPos = new Position3D(0, 0, 0);
        RelativeRotation startRot = new RelativeRotation(0, -(float) Math.PI/2);
        CollisionBox playerBox = new CollisionBox(new Position3D(-0.5f, 0.5f, -0.5f), new Position3D(0.5f, -1.999f, 0.5f));
        
        return new Player(startPos, startRot, playerBox);
    }

    @Override
    public ArrayList<ShapeData> loadShapes(){
        ArrayList<ShapeData> shapes = new ArrayList<>();

        //First corridor
        shapes.add(new ShapeData(new Position3D(-14, -2, -2), new RelativeRotation(0, 0), new File("src/main/resources/shapes/hallway_L.trym")));
        shapes.add(new ShapeData(new Position3D(2, -2, -2), new RelativeRotation(0, (float) Math.PI/2), new File("src/main/resources/shapes/hallway_cap.trym")));
        shapes.add(new ShapeData(new Position3D(-12, -2, 8), new RelativeRotation(0, 0), new File("src/main/resources/shapes/door_frame.trym")));

        //Parkour room
        shapes.add(new ShapeData(new Position3D(-20, -5, 9), new RelativeRotation(0, 0), new File("src/main/resources/shapes/large_room_A.trym")));
        shapes.add(new ShapeData(new Position3D(-12, -2, 9), new RelativeRotation(0, 0), new File("src/main/resources/shapes/stone_ledge.trym")));
        shapes.add(new ShapeData(new Position3D(-12, -2, 29), new RelativeRotation(0, (float) Math.PI), new File("src/main/resources/shapes/stone_ledge.trym")));

        shapes.add(new ShapeData(new Position3D(-6, -2.1f, 14), new RelativeRotation(0, 0), new File("src/main/resources/shapes/wallrun_block.trym")));
        shapes.add(new ShapeData(new Position3D(-8, -3.5f, 12.5f), new RelativeRotation(0, 0), new File("src/main/resources/shapes/metal_cube.trym")));
        shapes.add(new ShapeData(new Position3D(-8.5f, -3.5f, 20.5f), new RelativeRotation(0, 0), new File("src/main/resources/shapes/metal_cube.trym")));
        shapes.add(new ShapeData(new Position3D(-12, -3f, 18f), new RelativeRotation(0, 0), new File("src/main/resources/shapes/metal_cube.trym")));
        shapes.add(new ShapeData(new Position3D(-14f, -2.8f, 16f), new RelativeRotation(0, 0), new File("src/main/resources/shapes/metal_cube.trym")));
        
        shapes.add(new ShapeData(new Position3D(-12, -1.3f, 13), new RelativeRotation(0, (float) -Math.PI/2), new File("src/main/resources/shapes/wallrun_block.trym")));
        shapes.add(new ShapeData(new Position3D(-17f, -2.8f, 15f), new RelativeRotation(0, 0), new File("src/main/resources/shapes/metal_cube.trym")));
        shapes.add(new ShapeData(new Position3D(-19f, -2.4f, 18f), new RelativeRotation(0, 0), new File("src/main/resources/shapes/metal_cube.trym")));
        
        shapes.add(new ShapeData(new Position3D(-17, -2f, 20), new RelativeRotation(0, 0), new File("src/main/resources/shapes/wallrun_block.trym")));
        shapes.add(new ShapeData(new Position3D(-19.2f, -3.5f, 26f), new RelativeRotation(0, 0), new File("src/main/resources/shapes/metal_cube.trym")));
        //Second corridor
        shapes.add(new ShapeData(new Position3D(-12, -2, 30), new RelativeRotation(0, (float) Math.PI), new File("src/main/resources/shapes/door_frame.trym")));
        shapes.add(new ShapeData(new Position3D(-10, -2, 38), new RelativeRotation(0, (float) Math.PI), new File("src/main/resources/shapes/hallway_small_L.trym")));
        shapes.add(new ShapeData(new Position3D(-18, -2, 34), new RelativeRotation(0, 0), new File("src/main/resources/shapes/hallway_special.trym")));
        shapes.add(new ShapeData(new Position3D(-18, -2, 54), new RelativeRotation(0, (float) Math.PI/2), new File("src/main/resources/shapes/hallway_small_L.trym")));
        shapes.add(new ShapeData(new Position3D(-10, -2, 52), new RelativeRotation(0, (float) Math.PI/2), new File("src/main/resources/shapes/door_frame.trym")));
        
        //Boss room
        shapes.add(new ShapeData(new Position3D(-9, -2, 62), new RelativeRotation(0, (float) Math.PI/2), new File("src/main/resources/shapes/large_room_B.trym")));

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
            //Walls 
        collisionBoxes.add(new CollisionBox(new Position3D(-20, -5, 9), new Position3D(-24, 7, 29)));
        collisionBoxes.add(new CollisionBox(new Position3D(-4, -5, 9), new Position3D(0, 7, 29)));
        collisionBoxes.add(new CollisionBox(new Position3D(-20, -5, 9), new Position3D(-13, 7, 8)));
        collisionBoxes.add(new CollisionBox(new Position3D(-11, -5, 9), new Position3D(-4, 7, 8)));
        collisionBoxes.add(new CollisionBox(new Position3D(-13, 0.7f, 9), new Position3D(-11, 7, 8)));
        collisionBoxes.add(new CollisionBox(new Position3D(-20, -5, 29), new Position3D(-13, 7, 30)));
        collisionBoxes.add(new CollisionBox(new Position3D(-11, -5, 29), new Position3D(-4, 7, 30)));
        collisionBoxes.add(new CollisionBox(new Position3D(-13, 0.7f, 29), new Position3D(-11, 7, 30)));
            
            //Ceiling
        collisionBoxes.add(new CollisionBox(new Position3D(-4, 7, 9), new Position3D(-20, 11, 29)));
        
            //Ledges
        collisionBoxes.add(new CollisionBox(new Position3D(-7, -2, 8), new Position3D(-17,-5,12)));
        collisionBoxes.add(new CollisionBox(new Position3D(-7, -2, 26), new Position3D(-17,-5,30)));

            //Wallrun blocks
        collisionBoxes.add(new CollisionBox(new Position3D(-6, -2.1f, 14), new Position3D(-4.5f,7,20)));
        collisionBoxes.add(new CollisionBox(new Position3D(-12, -1.3f, 13), new Position3D(-18f,7,14.5f)));
        collisionBoxes.add(new CollisionBox(new Position3D(-17, -2, 20), new Position3D(-15.5f,7,26f)));
            
            //Metal cubes
        collisionBoxes.add(new CollisionBox(new Position3D(-8, -3.5f, 12.5f), new Position3D(-6.5f,-2,14)));
        collisionBoxes.add(new CollisionBox(new Position3D(-8.5f, -3.5f, 20.5f), new Position3D(-7,-2,22)));
        collisionBoxes.add(new CollisionBox(new Position3D(-12, -3f, 18f), new Position3D(-10.5f,-1.5f,19.5f)));
        collisionBoxes.add(new CollisionBox(new Position3D(-14f, -2.8f, 16f), new Position3D(-12.5f,-1.3f,17.5f)));
        collisionBoxes.add(new CollisionBox(new Position3D(-17f, -2.8f, 15f), new Position3D(-15.5f,-1.3f,16.5f)));
        collisionBoxes.add(new CollisionBox(new Position3D(-19f, -2.4f, 18f), new Position3D(-17.5f,-0.9f,19.5f)));
        collisionBoxes.add(new CollisionBox(new Position3D(-19.2f, -3.5f, 26f), new Position3D(-17.7f,-2,27.5f)));
        
        //Boss room and second corridor floor
        collisionBoxes.add(new CollisionBox(new Position3D(-20, -2, 30), new Position3D(19, -6, 62)));
        
        //Second corridor
            //Walls
        collisionBoxes.add(new CollisionBox(new Position3D(-10,-2,30), new Position3D(-6,1.5f,38)));
        collisionBoxes.add(new CollisionBox(new Position3D(-10,-2,38), new Position3D(-14,1.5f,50)));
        collisionBoxes.add(new CollisionBox(new Position3D(-10,-2,54), new Position3D(-18,1.5f,58)));
        collisionBoxes.add(new CollisionBox(new Position3D(-14,-2,30), new Position3D(-18,1.5f,34)));
        collisionBoxes.add(new CollisionBox(new Position3D(-18,-2,34), new Position3D(-22,1.5f,42)));
        collisionBoxes.add(new CollisionBox(new Position3D(-20,-2,42), new Position3D(-24,1.5f,46)));
        collisionBoxes.add(new CollisionBox(new Position3D(-18,-2,46), new Position3D(-22,1.5f,54)));
            
            //Ceiling
        collisionBoxes.add(new CollisionBox(new Position3D(-10,1.5f,30), new Position3D(-20,5.5f,54)));

        //Boss room
            //Walls
        collisionBoxes.add(new CollisionBox(new Position3D(-9,-2,42), new Position3D(19,14,38)));
        collisionBoxes.add(new CollisionBox(new Position3D(19,-2,42), new Position3D(23,14,62)));
        collisionBoxes.add(new CollisionBox(new Position3D(19,-2,62), new Position3D(-9,14,66)));

        collisionBoxes.add(new CollisionBox(new Position3D(-9,-2,42), new Position3D(-10,14,51)));
        collisionBoxes.add(new CollisionBox(new Position3D(-9,0.7f,51), new Position3D(-10,14,53)));
        collisionBoxes.add(new CollisionBox(new Position3D(-9,-2,53), new Position3D(-10,14,62)));
        

        return collisionBoxes;
    }

    @Override
    public ArrayList<CollisionBox> loadKillBoxes() {
        ArrayList<CollisionBox> killBoxes = new ArrayList<>();

        killBoxes.add(new CollisionBox(new Position3D(-20,-5,9), new Position3D(-4,-9,29)));

        return killBoxes;
    }

    @Override
    public ArrayList<Enemy> loadEnemies(){
        ArrayList<Enemy> enemies = new ArrayList<>();

        enemies.add(new Zombie(new Position3D(-11, -1.9989f, 4), new RelativeRotation(0, (float) Math.PI), 7));
        enemies.add(new Zombie(new Position3D(-13, -1.9989f, 4), new RelativeRotation(0, (float) Math.PI), 7));
        
        enemies.add(new Zombie(new Position3D(-12, -1.9989f, 36), new RelativeRotation(0, (float) Math.PI), 7));
        enemies.add(new Zombie(new Position3D(-19.4f, -1.9989f, 42.6f), new RelativeRotation(0, (float) Math.PI/2), 7));

        enemies.add(new Zombie(new Position3D(-15, -1.9989f, 52), new RelativeRotation(0, (float) Math.PI), 7));
        enemies.add(new Zombie(new Position3D(-17, -1.9989f, 52), new RelativeRotation(0, (float) Math.PI), 7));

        enemies.add(new TheBeast(new Position3D(8, -1.9989f, 52), new RelativeRotation(0, (float) -Math.PI/2), 15));
        
        
      
        return enemies;
    }

    @Override
    public ArrayList<EnemySpawner> loadEnemySpawners(){
        ArrayList<EnemySpawner> enemySpawners = new ArrayList<>();

        Enemy zombieInit1 = new Zombie(new Position3D(-4, -1.9989f, 44), new RelativeRotation(0, (float) (Math.PI*1/4)), 9);
        enemySpawners.add(new EnemySpawner(120, 3, zombieInit1));

        Enemy zombieInit2 = new Zombie(new Position3D(-4, -1.9989f, 60), new RelativeRotation(0, (float) (Math.PI*3/4)), 9);
        enemySpawners.add(new EnemySpawner(120, 3, zombieInit2));

        return enemySpawners;
    }

    @Override
    public ArrayList<Door> loadDoors(){
        ArrayList<Door> doors = new ArrayList<>();

        doors.add(new Door(new Position3D(-12, -2, 8), new RelativeRotation(0, 0), 5));
        doors.add(new Door(new Position3D(-12, -2, 30), new RelativeRotation(0, (float) -Math.PI), 5));
        doors.add(new Door(new Position3D(-10, -2, 52), new RelativeRotation(0, (float) Math.PI/2), 5));
        
        return doors;
    }

    @Override
    public ArrayList<Entity> loadEntities() {
        ArrayList<Entity> entities = new ArrayList<>();

        return entities;
    }
}
