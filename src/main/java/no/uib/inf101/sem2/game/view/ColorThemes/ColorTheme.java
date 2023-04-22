package no.uib.inf101.sem2.game.view.ColorThemes;

import java.awt.Color;

/**
 * Interface for defining color themes used in the game.
 * Provides methods for getting colors for various UI elements and game components.
 */
public interface ColorTheme {
    /**
     * Returns the background color for the loading screen.
     *
     * @return The loading screen background color.
     */
    public Color getLoadingScreenBackgroundColor();

    /**
     * Returns the color for text elements.
     *
     * @return The text color.
     */
    public Color getTextColor();

    /**
     * Returns the hue for the pause menu.
     *
     * @return The pause menu hue color.
     */
    public Color getPauseMenuHue();

    /**
     * Returns the color for buttons.
     *
     * @return The button color.
     */
    public Color getButtonColor();

    /**
     * Returns the border color for buttons.
     *
     * @return The button border color.
     */
    public Color getButtonBorderColor();

    /**
     * Returns the color for the crosshair.
     *
     * @return The crosshair color.
     */
    public Color getCrosshairColor();

    /**
     * Returns the color for the health indicator.
     *
     * @return The health indicator color.
     */
    public Color getHealthColor();

    /**
     * Returns the background color for the health indicator.
     *
     * @return The health indicator background color.
     */
    public Color getHealthBackgroundColor();

    /**
     * Returns the border color for the health indicator.
     *
     * @return The health indicator border color.
     */
    public Color getHealthBorderColor();
}