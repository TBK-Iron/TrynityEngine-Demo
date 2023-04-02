package no.uib.inf101.sem2.game.controller;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.controller.ControllableEngineModel;
import no.uib.inf101.sem2.gameEngine.controller.EngineController;
import no.uib.inf101.sem2.gameEngine.view.GameView;

public class GameController extends EngineController {
    public GameController(ControllableEngineModel engineModel, GameView view, Config config) {
        super(engineModel, view, config);
    }
}
