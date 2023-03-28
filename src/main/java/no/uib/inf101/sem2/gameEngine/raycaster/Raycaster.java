package no.uib.inf101.sem2.gameEngine.raycaster;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.Shape3D;

public class Raycaster {
    ICamera viewport;
    double fov;


    public Raycaster(int pixelsHorizontal, int pixelsVertical){
        this.viewport = new Camera(pixelsHorizontal, pixelsVertical, Math.PI/2);
    }


    public void cast(ArrayList<Shape3D>){

    }

}
