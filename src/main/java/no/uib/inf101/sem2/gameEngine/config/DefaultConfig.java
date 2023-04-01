package no.uib.inf101.sem2.gameEngine.config;

import java.util.HashMap;
import java.util.Map;

public class DefaultConfig implements Config {
    final float verticalFOV = 90;
    final int screenWidth = 640;
    final int screenHeight = 360;
    final float nearPlane = 0.5f;
    final float farPlane = 100f;
    @Override
    public float verticalFOV() {
        return this.verticalFOV;
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

    
}
