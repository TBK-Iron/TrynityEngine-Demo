package no.uib.inf101.sem2.gameEngine.model.shape.positionData;

public interface GridPosition{
    default public float x(){
        throw new UnsupportedOperationException("x is not supported for this position");
    };

    default public float y(){
        throw new UnsupportedOperationException("y is not supported for this position");
    };

    default public float z(){
        throw new UnsupportedOperationException("z is not supported for this position");
    };

    default public float w(){
        throw new UnsupportedOperationException("w is not supported for this position");
    };
}
