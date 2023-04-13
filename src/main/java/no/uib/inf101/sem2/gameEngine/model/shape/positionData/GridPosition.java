package no.uib.inf101.sem2.gameEngine.model.shape.positionData;

public interface GridPosition{
    default float x(){
        throw new UnsupportedOperationException("x is not supported for this position");
    }

    default float y(){
        throw new UnsupportedOperationException("y is not supported for this position");
    }

    default float z(){
        throw new UnsupportedOperationException("z is not supported for this position");
    }

    default float w(){
        throw new UnsupportedOperationException("w is not supported for this position");
    }
}
