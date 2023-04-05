package no.uib.inf101.sem2.gameEngine.view;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.Camera;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;

public interface ViewableEngineModel {
    
    public ArrayList<Shape3D> getRenderShapes();

    public Camera getCamera();
}
