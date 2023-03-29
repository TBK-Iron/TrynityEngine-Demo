package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;

public interface IShape {
    
    public GridPosition getPos();

    public ArrayList<Face> getFaces();
}
