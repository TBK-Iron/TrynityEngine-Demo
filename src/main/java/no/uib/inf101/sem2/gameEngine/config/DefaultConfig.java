package no.uib.inf101.sem2.gameEngine.config;


public class DefaultConfig implements Config {
    final float verticalFOV = 75;
    /* int screenWidth = 1366;
    int screenHeight = 768; */
    private int screenWidth = 854;
    private int screenHeight = 480;
    private final float nearPlane = 0.5f;
    private final float farPlane = 100f;
    private final float fps = 45f;
    private final float cameraMoveSpeed = 0.09f;
    private final float cameraSprintSpeed = 0.17f;
    private final int skyboxColor = 0xFFADD8E6;
    private final float gravity = (float) (25 / Math.pow(fps, 2));
    private final float jumpBurst = (float) Math.sqrt(2 * gravity * 1.25);
    private final boolean noclip = false;

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
    public void resizeFrame(int width, int height) {
        System.out.println("Resizing frame to: " + width + "x" + height);
        this.screenWidth = width;
        this.screenHeight = height;
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
    
}
