package no.uib.inf101.sem2.game.settings;

public interface Settings {

    public void setScreenSize(int width, int height);

    public void setNoclip(boolean noclip);

    public void setWalkingSpeed(float speed);

    public void setSprintSpeed(float speed);

    public void setRenderDistance(float distance);
}
