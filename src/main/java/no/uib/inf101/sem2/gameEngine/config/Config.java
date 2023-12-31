package no.uib.inf101.sem2.gameEngine.config;

/**
 * Config interface represents a configuration for the game engine.
 * It provides methods to access various configuration settings.
 */
public interface Config {

    /**
     * @return The vertical field of view (FOV) of the camera. (in radians)
     */
    float verticalFOV();

    /**
     * @return The width of the screen in pixels.
     */
    int screenWidth();

    /**
     * @return The height of the screen in pixels.
     */
    int screenHeight();

    /**
     * @return The near clipping plane distance of the camera.
     */
    float nearPlane();

    /**
     * @return The far clipping plane distance of the camera.
     */
    float farPlane();

    /**
     * @return The target frames per second (FPS) of the game engine.
     */
    float fps();

    /**
     * @return The speed at which the camera moves. (per frame)
     */
    float cameraMoveSpeed();

    /**
     * 
     * @return The speed at which the camera moves when sprinting. (per frame)
     */
    public float cameraSprintSpeed();

    /**
     * @return The color of the skybox in ARGB format.
     */
    int skyboxColor();

    /**
     * @return The acceleration due to gravity in the game world. (per frame)
     */
    float gravityAcceleration();

    /**
     * @return The burst of upward movement applied when the player jumps.
     */
    float jumpBurst();

    /**
     * @return True if noclip mode is enabled, false otherwise.
     */
    boolean noclip();

    /**
     * Gets the boolean value indicating whether or not to display the FPS counter.
     * 
     * @return A boolean value indicating whether or not to display the FPS counter.
     */
    public boolean displayFPS();
}
