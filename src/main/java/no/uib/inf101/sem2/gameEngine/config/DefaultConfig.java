package no.uib.inf101.sem2.gameEngine.config;


public class DefaultConfig implements Config {
    final float verticalFOV = 75;
    final int screenWidth = 1366;
    final int screenHeight = 768;
    final float nearPlane = 0.5f;
    final float farPlane = 100f;
    final float fps = 60f;
    final float cameraMoveSpeed = 0.04f;

    @Override
    public float verticalFOV() {
        return (float) Math.toRadians(this.verticalFOV);
    }
    @Override
    public int screenWidth() {
        return this.screenWidth;
    }
    @Override
    public int screenHeight() {
        return this.screenHeight;
    }
    @Override
    public float nearPlane() {
        return this.nearPlane;
    }
    @Override
    public float farPlane() {
        return this.farPlane;
    }

    @Override
    public float fps() {
        return this.fps;
    }

    @Override
    public float cameraMoveSpeed() {
        return this.cameraMoveSpeed;
    }

    
}
