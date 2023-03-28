package no.uib.inf101.sem2.gameEngine.model;

import java.io.File;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.grid3D.GridPosision;
import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;

public class Entity<E> extends Shape3D {

    public Entity(GridPosision pos, Rotation rotation, File file) {
        super(pos, rotation, file);
    }
    
    public void setRotation(Rotation newRotation){
        this.rotation = newRotation;
        this.updateRotation();
    }

    public void setPosision(GridPosision newPos){
        this.anchoredPos = newPos;
        this.updatePosision();
        
    }
}
