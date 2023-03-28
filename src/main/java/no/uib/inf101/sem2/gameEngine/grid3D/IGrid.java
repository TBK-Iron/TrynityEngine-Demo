package no.uib.inf101.sem2.gameEngine.grid3D;

public interface IGrid<E> extends Iterable<GridValue<E>> {

    public void addShape(E value,GridPosision pos);


}
