package no.uib.inf101.sem2.gameEngine.model.shape;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;

public final class CollisionBox {
    private final GridPosition pos1;
    private final GridPosition pos2;

    public CollisionBox(GridPosition pos1, GridPosition pos2){
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public boolean isColliding(ArrayList<GridPosition> shapePoints, GridPosition anchoredShapePos){
        GridPosition collPos1 = pos1;
        GridPosition collPos2 = pos2;

        for(GridPosition point : shapePoints){
            point = new Position3D(point.x() + anchoredShapePos.x(), point.y() + anchoredShapePos.y(), point.z() + anchoredShapePos.z());
            if((point.x() < collPos1.x() && point.x() > collPos2.x()) || (point.x() > collPos1.x() && point.x() < collPos1.x())){
                if((point.y() < collPos1.y() && point.y() > collPos2.y()) || (point.y() > collPos1.y() && point.y() < collPos1.y())){
                    if((point.z() < collPos1.z() && point.z() > collPos2.z()) || (point.z() > collPos1.z() && point.z() < collPos1.z())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    

}
