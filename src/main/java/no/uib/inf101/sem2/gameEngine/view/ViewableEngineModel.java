package no.uib.inf101.sem2.gameEngine.view;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public interface ViewableEngineModel {
    
    public ArrayList<Shape3D> getShapes();

    public RelativeRotation getCameraRotation();

    public GridPosition getCameraPosition();
}
