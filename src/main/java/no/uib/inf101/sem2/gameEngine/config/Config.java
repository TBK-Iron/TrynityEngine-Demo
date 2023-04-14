package no.uib.inf101.sem2.gameEngine.config;

public interface Config {

    float verticalFOV();

    int screenWidth();

    int screenHeight();

    float nearPlane();

    float farPlane();

    float fps();

    float cameraMoveSpeed();

    void resizeFrame(int width, int height);

    int skyboxColor();

    float gravityAcceleration();

    float jumpBurst();

    boolean noclip();
}
