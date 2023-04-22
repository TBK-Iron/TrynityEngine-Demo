package no.uib.inf101.sem2.game.view.ColorThemes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultColorThemeTest {

    private DefaultColorTheme colorTheme;

    @BeforeEach
    public void setUp() {
        colorTheme = new DefaultColorTheme();
    }

    @Test
    public void testGetLoadingScreenBackgroundColor() {
        Color expectedColor = new Color(224, 224, 224);
        assertEquals(expectedColor, colorTheme.getLoadingScreenBackgroundColor());
    }

    @Test
    public void testGetTextColor() {
        Color expectedColor = new Color(50, 50, 60);
        assertEquals(expectedColor, colorTheme.getTextColor());
    }

    @Test
    public void testGetPauseMenuHue() {
        Color expectedColor = new Color(128, 128, 128, 128);
        assertEquals(expectedColor, colorTheme.getPauseMenuHue());
    }

    @Test
    public void testGetButtonColor() {
        Color expectedColor = new Color(91, 111, 116);
        assertEquals(expectedColor, colorTheme.getButtonColor());
    }

    @Test
    public void testGetButtonBorderColor() {
        Color expectedColor = new Color(50, 50, 50);
        assertEquals(expectedColor, colorTheme.getButtonBorderColor());
    }

    @Test
    public void testGetCrosshairColor() {
        Color expectedColor = new Color(10, 10, 10);
        assertEquals(expectedColor, colorTheme.getCrosshairColor());
    }

    @Test
    public void testGetHealthColor() {
        Color expectedColor = new Color(109, 227, 56);
        assertEquals(expectedColor, colorTheme.getHealthColor());
    }

    @Test
    public void testGetHealthBackgroundColor() {
        Color expectedColor = new Color(223, 49, 20, 100);
        assertEquals(expectedColor, colorTheme.getHealthBackgroundColor());
    }

    @Test
    public void testGetHealthBorderColor() {
        Color expectedColor = new Color(0, 0, 0);
        assertEquals(expectedColor, colorTheme.getHealthBorderColor());
    }
}
