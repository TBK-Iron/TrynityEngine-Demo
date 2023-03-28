package no.uib.inf101.sem2.gameEngine.model;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.grid3D.GridPosision;

public interface IShape {
    
    public GridPosision getPos();

    public ArrayList<ArrayList<GridPosision>> getFaces();
}
