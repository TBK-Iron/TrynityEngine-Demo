package no.uib.inf101.sem2.gameEngine.view;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.Camera;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;

public interface ViewableEngineModel {
    
    ArrayList<Shape3D> getRenderShapes();

    Camera getCamera();
}
