package no.uib.inf101.sem2.gameEngine.view.pipeline;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;

public interface IPipeline {

    public ArrayList<Shape3D> cameraTransform(ArrayList<Shape3D> shapes);

    public ArrayList<Shape3D> cull(ArrayList<Shape3D> shapes);

    public ArrayList<Face> clipTransform(ArrayList<Shape3D> shapes);

    public ArrayList<Face> clip(ArrayList<Face> faces);

    public ArrayList<Face> NDCTransform(ArrayList<Face> faces);

    public ArrayList<Face> sortFacesByZ(ArrayList<Face> faces);

    public ArrayList<Face> castTo2D(ArrayList<Face> faces);
}
