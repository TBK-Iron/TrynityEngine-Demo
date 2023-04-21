package no.uib.inf101.sem2.game.controller;

import no.uib.inf101.sem2.game.model.GameState;
import no.uib.inf101.sem2.game.model.levels.Level;

public interface ControllableGameModel {
    public void loadMap(Level map);

    public GameState getGameState();

    public void setGameState(GameState state);

    public void shoot();

    public void updateGame();
}
