package no.uib.inf101.sem2.gameEngine.view;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;

public interface ViewableGameModel {
    
    public ArrayList<Face> getSortedFaces(GridPosition viewPos);
}
