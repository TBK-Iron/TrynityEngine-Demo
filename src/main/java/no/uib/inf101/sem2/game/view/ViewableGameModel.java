package no.uib.inf101.sem2.game.view;

import no.uib.inf101.sem2.game.model.GameState;

/**
 * Interface for accessing view-related aspects of a GameModel.
 * Provides methods for getting the current game state and the player's health percentage.
 */
public interface ViewableGameModel {

    /**
     * Returns the current game state.
     *
     * @return The current GameState.
     */
    public GameState getGameState();

    /**
     * Returns the player's health percentage.
     *
     * @return The player's health as a percentage of their maximum health.
     */
    public float getPlayerHealthPercent();

    /**
     * Returns a boolean that determines if the player just shot the gun
     * 
     * @return true if the player just shot the gun, false otherwise
     */
    public boolean getGunState();
}
