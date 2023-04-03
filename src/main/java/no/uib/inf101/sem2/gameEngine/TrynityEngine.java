package no.uib.inf101.sem2.gameEngine;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.controller.EngineController;
import no.uib.inf101.sem2.gameEngine.model.EngineModel;
import no.uib.inf101.sem2.gameEngine.view.SceneMaker;

public class TrynityEngine implements gameEngine {
    EngineController controller;
    EngineModel model;
    SceneMaker sceneMaker;

    public TrynityEngine(Config config){
        this.model = new EngineModel();
        this.sceneMaker = new SceneMaker(model, config);
        this.controller = new EngineController(model, sceneMaker, config);
    }

    public EngineController controller(){
        return this.controller;
    }

    public EngineModel model(){
        return this.model;
    }

    public SceneMaker sceneMaker(){
        return this.sceneMaker;
    }
}
