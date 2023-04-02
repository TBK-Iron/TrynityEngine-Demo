package no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Matrix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;


public class FrustumTest {

    @Test
    public void testClipFace(){
        
        /* float fov = (float) Math.toRadians(60);
        float aspectRatio = 16f / 9f;
        float near = 0.1f;
        float far = 100f;
        Rotation rotation = new Rotation(0, 0, 0);
        ViewProjectionMatrix vpMatrix = new ViewProjectionMatrix(fov, aspectRatio, near, far, rotation);

        Matrix viewProjectionMatrix = vpMatrix.getViewProjectionMatrix();

        
        Frustum frustum = new Frustum(viewProjectionMatrix, near, far);

        ArrayList<GridPosition> points1 = new ArrayList<>();
            points1.add(new Position3D(-1.0f, -1.0f, 5.0f));
            points1.add(new Position3D(1.0f, -1.0f, 5.0f));
            points1.add(new Position3D(1.0f, 1.0f, 5.0f));
            points1.add(new Position3D(-1.0f, 1.0f, 5.0f));
        
        Face inputFace1 = new Face(points1, Color.BLACK);
        Face clippedFace1 = frustum.clipFace(inputFace1);

        //Test where the face shouldn't be clipped
        assertEquals(inputFace1, clippedFace1);

        ArrayList<GridPosition> points2 = new ArrayList<>();
            points2.add(new Position3D(2, 1, 1));
            points2.add(new Position3D(-2, 1, 1));
            points2.add(new Position3D(-2, -1, 1));
            points2.add(new Position3D(2, -1, 1));


        Face inputFace2 = new Face(points2, null);
        Face clippedFace2 = frustum.clipFace(inputFace2);

        ArrayList<GridPosition> desiredClipPoints = new ArrayList<>();
            desiredClipPoints.add(new Position3D(1.343f, 0.57732f, 1f));
            desiredClipPoints.add(new Position3D(-1.343f, 0.57732f, 1f));
            desiredClipPoints.add(new Position3D(-1.343f, -0.57732f, 1f));
            desiredClipPoints.add(new Position3D(1.343f, -0.57732f, 1f));

        Face desiredClippedFace = new Face(desiredClipPoints, null);
        
        //Test where the all points and sides in face are outside the frustum, and the result should be a square that are in all the corners of the
        //frustum.
        assertEquals(clippedFace2, desiredClippedFace); */
    }
}
