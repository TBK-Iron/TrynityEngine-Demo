package no.uib.inf101.sem2.gameEngine.config;


public class DefaultConfig implements Config {
    final float verticalFOV = 90;
    final int screenWidth = 1280;
    final int screenHeight = 720;
    final float nearPlane = 0.5f;
    final float farPlane = 100f;
    final float fps = 30f;

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

    
}
