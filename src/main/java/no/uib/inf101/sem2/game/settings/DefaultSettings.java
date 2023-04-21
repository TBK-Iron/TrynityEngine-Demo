package no.uib.inf101.sem2.game.settings;

import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;

public class DefaultSettings extends DefaultConfig implements Settings {
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
