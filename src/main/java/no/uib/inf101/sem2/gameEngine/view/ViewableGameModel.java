package no.uib.inf101.sem2.gameEngine.view;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.grid3D.GridPosision;

public interface ViewableGameModel {
    
    public ArrayList<ArrayList<GridPosision>> getSortedFaces(GridPosision viewPos);
}
