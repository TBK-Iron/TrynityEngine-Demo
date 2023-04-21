package no.uib.inf101.sem2.game.model.levels;

import java.util.ArrayList;

import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.Player;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemySpawner;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;

public interface Level {

    public String getLevelName();

    public Player getPlayer();

    public ArrayList<ShapeData> loadShapes();

    public ArrayList<ShapeData> loadEntities();

    public ArrayList<CollisionBox> loadCollisionBoxes();

    public ArrayList<CollisionBox> loadKillBoxes();

    public ArrayList<Enemy> loadEnemies();

    public ArrayList<EnemySpawner> loadEnemySpawners();

    public ArrayList<Door> loadDoors();
}
