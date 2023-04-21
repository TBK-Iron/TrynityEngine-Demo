package no.uib.inf101.sem2.gameEngine.model;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.*;

public interface ConfigurableEngineModel {
    
    public void setCamera(Camera camera);

    public void addShape(Shape3D shape);

    public void addEntity(Entity entity);

    public void resetModel();

    public ArrayList<Entity> getEntities();

    public void updateCameraPosition();

    public void updateEntityPositions();

    public void updateEntityRotations();
}
