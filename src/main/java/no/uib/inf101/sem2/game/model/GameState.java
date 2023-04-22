package no.uib.inf101.sem2.game.model;

/**
 * This enum represents the different states the game can have.
 */
public enum GameState {
    /**
     * The game is in the loading state, displaying the loading screen.
     */
    LOADING,

    /**
     * The game is in the main menu state, displaying the main menu.
     */
    MAIN_MENU,

    /**
     * The game is in the level menu state, displaying the level selection menu.
     */
    LEVEL_MENU,

    /**
     * The game is in the settings menu state, displaying the settings menu.
     */
    SETTINGS_MENU,

    /**
     * The game is in the active state, showing the gameplay screen with the game
     * running.
     */
    ACTIVE,

    /**
     * The game is in the paused state, showing the gameplay screen with the game
     * paused and a pause menu overlay.
     */
    PAUSED,

    /**
     * The game is in the settings game state, showing the gameplay screen with the
     * game paused and a settings menu overlay.
     */
    SETTINGS_GAME
}