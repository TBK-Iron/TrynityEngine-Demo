package no.uib.inf101.sem2.game.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JPanel;

import no.uib.inf101.sem2.game.controller.Button;
import no.uib.inf101.sem2.game.controller.ButtonsHandler;
import no.uib.inf101.sem2.game.model.GameState;
import no.uib.inf101.sem2.game.model.resourceLoaders.TextureLoader;
import no.uib.inf101.sem2.game.view.ColorThemes.ColorTheme;
import no.uib.inf101.sem2.game.view.ColorThemes.DefaultColorTheme;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.view.SceneMaker;

/**
 * This class represents the game view, handling various game states and
 * rendering the game interface.
 */
public class GameView extends JPanel {

    private final SceneMaker engineView;
    private final ViewableGameModel model;
    private BufferedImage sceneImage;
    private final Map<String, BufferedImage> images;
    private Config config;
    private final ColorTheme CTheme;

    
    private ButtonsHandler buttons;

    /**
     * Creates a new GameView with the specified game model, buttons handler, configuration, scene maker, and texture loader.
     *
     * @param model       The game model to be viewed.
     * @param buttons     The buttons handler for the game view.
     * @param config      The configuration settings for the game.
     * @param engineView  The scene maker for rendering game scenes.
     * @param images      The texture loader for loading game images.
     */
    public GameView(ViewableGameModel model, ButtonsHandler buttons, Config config, SceneMaker engineView, Map<String, BufferedImage> images) {
        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.model = model;
        this.engineView = engineView;
        this.config = config;
        this.images = images;
        this.buttons = buttons;
        this.CTheme = new DefaultColorTheme();
    }

    /**
     * Returns the buttons handler for this game view.
     *
     * @return The buttons handler.
     */
    public ButtonsHandler getButtons(){
        return this.buttons;
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(this.model.getGameState() == GameState.LOADING){
            drawLoadingScreen(g2);
        } else if(this.model.getGameState() == GameState.MAIN_MENU){
            drawMainMenu(g2);
        } else if(this.model.getGameState() == GameState.LEVEL_MENU){
            drawLevelMenu(g2);
        } else if (this.model.getGameState() == GameState.SETTINGS_MENU){
            drawSettingsMenu(g2);
        } else if(this.model.getGameState() == GameState.ACTIVE){
            drawActiveGame(g2);
        } else if(this.model.getGameState() == GameState.PAUSED){
            drawPausedGame(g2);
        } else if(this.model.getGameState() == GameState.SETTINGS_GAME){
            drawSettingsGame(g2);
        }
    }

    // Helper methods for drawing different game states

    private void drawLoadingScreen(Graphics2D g2){
        Rectangle2D rect = new Rectangle2D.Double(0, 0, this.config.screenWidth(), this.config.screenHeight());
        g2.setColor(this.CTheme.getLoadingScreenBackgroundColor());
        g2.fill(rect);
        double x = this.config.screenWidth() / 2;
        double y = this.config.screenHeight() / 2;
        Inf101Graphics.drawCenteredImage(g2, this.images.get("trynity_logo"), x, y, 0.45);
    }

    private void drawMainMenu(Graphics2D g2){
        double x = this.config.screenWidth() / 2;
        double y = this.config.screenHeight() / 2;
        Inf101Graphics.drawCenteredImage(g2, this.images.get("menu_background"), x, y, 0.5);

        drawButtons(g2, this.buttons.getMainMenuButtons(), this.CTheme, this.config.screenWidth());
    }

    private void drawLevelMenu(Graphics2D g2){
        double x = this.config.screenWidth() / 2;
        double y = this.config.screenHeight() / 2;
        Inf101Graphics.drawCenteredImage(g2, this.images.get("menu_background"), x, y, 0.5);

        drawButtons(g2, this.buttons.getLevelMenuButtons(), this.CTheme, this.config.screenWidth());
    }

    private void drawSettingsMenu(Graphics2D g2){
        double x = this.config.screenWidth() / 2;
        double y = this.config.screenHeight() / 2;
        Inf101Graphics.drawCenteredImage(g2, this.images.get("menu_background"), x, y, 0.5);

        drawButtons(g2, this.buttons.getSettingsMenuButtons(), this.CTheme, this.config.screenWidth());
    }

    //TODO: add more GUI elements
    private void drawActiveGame(Graphics2D g2){
        long startTime = System.nanoTime();

        this.sceneImage = this.engineView.getNextSceneImage();
        g2.drawImage(this.sceneImage, 0, 0, null);

        drawCrosshair(g2, CTheme, config.screenWidth()/2, config.screenHeight()/2, 15);
        drawHealthBar(g2, this.model.getPlayerHealthPercent(), CTheme, config.screenHeight(), config.screenHeight()/20);
        drawHeldGun(g2, config.screenWidth(), config.screenHeight(), this.images, this.model.getGunState());

        long endTime = System.nanoTime();
        long fps = 1000000000/(endTime - startTime);
        if(this.config.displayFPS()){
            drawFPS(g2, CTheme, fps);
        }


        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image transparentImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Cursor transparentCursor = toolkit.createCustomCursor(transparentImage, new Point(0, 0), "transparentCursor");
        this.setCursor(transparentCursor);
    }

    private static void drawCrosshair(Graphics2D g2, ColorTheme CTheme, int x, int y, int size){
        g2.setColor(CTheme.getCrosshairColor());
        g2.setStroke(new BasicStroke(size/6));
        g2.drawLine(x - size, y, x + size, y);
        g2.drawLine(x, y - size, x, y + size);
    }

    private static void drawHealthBar(Graphics2D g2, float healthPercent, ColorTheme CTheme, int screenHeight, int size){
        
        Rectangle2D healthBarBackground = new Rectangle2D.Double(screenHeight/20, screenHeight - size - screenHeight/20, size*8, size);
        Rectangle2D healthBar = new Rectangle2D.Double(screenHeight/20, screenHeight - size - screenHeight/20, size*8*healthPercent, size);

        g2.setColor(CTheme.getHealthBackgroundColor());
        g2.fill(healthBarBackground);

        g2.setColor(CTheme.getHealthColor());
        g2.fill(healthBar);

        g2.setColor(CTheme.getHealthBorderColor());
        g2.draw(healthBarBackground);
    }

    private static void drawFPS(Graphics2D g2, ColorTheme CTheme, long fps){
        g2.setColor(CTheme.getTextColor());
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("FPS: " + fps, 10, 20);
    }

    private static void drawHeldGun(Graphics2D g2, int screenWidth, int screenHeight, Map<String, BufferedImage> images, boolean isFiring){
        BufferedImage gun;
        if(isFiring){
            gun = images.get("gun_firing");
        } else {
            gun = images.get("gun");
        } 
        double gunWidth = screenWidth/3;
        double gunHeight = gunWidth*gun.getHeight()/gun.getWidth();
        double gunX = screenWidth - gunWidth - screenWidth/10;
        double gunY = screenHeight - gunHeight;

        g2.drawImage(gun,(int) gunX,(int) gunY,(int) gunWidth,(int) gunHeight, null);

        
    }

    private void drawPausedGame(Graphics2D g2){
        g2.drawImage(this.sceneImage, 0, 0, null);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        double width = this.config.screenWidth();
        double height = this.config.screenHeight();

        Rectangle2D transparentMesh = new Rectangle2D.Double(0, 0, width, height);
        g2.setColor(this.CTheme.getPauseMenuHue());
        g2.fill(transparentMesh);

        drawButtons(g2, this.buttons.getPauseMenuButtons(), this.CTheme, this.config.screenWidth());
    }

    private void drawSettingsGame(Graphics2D g2){
        g2.drawImage(this.sceneImage, 0, 0, null);

        double width = this.config.screenWidth();
        double height = this.config.screenHeight();

        Rectangle2D transparentMesh = new Rectangle2D.Double(0, 0, width, height);
        g2.setColor(this.CTheme.getPauseMenuHue());
        g2.fill(transparentMesh);

        drawButtons(g2, this.buttons.getSettingsMenuButtons(), this.CTheme, this.config.screenWidth());
    }

    /**
     * Draws the provided buttons on the game view using the specified color theme.
     *
     * @param g2      The Graphics2D object to draw the buttons with.
     * @param buttons An ArrayList of Button objects to be drawn.
     * @param CTheme  The ColorTheme to be applied to the buttons.
     */
    private static void drawButtons(Graphics2D g2, ArrayList<Button> buttons, ColorTheme CTheme, float screenWidth){
        for(Button b : buttons){
            g2.setColor(CTheme.getButtonColor());
            g2.fill(b.getRect());

            g2.setColor(CTheme.getButtonBorderColor());
            g2.setStroke(new BasicStroke(3));
            g2.draw(b.getRect());

            g2.setColor(CTheme.getTextColor());
            g2.setFont(new Font("Arial", Font.PLAIN, (int) (screenWidth/28.4666666667f)));
            Inf101Graphics.drawCenteredString(g2, b.getText(), b.getCenterX(), b.getCenterY());
        }
    }
    
}
