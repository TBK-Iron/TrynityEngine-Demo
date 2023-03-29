package no.uib.inf101.sem2.gameEngine.grid3D;

import java.util.ArrayList;
import java.util.Iterator;

public class Grid<E> implements IGrid<E> {
    ArrayList<GridValue<E>> shapes;

    public Grid(){
        shapes = new ArrayList<>();
    }

    @Override
    public void addShape(E value, GridPosision pos){
        shapes.add(new GridValue<E>(value, pos));
    }

    @Override
    public Iterator<GridValue<E>> iterator(){
        return shapes.iterator();
    }
}