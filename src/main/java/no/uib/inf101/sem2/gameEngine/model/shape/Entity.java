package no.uib.inf101.sem2.gameEngine.model.shape;

import java.io.File;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;

public class Entity extends Shape3D {

    public Entity(GridPosition pos, Rotation rotation, File file) {
        super(pos, rotation, file);
    }
    
    public void setRotation(Rotation newRotation){
        this.rotation = newRotation;
        this.updateRotation();
    }

    public void setPosision(GridPosition newPos){
        this.anchoredPos = newPos;
        this.updatePosition();
        
    }
}
