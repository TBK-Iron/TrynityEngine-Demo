package no.uib.inf101.sem2.game.model.levels;

import java.util.ArrayList;

import no.uib.inf101.sem2.game.model.entities.Door;
import no.uib.inf101.sem2.game.model.entities.enemies.Enemy;
import no.uib.inf101.sem2.game.model.entities.enemies.EnemySpawner;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public interface Level {

    public RelativeRotation startRotation();

    public GridPosition startPosition();

    public ArrayList<ShapeData> loadShapes();

    public ArrayList<ShapeData> loadEntities();

    public ArrayList<CollisionBox> loadCollisionBoxes();

    public ArrayList<Enemy> loadEnemies();

    public ArrayList<EnemySpawner> loadEnemySpawners();

    public ArrayList<Door> loadDoors();
}
