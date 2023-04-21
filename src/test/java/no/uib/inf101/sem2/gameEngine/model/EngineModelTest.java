package no.uib.inf101.sem2.gameEngine.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.config.DefaultConfig;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EngineModelTest {

    private EngineModel engineModel;
    private Config config;
    private CollisionDetector collisionDetector;
    private ShapeData shapeData;

    @BeforeEach
    public void setUp() {
        config = new DefaultConfig();
        collisionDetector = new CollisionDetector();
        engineModel = new EngineModel(config, collisionDetector);
        shapeData = new ShapeData(new Position3D(0, 0, 0), new RelativeRotation(0, 0), new File("src/main/resources/shapes/brick_cube.trym"));
    }

    @Test
    public void testAddShape() {
        Shape3D shape = new Shape3D(shapeData);
        engineModel.addShape(shape);
        assertEquals(1, engineModel.getRenderShapes().size());
        assertTrue(engineModel.getRenderShapes().contains(shape));
    }

    @Test
    public void testAddEntity() {
        Entity entity = new Entity(shapeData);
        engineModel.addEntity(entity);
        assertEquals(1, engineModel.getEntities().size());
        assertTrue(engineModel.getEntities().contains(entity));
    }

    @Test
    public void testSetCamera() {
        Camera newCamera = new Camera(new Position3D(0, 0, 0), new RelativeRotation(0, 0));
        engineModel.setCamera(newCamera);
        assertEquals(newCamera, engineModel.getCamera());
    }

    @Test
    public void testAddToCameraRotation() {
        RelativeRotation rotation = new RelativeRotation(0, 3);
        engineModel.addToCameraRotation(rotation);
        assertEquals(rotation, engineModel.getCamera().getRotation());
    }

    @Test
    public void testSetMovementDelta() {
        Vector delta = new Vector(new float[]{1, 2, 3});
        engineModel.setMovementDelta(delta);
        assertNotEquals(new Vector(new float[]{0, 0, 0}), engineModel.cameraMoveSpeed);
    }

}