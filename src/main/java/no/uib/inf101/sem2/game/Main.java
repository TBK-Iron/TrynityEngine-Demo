package no.uib.inf101.sem2.game;

import no.uib.inf101.sem2.game.controller.ButtonsHandler;
import no.uib.inf101.sem2.game.controller.ControllableGameModel;
import no.uib.inf101.sem2.game.controller.GameController;
import no.uib.inf101.sem2.game.model.GameModel;
import no.uib.inf101.sem2.game.model.GameState;
import no.uib.inf101.sem2.game.model.resourceLoaders.SoundPlayer;
import no.uib.inf101.sem2.game.model.resourceLoaders.TextureLoader;
import no.uib.inf101.sem2.game.settings.DefaultSettings;
import no.uib.inf101.sem2.game.settings.Settings;
import no.uib.inf101.sem2.game.view.GameView;
import no.uib.inf101.sem2.game.view.ViewableGameModel;
import no.uib.inf101.sem2.gameEngine.TrynityEngine;
import no.uib.inf101.sem2.gameEngine.gameEngine;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.ConfigurableEngineModel;

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.event.ComponentAdapter;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {
  public static void main(String[] args) {

    Settings settings = new DefaultSettings();
    Config config = (Config) settings;

    SoundPlayer soundPlayer = new SoundPlayer();
    TextureLoader textureLoader = new TextureLoader();


    gameEngine engine = new TrynityEngine(config, textureLoader.getTextures());

    ButtonsHandler buttonsHandler = new ButtonsHandler(config);

    GameModel model = new GameModel((ConfigurableEngineModel) engine.model(), engine.collisionDetector(), soundPlayer, config);
    GameView view = new GameView((ViewableGameModel) model, buttonsHandler, config, engine.sceneMaker(), textureLoader);
    GameController controller = new GameController((ControllableGameModel) model, view, settings, engine.controller());

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("INF101");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);

    frame.getRootPane().addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent componentEvent) {
        settings.setScreenSize(componentEvent.getComponent().getWidth(), componentEvent.getComponent().getHeight());
        buttonsHandler.resetButtonHandler();
      }
    });

    Timer timer = new Timer((int) (1000 / config.fps()), new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        if(model.getGameState() == GameState.ACTIVE){
          engine.model().updateCameraPosition();
          engine.model().updateEntityPositions();
          engine.model().updateEntityRotations();
          model.updateGame();
        }
  
        view.repaint();
      }
    });
 
    timer.start();

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    Runnable task = () -> {
        model.setGameState(GameState.MAIN_MENU);
    };

    executor.schedule(task, 2500, TimeUnit.MILLISECONDS);
  }
}
