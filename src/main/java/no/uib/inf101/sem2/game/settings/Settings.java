package no.uib.inf101.sem2.game.settings;

/**
 * Interface for managing game settings.
 * Provides methods for setting various game settings such as screen size, noclip, walking speed, sprint speed, and render distance.
 */
public interface Settings {
    /**
     * Sets the screen size.
     *
     * @param width The desired screen width.
     * @param height The desired screen height.
     */
    public void setScreenSize(int width, int height);

    /**
     * Enables or disables noclip mode.
     *
     * @param noclip A boolean value indicating whether noclip mode should be enabled or disabled.
     */
    public void setNoclip(boolean noclip);

    /**
     * Sets the walking speed.
     *
     * @param speed The desired walking speed.
     */
    public void setWalkingSpeed(float speed);

    /**
     * Sets the sprint speed.
     *
     * @param speed The desired sprint speed.
     */
    public void setSprintSpeed(float speed);

    /**
     * Sets the render distance.
     *
     * @param distance The desired render distance.
     */
    public void setRenderDistance(float distance);
}
