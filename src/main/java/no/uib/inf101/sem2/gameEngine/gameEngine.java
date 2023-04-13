package no.uib.inf101.sem2.gameEngine;

import no.uib.inf101.sem2.gameEngine.controller.EngineController;
import no.uib.inf101.sem2.gameEngine.model.EngineModel;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.view.SceneMaker;

public interface gameEngine {
    EngineController controller();

    EngineModel model();

    SceneMaker sceneMaker();

    CollisionDetector collisionDetector();
}
