package no.uib.inf101.sem2.gameEngine.model.shape;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

class EntityTest {
    @Test
    void testConstructor() {
        ShapeData shapeData = new ShapeData(new Position3D(0, 0, 2.5f), new RelativeRotation((float) (Math.PI / 10), (float) (Math.PI / 4 - 0.2)), new File("src/main/resources/shapes/brick_cube.trym"));
        Entity entity = new Entity(shapeData);

        assertNotNull(entity, "Entity should not be null");
        assertEquals(new Position3D(0, 0, 2.5f), entity.getPosition(), "Entity position should match shapeData position");
        assertEquals( new RelativeRotation((float) (Math.PI / 10), (float) (Math.PI / 4 - 0.2)), entity.getRotation(), "Entity rotation should match shapeData rotation");
    }

    @Test
    void testSetCollision() {
        ShapeData shapeData = new ShapeData(new Position3D(0, 0, 2.5f), new RelativeRotation((float) (Math.PI / 10), (float) (Math.PI / 4 - 0.2)), new File("src/main/resources/shapes/brick_cube.trym"));
        Entity entity = new Entity(shapeData);
        CollisionBox collisionBox = new CollisionBox(new Position3D(0, 0, 0), new Position3D(1, 1, 1)); // Create a CollisionBox instance

        entity.setCollision(collisionBox);
        assertEquals(collisionBox, entity.getCollisionBox(), "Collision box should be set correctly");
    }

    @Test
    void testSetPosition() {
        ShapeData shapeData = new ShapeData(new Position3D(0, 0, 2.5f), new RelativeRotation((float) (Math.PI / 10), (float) (Math.PI / 4 - 0.2)), new File("src/main/resources/shapes/brick_cube.trym"));
        Entity entity = new Entity(shapeData);
        GridPosition newPosition = new Position3D(1, 1, 1);

        entity.setPosition(newPosition);
        assertEquals(newPosition, entity.getPosition(), "Entity position should be updated correctly");
    }

    @Test
    void testIsMoving() {
        ShapeData shapeData = new ShapeData(new Position3D(0, 0, 2.5f), new RelativeRotation((float) (Math.PI / 10), (float) (Math.PI / 4 - 0.2)), new File("src/main/resources/shapes/brick_cube.trym"));
        Entity entity = new Entity(shapeData);

        assertFalse(entity.isMoving(), "Entity should not be moving initially");
    
        entity.setMovementVector(new Vector(new float[]{1, 0, 0}));
        assertTrue(entity.isMoving(), "Entity should be moving after setting movement vector");
    }

    @Test
    public void testTargetReached() {
        ShapeData shapeData = new ShapeData(new Position3D(0, 0, 2.5f), new RelativeRotation((float) (Math.PI / 10), (float) (Math.PI / 4 - 0.2)), new File("src/main/resources/shapes/brick_cube.trym"));
        Entity entity = new Entity(shapeData);
        GridPosition targetPosition = new Position3D(1, 1, 1);
        entity.setTargetPosition(targetPosition, 0.5f);

        assertFalse(entity.targetReached(), "Target should not be reached initially");

        entity.setPosition(targetPosition);
        assertTrue(entity.targetReached(), "Target should be reached after setting position");
    }

    @Test
    public void testSetTargetPositionWithCollisionBox() {
        ShapeData shapeData = new ShapeData(new Position3D(0, 0, 2.5f), new RelativeRotation((float) (Math.PI / 10), (float) (Math.PI / 4 - 0.2)), new File("src/main/resources/shapes/brick_cube.trym"));
        Entity entity = new Entity(shapeData);

        GridPosition min = new Position3D(0, 0, 0);
        GridPosition max = new Position3D(1, 1, 1);
        CollisionBox collisionBox = new CollisionBox(min, max);
        entity.setCollision(collisionBox);

        GridPosition targetPosition = new Position3D(2, 2, 2);
        entity.setTargetPosition(targetPosition, 1.0f);

        assertEquals(targetPosition, entity.getTargetPosition(), "Target position should be set correctly");
        assertTrue(entity.isMoving(), "Entity should be moving after setting target position");
    }

    @Test
    public void testSetRotationDelta() {
        ShapeData shapeData = new ShapeData(new Position3D(0, 0, 2.5f), new RelativeRotation((float) (Math.PI / 10), (float) (Math.PI / 4 - 0.2)), new File("src/main/resources/shapes/brick_cube.trym"));
        Entity entity = new Entity(shapeData);

        RelativeRotation delta = new RelativeRotation(0.1f, 0.1f, 0.1f);
        int frames = 5;
        RelativeRotation acceleration = new RelativeRotation(0.01f, 0.01f, 0.01f);

        entity.setRotationDelta(delta, frames, acceleration);

        for (int i = 0; i < frames; i++) {
            RelativeRotation initialRotation = entity.getRotation();
            entity.rotate();
            RelativeRotation finalRotation = entity.getRotation();
            assertEquals(initialRotation.add(delta), finalRotation, "Rotation should update correctly after each frame");
        }
    }

}