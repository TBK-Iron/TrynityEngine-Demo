package no.uib.inf101.sem2.game.view;

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
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import no.uib.inf101.sem2.game.model.GameState;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.view.SceneMaker;

public class GameView extends JPanel {

    private final SceneMaker engineView;
    private final ViewableGameModel model;
    private BufferedImage sceneImage;
    private final BufferedImage logoImage;
    private Config config;


    public GameView(ViewableGameModel model, Config config, SceneMaker engineView, BufferedImage logoImage) {
        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.model = model;
        this.engineView = engineView;
        this.config = config;
        this.logoImage = logoImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(this.model.getGameState() == GameState.LOADING){
            drawLoadingScreen(g2);
        } else if(this.model.getGameState() == GameState.MAIN_MENU){

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
        Inf101Graphics.drawCenteredImage(g2, logoImage, x, y, 0.45);
    }

    private void drawActiveGame(Graphics2D g2){
        this.sceneImage = this.engineView.getNextSceneImage();
        g2.drawImage(this.sceneImage, 0, 0, null);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image transparentImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Cursor transparentCursor = toolkit.createCustomCursor(transparentImage, new Point(0, 0), "transparentCursor");
        this.setCursor(transparentCursor);
    }

    private void drawPausedGame(Graphics2D g2){
        g2.drawImage(this.sceneImage, 0, 0, null);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        double width = this.config.screenWidth();
        double height = this.config.screenHeight();

        Rectangle2D transparentMesh = new Rectangle2D.Double(0, 0, width, height);
        g2.setColor(new Color(128, 128, 128, 128));
        g2.fill(transparentMesh);

        Rectangle2D resumeButton = new Rectangle2D.Double(width*1/4, height*0.25, width*2/4, height*0.1);
        g2.setColor(Color.GRAY);
        g2.fill(resumeButton);
    }
    
}
