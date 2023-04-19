package no.uib.inf101.sem2.game.controller;

import no.uib.inf101.sem2.game.model.GameState;

public interface ControllableGameModel {
    public GameState getGameState();

    public void setGameState(GameState state);

    public void shoot();

    public void updateGame();
}
