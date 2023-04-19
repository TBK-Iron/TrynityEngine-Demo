package no.uib.inf101.sem2.game.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import no.uib.inf101.sem2.game.controller.Button;
import no.uib.inf101.sem2.game.model.GameState;
import no.uib.inf101.sem2.game.model.resourceLoaders.TextureLoader;
import no.uib.inf101.sem2.game.view.ColorThemes.ColorTheme;
import no.uib.inf101.sem2.game.view.ColorThemes.DefaultColorTheme;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.view.SceneMaker;

public class GameView extends JPanel {

    private final SceneMaker engineView;
    private final ViewableGameModel model;
    private BufferedImage sceneImage;
    private final TextureLoader images;
    private Config config;
    private final ColorTheme CTheme;

    private ArrayList<Button> pauseMenuButtons;
    private ArrayList<Button> mainMenuButtons;


    public GameView(ViewableGameModel model, Config config, SceneMaker engineView, TextureLoader images) {
        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.model = model;
        this.engineView = engineView;
        this.config = config;
        this.images = images;
        this.CTheme = new DefaultColorTheme();

        this.createPauseMenuButtons();
        this.createMainMenuButtons();
    }

    private void createMainMenuButtons(){
        int sWidth = this.config.screenWidth();
        int sHeight = this.config.screenHeight();

        this.mainMenuButtons = new ArrayList<>();
        this.mainMenuButtons.add(new Button(sWidth/2, sHeight/4, sWidth/2, sHeight/8, "Play"));
        this.mainMenuButtons.add(new Button(sWidth/2, sHeight/2, sWidth/2, sHeight/8, "Settings"));
        this.mainMenuButtons.add(new Button(sWidth/2, sHeight*3/4, sWidth/2, sHeight/8, "Quit"));
    }
    
    private void createPauseMenuButtons(){
        int sWidth = this.config.screenWidth();
        int sHeight = this.config.screenHeight();

        this.pauseMenuButtons = new ArrayList<>();
        this.pauseMenuButtons.add(new Button(sWidth/2, sHeight/4, sWidth/2, sHeight/8, "Resume"));
        this.pauseMenuButtons.add(new Button(sWidth/2, sHeight/2, sWidth/2, sHeight/8, "Settings"));
        this.pauseMenuButtons.add(new Button(sWidth/2, sHeight*3/4, sWidth/2, sHeight/8, "Main Menu"));    
    }

    public ArrayList<Button> getPauseMenuButtons(){
        return this.pauseMenuButtons;
    }

    public ArrayList<Button> getMainMenuButtons(){
        return this.mainMenuButtons;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(this.model.getGameState() == GameState.LOADING){
            drawLoadingScreen(g2);
        } else if(this.model.getGameState() == GameState.MAIN_MENU){
            drawMainMenu(g2);
        } else if(this.model.getGameState() == GameState.LEVEL_MENU){

        } else if(this.model.getGameState() == GameState.ACTIVE){
            drawActiveGame(g2);
        } else if(this.model.getGameState() == GameState.PAUSED){
            drawPausedGame(g2);
        }
    }

    private void drawLoadingScreen(Graphics2D g2){
        double x = this.config.screenWidth() / 2;
        double y = this.config.screenHeight() / 2;
        Inf101Graphics.drawCenteredImage(g2, this.images.getLogo(), x, y, 0.45);
    }

    private void drawMainMenu(Graphics2D g2){
        double x = this.config.screenWidth() / 2;
        double y = this.config.screenHeight() / 2;
        Inf101Graphics.drawCenteredImage(g2, this.images.getMenuBackground(), x, y, 0.5);

        drawButtons(g2, this.mainMenuButtons, this.CTheme);
    }

    //TODO: add GUI elements
    private void drawActiveGame(Graphics2D g2){
        this.sceneImage = this.engineView.getNextSceneImage();
        g2.drawImage(this.sceneImage, 0, 0, null);
        drawCrosshair(g2, CTheme, config.screenWidth()/2, config.screenHeight()/2, 15);
        drawHealthBar(g2, this.model.getPlayerHealthPercent(), CTheme, config.screenHeight(), config.screenHeight()/20);


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
        
        Rectangle2D healthBarBackground = new Rectangle2D.Double(0, screenHeight - size, size*5, size);
        Rectangle2D healthBar = new Rectangle2D.Double(0, screenHeight - size, size*5*healthPercent, size);

        g2.setColor(CTheme.getHealthBackgroundColor());
        g2.fill(healthBarBackground);

        g2.setColor(CTheme.getHealthColor());
        g2.fill(healthBar);

        g2.setColor(CTheme.getHealthBorderColor());
        g2.draw(healthBarBackground);
    }

    //TODO: finish
    private void drawPausedGame(Graphics2D g2){
        g2.drawImage(this.sceneImage, 0, 0, null);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        double width = this.config.screenWidth();
        double height = this.config.screenHeight();

        Rectangle2D transparentMesh = new Rectangle2D.Double(0, 0, width, height);
        g2.setColor(this.CTheme.getPauseMenuHue());
        g2.fill(transparentMesh);

        drawButtons(g2, this.pauseMenuButtons, this.CTheme);
    }

    private static void drawButtons(Graphics2D g2, ArrayList<Button> buttons, ColorTheme CTheme){
        for(Button b : buttons){
            g2.setColor(CTheme.getButtonColor());
            g2.fill(b.getRect());

            g2.setColor(CTheme.getButtonBorderColor());
            g2.setStroke(new BasicStroke(3));
            g2.draw(b.getRect());

            g2.setColor(CTheme.getTextColor());
            Inf101Graphics.drawCenteredString(g2, b.getName(), b.getCenterX(), b.getCenterY());
        }
    }
    
}
