package no.uib.inf101.sem2.gameEngine.model.shape;

import java.io.File;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;


public class Entity extends Shape3D {

    public Entity(GridPosition pos, RelativeRotation rotation, File file) {
        super(pos, rotation, file);
    }
    
    public void setRotation(RelativeRotation newRotation){
        this.rotation = newRotation;
        this.updateRotation();
    }

    public void setPosision(GridPosition newPos){
        this.anchoredPos = newPos;
        this.updatePosition();
        
    }
}
