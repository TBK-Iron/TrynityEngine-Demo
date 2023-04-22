package no.uib.inf101.sem2.game.settings;

import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;

public class DefaultSettings extends DefaultConfig implements Settings {

    public static final float renderDistanceSHORT = 15;
    public static final float renderDistanceMEDIUM = 50;
    public static final float renderDistanceFAR = 120;

    public static final float walkingSpeedSLOW = 0.06f;
    public static final float walkingSpeedMEDIUM = 0.09f;
    public static final float walkingSpeedFAST = 0.12f;

    public static final float sprintSpeedSLOW = 0.12f;
    public static final float sprintSpeedMEDIUM = 0.17f;
    public static final float sprintSpeedFAST = 0.23f;

    public DefaultSettings(){
        super();
    }

    @Override
    public void setScreenSize(int width, int height){
        this.screenWidth = width;
        this.screenHeight = height;
    }

    @Override
    public void setNoclip(boolean noclip){
        this.noclip = noclip;
    }

    @Override
    public void setWalkingSpeed(float speed){
        this.cameraMoveSpeed = speed;
    }

    @Override
    public void setSprintSpeed(float speed){
        this.cameraSprintSpeed = speed;
    }

    @Override
    public void setRenderDistance(float distance){
        this.farPlane = distance;
    }
}
