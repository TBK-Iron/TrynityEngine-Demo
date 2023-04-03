package no.uib.inf101.sem2.game;

import no.uib.inf101.sem2.gameEngine.model.EngineModel;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.SceneMaker;
import no.uib.inf101.sem2.gameEngine.view.ViewableEngineModel;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.game.controller.ControllableGameModel;
import no.uib.inf101.sem2.game.controller.GameController;
import no.uib.inf101.sem2.game.model.GameModel;
import no.uib.inf101.sem2.game.model.levels.Level;
import no.uib.inf101.sem2.game.model.levels.TestLevel1;
import no.uib.inf101.sem2.game.view.GameView;
import no.uib.inf101.sem2.game.view.ViewableGameModel;
import no.uib.inf101.sem2.gameEngine.TrynityEngine;
import no.uib.inf101.sem2.gameEngine.gameEngine;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;
import no.uib.inf101.sem2.gameEngine.controller.ControllableEngineModel;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {
  public static void main(String[] args) {

    Config config = new DefaultConfig();

    gameEngine engine = new TrynityEngine(config);

    Level map = new TestLevel1();

    GameModel model = new GameModel(map, engine.model());
    GameView view = new GameView((ViewableGameModel) model, config, engine.sceneMaker());
    GameController controller = new GameController((ControllableGameModel) model, view, config, engine.controller());

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("INF101");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);

    Timer timer = new Timer((int) (1000 / config.fps()), new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        engine.model().updateCameraPosition();
        view.repaint();
      }
    });

    timer.start();
  }
}
