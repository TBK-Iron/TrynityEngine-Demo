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
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;
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

    public void setCamera(Camera camera){
        this.camera = camera;
    }

    public void addShape(Shape3D shape){
        shapes.add(shape);
    }

    public void addEntity(Entity entity){
        entities.add(entity);
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
                
                int collisions = 0;
                while(collidingBox != null){
                    newPos = collidingBox.getCollisionPos(this.camera.getCollisionBox(), this.camera.getPos(), newPos);
                    collidingBox = collisionDetector.getCollidingBox(this.camera.getCollisionBox(), newPos);
                    collisions++;
                }
                this.camera.setPos(newPos);
                if(collisions != 0){
                    this.cameraMoveSpeed = new Vector(new float[]{this.cameraMoveSpeed.get(0), 0, this.cameraMoveSpeed.get(2)});
                } else {
                    this.cameraMoveSpeed = applyGravityToDelta(this.cameraMoveSpeed);
                }              
            }
        }
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
                    if(entity.targetReached()){
                        entity.setPosition(entity.getTargetPosition());
                    } else {
                        GridPosition newPos = Vector.add(new Vector((Position3D) entity.getPosition()), entity.getMovementVector()).getPoint();
                        CollisionBox collidingBox = collisionDetector.getCollidingBox(entity.getCollisionBox(), newPos);
                        
                        int collisions = 0;
                        while(collidingBox != null){
                            newPos = collidingBox.getCollisionPos(entity.getCollisionBox(), entity.getPosition(), newPos);
                            collidingBox = collisionDetector.getCollidingBox(entity.getCollisionBox(), newPos);
                            collisions++;
                        }
                        entity.setPosition(newPos);
                        if(collisions != 0){
                            entity.setMovementVector(new Vector(new float[]{entity.getMovementVector().get(0), 0, entity.getMovementVector().get(2)}));
                        } else {
                            entity.setMovementVector(applyGravityToDelta(entity.getMovementVector()));
                        }  
                    }
                    
                }
            }
        }
    }

    public void updateEntityRotations(){
        for(Entity entity : this.entities){
            entity.rotate();
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
            Matrix rotationMatrix = new RotateTransform(new RelativeRotation(0, this.camera.getRotation().getLeftRight()), true).getMatrix();
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
