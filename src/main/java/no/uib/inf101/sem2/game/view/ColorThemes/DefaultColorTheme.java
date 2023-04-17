package no.uib.inf101.sem2.game.view.ColorThemes;

import java.awt.Color;

public class DefaultColorTheme implements ColorTheme {

    @Override
    public Color getTextColor() {
        return new Color(50, 50, 60);
    }

    @Override
    public Color getPauseMenuHue() {
        return new Color(128, 128, 128, 128);
    }

    @Override
    public Color getButtonColor() {
        return new Color(91, 111, 116);
    }

    @Override
    public Color getButtonBorderColor(){
        return new Color(50, 50, 50);
    }
    
}
