package no.uib.inf101.sem2.gameEngine.view.raycaster;

import no.uib.inf101.sem2.gameEngine.grid3D.Grid;
import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;

public interface ICamera {
    public void updatePos(GridPosition pos);

    public void updateRotation(RelativeRotation rotation);

    public boolean isRendered(GridPosition vertex);

    public GridPosition getCastPos();

    public RelativeRotation getRotation();

    public double getFocalLength();
}
