package no.uib.inf101.sem2.gameEngine.view.pipeline.transformations;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Matrix;

public interface Transformation {

    public Matrix getMatrix();

    public Face transform(Face face);
}
