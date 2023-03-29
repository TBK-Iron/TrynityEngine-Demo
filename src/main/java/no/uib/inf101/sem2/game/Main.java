package no.uib.inf101.sem2.game;

import no.uib.inf101.sem2.gameEngine.model.Model;
import no.uib.inf101.sem2.gameEngine.view.GameView;
import no.uib.inf101.sem2.gameEngine.view.SampleView;
import no.uib.inf101.sem2.gameEngine.view.ViewableGameModel;
import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;

import javax.swing.JFrame;

public class Main {
  public static void main(String[] args) {

    Model model = new Model();
    model.createShape(new GridPosition(0, 0, 0), new Rotation(0, 0, 0), "src/main/resources/tunnel.trym");
    GameView view = new GameView((ViewableGameModel) model);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("INF101");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);

  }
}
