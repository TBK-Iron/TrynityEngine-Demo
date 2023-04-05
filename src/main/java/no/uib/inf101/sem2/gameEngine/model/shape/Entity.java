package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;


public class Entity extends Shape3D {

    ArrayList<GridPosition> collisionPoints;
    Vector movementVector;

    public Entity(ShapeData shapeData, ShapeData collisionShape) {
        super(shapeData);
        getCollisionPoints(collisionShape);
        this.movementVector = new Vector(new float[]{0, 0, 0});
    }

    private void getCollisionPoints(ShapeData collisionShape){
        Shape3D collisionShape3D = new Shape3D(collisionShape);
        for(Face face : collisionShape3D.getFaces()){
            for(GridPosition point : face.getPoints()){
                this.collisionPoints.add(point);
            }
        }
    }
    
    public void setRotation(RelativeRotation newRotation){
        this.rotation = newRotation;
        
    }

    public void setPosision(GridPosition newPos){
        this.anchoredPos = newPos;
    }

    public ArrayList<GridPosition> getCollisionPoints(){
        return this.collisionPoints;
    }

    public void setMovementVector(Vector newVector){
        this.movementVector = newVector;
    }

    public void move(){
        float newX = this.anchoredPos.x() + this.movementVector.get(0);
        float newY = this.anchoredPos.y() + this.movementVector.get(1);
        float newZ = this.anchoredPos.z() + this.movementVector.get(2);
        this.anchoredPos = new Position3D(newX, newY, newZ);
    }

    public boolean isMoving(){
        float magnitude = this.movementVector.magnitude();
        if(magnitude == 0){
            return false;
        } else {
            return true;
        }
    }
}
