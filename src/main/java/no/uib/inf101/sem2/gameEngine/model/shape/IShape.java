package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public interface IShape {
    
    public GridPosition getPosition();

    public RelativeRotation getRotation();

    public ArrayList<Face> getFaces();
}
