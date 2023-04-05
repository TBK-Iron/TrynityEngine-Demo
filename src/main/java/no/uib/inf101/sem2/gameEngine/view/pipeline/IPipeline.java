package no.uib.inf101.sem2.gameEngine.view.pipeline;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.Camera;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

public interface IPipeline {

    public ArrayList<Shape3D> worldTransform(ArrayList<Shape3D> shapes);

    public ArrayList<Shape3D> cameraTransform(ArrayList<Shape3D> shapes, Camera camera);

    public ArrayList<Shape3D> cull(ArrayList<Shape3D> shapes);

    public ArrayList<Face> clip(ArrayList<Shape3D> shapes);

    public ArrayList<Face> projectTransform(ArrayList<Face> faces);

    public ArrayList<Face> NDCTransform(ArrayList<Face> faces);

    public ArrayList<Face> sortFacesByZ(ArrayList<Face> faces);

    public ArrayList<Face> castTo2D(ArrayList<Face> faces);
}
