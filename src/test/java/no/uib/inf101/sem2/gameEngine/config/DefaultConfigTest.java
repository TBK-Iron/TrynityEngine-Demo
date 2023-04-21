package no.uib.inf101.sem2.gameEngine.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DefaultConfigTest {
    private DefaultConfig config;

    @BeforeEach
    public void setUp() {
        config = new DefaultConfig();
    }

    @Test
    public void testVerticalFOV() {
        assertEquals((float) Math.toRadians(75f), config.verticalFOV());
    }

    @Test
    public void testScreenWidth() {
        assertEquals(854, config.screenWidth());
    }

    @Test
    public void testScreenHeight() {
        assertEquals(480, config.screenHeight());
    }

    @Test
    public void testNearPlane() {
        assertEquals(0.5f, config.nearPlane());
    }

    @Test
    public void testFarPlane() {
        assertEquals(100f, config.farPlane());
    }

    @Test
    public void testFps() {
        assertEquals(45f, config.fps());
    }

    @Test
    public void testCameraMoveSpeed() {
        assertEquals(0.09f, config.cameraMoveSpeed());
    }

    @Test
    public void testSkyboxColor() {
        assertEquals(0xFFADD8E6, config.skyboxColor());
    }

    @Test
    public void testGravityAcceleration() {
        assertEquals(0.012345679f, config.gravityAcceleration());
    }

    @Test
    public void testJumpBurst() {
        assertEquals(0.1756821f, config.jumpBurst());
    }

    @Test
    public void testNoclip() {
        assertFalse(config.noclip());
    }
}
