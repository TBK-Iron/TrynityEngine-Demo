package no.uib.inf101.sem2.game.view;

import no.uib.inf101.sem2.game.model.GameState;

public interface ViewableGameModel {

    public GameState getGameState();

    public float getPlayerHealthPercent();
}
