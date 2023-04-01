package no.uib.inf101.sem2.gameEngine.view.raycaster;


import no.uib.inf101.sem2.gameEngine.grid3D.Grid;
import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position2D;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Vector;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.ViewProjectionMatrix;

public interface ICamera {
    public void updatePos(GridPosition pos);

    public void updateRotation(RelativeRotation rotation);

    public Frustum getFrustum();

    public ViewProjectionMatrix getViewProjectionMatrix();

    public GridPosition getCastPos();

    public RelativeRotation getRotation();

    public float getFocalLength();

    public float getWidth();

    public float getHeight();
}
