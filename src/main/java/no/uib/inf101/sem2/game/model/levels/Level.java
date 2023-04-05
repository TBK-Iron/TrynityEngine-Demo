package no.uib.inf101.sem2.game.model.levels;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;

public interface Level {
    public ArrayList<ShapeData> loadShapes();

    public ArrayList<ShapeData> loadEntities();

    public ArrayList<ShapeData> loadEntityCollision();

    public ArrayList<CollisionBox> loadCollisionBoxes();
}
