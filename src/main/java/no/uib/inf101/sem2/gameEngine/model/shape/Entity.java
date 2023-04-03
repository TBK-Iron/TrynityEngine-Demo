package no.uib.inf101.sem2.gameEngine.model.shape;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;


public class Entity extends Shape3D {

    public Entity(ShapeData shapeData) {
        super(shapeData);
    }
    
    public void setRotation(RelativeRotation newRotation){
        this.rotation = newRotation;
        
    }

    public void setPosision(GridPosition newPos){
        this.anchoredPos = newPos;
    }
}
