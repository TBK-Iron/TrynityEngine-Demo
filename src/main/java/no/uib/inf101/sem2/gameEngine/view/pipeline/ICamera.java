package no.uib.inf101.sem2.gameEngine.view.pipeline;


import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Transformation;

public interface ICamera {
    public void updatePos(GridPosition pos);

    public void updateRotation(RelativeRotation rotation);

    public Frustum getFrustum();

    public Transformation getViewProjectionTransform();

    public Transformation getViewTranform();

    public Transformation getProjectionTransform();

    public GridPosition getCastPos();

    public RelativeRotation getRotation();

    public float getFocalLength();

    public float getWidth();

    public float getHeight();
}
