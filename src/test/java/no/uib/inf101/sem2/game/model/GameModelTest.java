package no.uib.inf101.sem2.game.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import no.uib.inf101.sem2.game.model.levels.TestLevel;
import no.uib.inf101.sem2.game.model.resourceLoaders.SoundPlayer;
import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;
import no.uib.inf101.sem2.gameEngine.model.ConfigurableEngineModel;
import no.uib.inf101.sem2.gameEngine.model.EngineModel;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameModelTest {

    private GameModel gameModel;

    @BeforeEach
    public void setUp() {
        Config config = new DefaultConfig();
        CollisionDetector collisionDetector = new CollisionDetector();
        ConfigurableEngineModel engineModel = new EngineModel(config, collisionDetector);
        SoundPlayer soundPlayer = new SoundPlayer();

        gameModel = new GameModel(engineModel, collisionDetector, soundPlayer, config);
        gameModel.loadMap(new TestLevel());
    }

    @Test
    public void testGetGameState() {
        assertEquals(GameState.LOADING, gameModel.getGameState(), "Game state should be LOADING initially.");
    }

    @Test
    public void testSetGameState() {
        gameModel.setGameState(GameState.ACTIVE);
        assertEquals(GameState.ACTIVE, gameModel.getGameState(), "Game state should be RUNNING after being set.");
    }

    @Test
    public void testGetPlayerHealthPercent() {
        float initialHealthPercent = gameModel.getPlayerHealthPercent();
        assertEquals(1.0f, initialHealthPercent, "Player's initial health percent should be 100.");
    }
}
