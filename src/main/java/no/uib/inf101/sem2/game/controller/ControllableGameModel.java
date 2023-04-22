package no.uib.inf101.sem2.game.controller;

import no.uib.inf101.sem2.game.model.GameState;
import no.uib.inf101.sem2.game.model.levels.Level;

/**
 * Interface for controlling a GameModel.
 * Provides methods for loading maps, controlling game states, shooting, updating the game, and stopping music.
 */
public interface ControllableGameModel {
    /**
     * Loads the game with the specified map.
     *
     * @param map The Level object containing the map data.
     */
    public void loadMap(Level map);

    /**
     * Returns the current game state.
     *
     * @return The current GameState.
     */
    public GameState getGameState();

    /**
     * Sets the current game state.
     *
     * @param state The new GameState.
     */
    public void setGameState(GameState state);

    /**
     * Shoots at the closest enemy within range, considering obstructions.
     */
    public void shoot();

    /**
     * Updates the game state, handling door and enemy interactions, player damage, and enemy spawning.
     */
    public void updateGame();

    /**
     * Stops the music that is playing on the currently active map.
     */
    public void stopMusic();
}

