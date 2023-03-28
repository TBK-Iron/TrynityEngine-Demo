package no.uib.inf101.sem2.gameEngine.raycaster;

import no.uib.inf101.sem2.gameEngine.grid3D.GridPosision;
import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;

public interface ICamera {
    public void setPos(GridPosision pos);

    public void setRotation(Rotation rotation);
}
