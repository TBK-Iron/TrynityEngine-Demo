package no.uib.inf101.sem2.gameEngine.grid3D;

import no.uib.inf101.sem2.gameEngine.model.shape.GridPosision;

public interface IGrid<E> extends Iterable<GridValue<E>> {

    public void addShape(E value,GridPosision pos);


}
