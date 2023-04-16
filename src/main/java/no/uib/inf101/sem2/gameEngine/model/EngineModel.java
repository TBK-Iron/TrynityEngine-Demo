package no.uib.inf101.sem2.gameEngine.model;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.controller.ControllableEngineModel;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionBox;
import no.uib.inf101.sem2.gameEngine.model.collision.CollisionDetector;
import no.uib.inf101.sem2.gameEngine.model.shape.Entity;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.ShapeData;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.ViewableEngineModel;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.RotateTransform;

public class EngineModel implements ViewableEngineModel, ControllableEngineModel {
    
    ArrayList<Shape3D> shapes;
    ArrayList<Entity> entities;
    Camera camera;
    CollisionDetector collisionDetector;
    Vector cameraMoveSpeed;
    Config config;
    
    public EngineModel(Config config, CollisionDetector collisionDetector){
        this.shapes = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.camera = new Camera(new Position3D(0, 0, 0), new RelativeRotation(0, 0, 0));
        this.cameraMoveSpeed = new Vector(new float[]{0, 0, 0});
        this.config = config;
        this.collisionDetector = collisionDetector;
    }

    public void createShape(ShapeData shapeData){
        shapes.add(new Shape3D(shapeData));
    }

    public void createEntity(ShapeData shapeData, CollisionBox collisionBox){
        entities.add(new Entity(shapeData, collisionBox));
    }

    public void setCameraCollision(CollisionBox collisionBox){
        this.camera.setCollision(collisionBox);
    }

    public ArrayList<Shape3D> getRenderShapes(){
        ArrayList<Shape3D> allShapes = new ArrayList<>();

        for(Shape3D shape : this.shapes){
            allShapes.add(shape);
        }
        for(Entity entity : this.entities){
            allShapes.add((Shape3D) entity);
        }

        return allShapes;
    }
    public ArrayList<Entity> getEntities(){
        return this.entities;
    }

    public float maxDistFromPointToFace(GridPosition pos1, Face face){
        float maxDistance = 0.0f;
        for(GridPosition pos2 : face.getPoints()){
            float dist = (float) Math.sqrt(Math.pow(pos2.x() - pos1.x(), 2) + Math.pow(pos2.x() - pos1.x(), 2) + Math.pow(pos2.x() - pos1.x(), 2));
            if(dist > maxDistance){
                maxDistance = dist;
            }
        }
        return maxDistance;
    }
    public void updateCameraPosition(){
        if(this.cameraMoveSpeed.magnitude() != 0){
            if(this.camera.getCollisionBox() == null || this.config.noclip()){
                this.camera.setPos(Vector.add(new Vector((Position3D) this.camera.getPos()), this.cameraMoveSpeed).getPoint());
            } else {
                GridPosition newPos = Vector.add(new Vector((Position3D) this.camera.getPos()), this.cameraMoveSpeed).getPoint();
                CollisionBox collidingBox = collisionDetector.getCollidingBox(this.camera.getCollisionBox(), newPos);
                if(collidingBox == null){
                    this.camera.setPos(newPos);
                    this.cameraMoveSpeed = applyGravityToDelta(this.cameraMoveSpeed);
                } else {     
                    GridPosition correctedNewPos = collidingBox.getCollisionPos(this.camera.getCollisionBox(), this.camera.getPos(), newPos);
                    this.camera.setPos(correctedNewPos);
                    //System.out.println(correctedNewPos);
                    this.cameraMoveSpeed = new Vector(new float[]{this.cameraMoveSpeed.get(0), 0, this.cameraMoveSpeed.get(2)});
                }
            }
        }
        
        //System.out.println("Camera position set to: " + this.cameraPos);
    }

    public void updateEntityPositions(){
        for(Entity entity : this.entities){
            if(entity.isMoving()){
                if(entity.getCollisionBox() == null){
                    if(entity.targetReached()){
                        entity.setPosition(entity.getTargetPosition());
                        entity.setMovementVector(new Vector(new float[]{0, 0, 0}));
                    } else {
                        entity.setPosition(Vector.add(new Vector((Position3D) entity.getPosition()), entity.getMovementVector()).getPoint());
                    }
                }else {
                    GridPosition newPos = Vector.add(new Vector((Position3D) entity.getPosition()), entity.getMovementVector()).getPoint();
                    CollisionBox collidingBox = collisionDetector.getCollidingBox(entity.getCollisionBox(), newPos);
                    if(collidingBox == null){
                        if(entity.targetReached()){
                            entity.setPosition(entity.getTargetPosition());
                            Vector delta = new Vector(new float[]{0, 0, 0});
                            entity.setMovementVector(applyGravityToDelta(delta));
                        } else {
                            entity.setPosition(newPos);
                            entity.setMovementVector(applyGravityToDelta(entity.getMovementVector()));
                        }
                        
                    } else {
                        GridPosition correctedNewPos = collidingBox.getCollisionPos(entity.getCollisionBox(), entity.getPosition(), newPos);
                        if(entity.targetReached()){
                            entity.setPosition(entity.getTargetPosition());
                        } else {
                            entity.setPosition(newPos);
                        }
                        
    
                        entity.setPosition(correctedNewPos);
                        entity.setMovementVector(new Vector(new float[]{entity.getMovementVector().get(0), 0, entity.getMovementVector().get(2)}));
                    }
                }
            }
        }
    }

    public Camera getCamera(){
        return this.camera;
    }

    public void addToCameraRotation(RelativeRotation cameraRotation){
        //System.out.println(cameraRotation);
        
        this.camera.setRotation(this.camera.getRotation().add(cameraRotation));
        //System.out.println("Camera rotation set to: " + this.cameraRotation);
    }

    public void setMovementDelta(Vector relativeDelta){
        Vector delta;
        if(relativeDelta.magnitude() != 0){
            Matrix rotationMatrix = new RotateTransform(new RelativeRotation(0, this.camera.getRotation().getLeftRight())).getMatrix();
            delta = rotationMatrix.multiply(relativeDelta);
        } else {
            
            delta = relativeDelta;
        }
        if(this.config.noclip()){
            this.cameraMoveSpeed = delta;
        } else if(this.cameraMoveSpeed.get(1) == 0) {
            this.cameraMoveSpeed = new Vector(new float[]{delta.get(0), delta.get(1) + this.cameraMoveSpeed.get(1), delta.get(2)});
        } else {
            this.cameraMoveSpeed = new Vector(new float[]{delta.get(0), this.cameraMoveSpeed.get(1), delta.get(2)});
        }
        
        //System.out.println("Camera movement set to: " + this.cameraMoveSpeed);
    }

    private Vector applyGravityToDelta(Vector delta){
        Vector newDelta = new Vector(new float[]{delta.get(0), delta.get(1) - this.config.gravityAcceleration(), delta.get(2)});
        
        return newDelta;
    }
}
