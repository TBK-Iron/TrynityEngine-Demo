package no.uib.inf101.sem2.gameEngine.view;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.grid3D.GridPosision;
import no.uib.inf101.sem2.gameEngine.model.Face;

public interface ViewableGameModel {
    
    public ArrayList<Face> getSortedFaces(GridPosision viewPos);
}
