package no.uib.inf101.sem2.game.model.levels;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;

public interface Level {
    public ArrayList<ShapeData> loadLevel();
}
