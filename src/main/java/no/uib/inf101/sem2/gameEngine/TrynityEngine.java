package no.uib.inf101.sem2.gameEngine;

import java.awt.image.BufferedImage;
import java.util.Map;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.controller.EngineController;
import no.uib.inf101.sem2.gameEngine.model.EngineModel;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.view.SceneMaker;

/**
 * TrynityEngine is the main class for managing the game engine components, including the controller,
 * collision detector, model, and scene maker.
 * 
 * This is the class that ties together the game and the engine.
 */
public final class TrynityEngine {
    private final EngineController controller;
    private final CollisionDetector collisionDetector;
    private final EngineModel model;
    private final SceneMaker sceneMaker;


    /**
     * Constructs a TrynityEngine with the provided configuration and textures.
     *
     * @param config The configuration object containing various game settings.
     * @param textures The map of texture keys to BufferedImage objects.
     */
    public TrynityEngine(Config config, Map<String, BufferedImage> textures){
        this.collisionDetector = new CollisionDetector();
        this.model = new EngineModel(config, collisionDetector);
        this.sceneMaker = new SceneMaker(model, config, textures);
        this.controller = new EngineController(model, config);
    }

    /**
     * Returns the EngineController for this game engine.
     *
     * @return The EngineController object.
     */
    public EngineController controller(){
        return this.controller;
    }

    /**
     * Returns the CollisionDetector for this game engine.
     *
     * @return The CollisionDetector object.
     */
    public CollisionDetector collisionDetector(){
        return this.collisionDetector;
    }

    /**
     * Returns the EngineModel for this game engine.
     *
     * @return The EngineModel object.
     */
    public EngineModel model(){
        return this.model;
    }

    /**
     * Returns the SceneMaker for this game engine.
     *
     * @return The SceneMaker object.
     */
    public SceneMaker sceneMaker(){
        return this.sceneMaker;
    }
}
