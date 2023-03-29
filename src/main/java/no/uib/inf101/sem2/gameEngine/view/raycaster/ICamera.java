package no.uib.inf101.sem2.gameEngine.view.raycaster;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosision;

public interface ICamera {
    public void setPos(GridPosision pos);

    public void setRotation(RelativeRotation rotation);

    public boolean isRendered(GridPosision vertex);
}
