package no.uib.inf101.sem2.game;

import no.uib.inf101.sem2.gameEngine.model.Model;
import no.uib.inf101.sem2.gameEngine.view.GameView;
import no.uib.inf101.sem2.gameEngine.view.ViewableGameModel;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.game.controller.GameController;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;
import no.uib.inf101.sem2.gameEngine.controller.ControllableEngineModel;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {
  public static void main(String[] args) {

    Config config = new DefaultConfig();

    Model model = new Model();
    model.createShape(new Position3D(0, 0, 0), new RelativeRotation(0, 0), "src/main/resources/tunnel.trym");
    GameView view = new GameView((ViewableGameModel) model, config);

    GameController controller = new GameController((ControllableEngineModel) model, view, config);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("INF101");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);

    Timer timer = new Timer((int) (1000 / config.fps()), new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        view.repaint();
      }
    });

    timer.start();
  }
}
