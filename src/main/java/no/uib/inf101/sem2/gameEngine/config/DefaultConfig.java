package no.uib.inf101.sem2.gameEngine.config;


public class DefaultConfig implements Config {
    final float verticalFOV = 75;
    /* int screenWidth = 1366;
    int screenHeight = 768; */
    protected int screenWidth = 854;
    protected int screenHeight = 480;
    protected final float nearPlane = 0.5f;
    protected float farPlane = 100f;
    protected final float fps = 45f;
    protected float cameraMoveSpeed = 0.09f;
    protected float cameraSprintSpeed = 0.17f;
    protected final int skyboxColor = 0xFFADD8E6;
    protected final float gravity = (float) (25 / Math.pow(fps, 2));
    protected final float jumpBurst = (float) Math.sqrt(2 * gravity * 1.25);
    protected boolean noclip = false;
    protected boolean displayFPS = false;

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

    @Override
    public float cameraSprintSpeed() {
        return this.cameraSprintSpeed;
    }

    @Override
    public int skyboxColor(){
        return this.skyboxColor;
    }

    @Override
    public float gravityAcceleration() {
        return this.gravity;
    }

    @Override
    public float jumpBurst() {
        return this.jumpBurst;
    }

    @Override
    public boolean noclip() {
        return this.noclip;
    }

    @Override
    public boolean displayFPS(){
        return this.displayFPS;
    }

    
}
