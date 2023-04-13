package no.uib.inf101.sem2.gameEngine;

import java.awt.image.BufferedImage;
import java.util.Map;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.controller.EngineController;
import no.uib.inf101.sem2.gameEngine.model.EngineModel;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.view.SceneMaker;

public final class TrynityEngine implements gameEngine {
    private final EngineController controller;
    private final CollisionDetector collisionDetector;
    private final EngineModel model;
    private final SceneMaker sceneMaker;

    public TrynityEngine(Config config, Map<String, BufferedImage> textures){
        this.collisionDetector = new CollisionDetector();
        this.model = new EngineModel(config, collisionDetector);
        this.sceneMaker = new SceneMaker(model, config, textures);
        this.controller = new EngineController(model, sceneMaker, config);
    }

    public EngineController controller(){
        return this.controller;
    }

    public CollisionDetector collisionDetector(){
        return this.collisionDetector;
    }

    public EngineModel model(){
        return this.model;
    }

    public SceneMaker sceneMaker(){
        return this.sceneMaker;
    }
}
