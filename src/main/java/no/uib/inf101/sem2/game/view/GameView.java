package no.uib.inf101.sem2.game.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import no.uib.inf101.sem2.game.model.GameModel;
import no.uib.inf101.sem2.game.model.GameState;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.view.SceneMaker;

public class GameView extends JPanel {

    SceneMaker engineView;
    ViewableGameModel model;


    public GameView(ViewableGameModel model, Config config, SceneMaker engineView) {
        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.model = model;
        this.engineView = engineView;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(this.model.getCurrentState() == GameState.ACTIVE){
            drawActiveGame(g2);
        }
    }

    private void drawActiveGame(Graphics2D g2){
        BufferedImage nextScene = this.engineView.getNextSceneImage();
        g2.drawImage(nextScene, 0, 0, null);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image transparentImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Cursor transparentCursor = toolkit.createCustomCursor(transparentImage, new Point(0, 0), "transparentCursor");
        this.setCursor(transparentCursor);
    }
}
