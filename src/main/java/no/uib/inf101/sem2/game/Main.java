package no.uib.inf101.sem2.game;

import no.uib.inf101.sem2.game.controller.ControllableGameModel;
import no.uib.inf101.sem2.game.controller.GameController;
import no.uib.inf101.sem2.game.model.GameModel;
import no.uib.inf101.sem2.game.model.GameState;
import no.uib.inf101.sem2.game.model.levels.GrassWorld;
import no.uib.inf101.sem2.game.model.levels.Level;
import no.uib.inf101.sem2.game.model.levels.TestLevel1;
import no.uib.inf101.sem2.game.model.levels.TextureTest;
import no.uib.inf101.sem2.game.model.resourceLoaders.TextureLoader;
import no.uib.inf101.sem2.game.view.GameView;
import no.uib.inf101.sem2.game.view.ViewableGameModel;
import no.uib.inf101.sem2.gameEngine.TrynityEngine;
import no.uib.inf101.sem2.gameEngine.gameEngine;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {
  public static void main(String[] args) {

    Config config = new DefaultConfig();
    TextureLoader textureLoader = new TextureLoader();


    gameEngine engine = new TrynityEngine(config, textureLoader.getTextures());

    Level map = new GrassWorld();

    GameModel model = new GameModel(map, engine.model(), engine.collisionDetector());
    GameView view = new GameView((ViewableGameModel) model, config, engine.sceneMaker(), textureLoader.getLogo());
    GameController controller = new GameController((ControllableGameModel) model, view, config, engine.controller());

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("INF101");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);

    frame.getRootPane().addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent componentEvent) {
        config.resizeFrame(componentEvent.getComponent().getWidth(), componentEvent.getComponent().getHeight());
      }
    });

    Timer timer = new Timer((int) (1000 / config.fps()), new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        if(model.getGameState() == GameState.ACTIVE){
          engine.model().updateCameraPosition();
        }
  
        view.repaint();
      }
    });
 
    timer.start();

    
  }
}
