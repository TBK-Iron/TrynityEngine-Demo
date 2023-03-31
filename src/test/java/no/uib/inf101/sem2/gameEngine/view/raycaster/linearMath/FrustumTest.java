package no.uib.inf101.sem2.gameEngine.view.raycaster.linearMath;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Frustum;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Matrix;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.ViewProjectionMatrix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;


public class FrustumTest {

    @Test
    public void testClipFace(){
        
        double fov = Math.toRadians(60);
        double aspectRatio = 16.0 / 9.0;
        double near = 0.1;
        double far = 100.0;
        Rotation rotation = new Rotation(0, 0, 0);
        ViewProjectionMatrix vpMatrix = new ViewProjectionMatrix(fov, aspectRatio, near, far, rotation);

        Matrix viewProjectionMatrix = vpMatrix.getViewProjectionMatrix();

        
        Frustum frustum = new Frustum(viewProjectionMatrix);

        ArrayList<GridPosition> points1 = new ArrayList<>();
            points1.add(new Position3D(-1.0, -1.0, 5.0));
            points1.add(new Position3D(1.0, -1.0, 5.0));
            points1.add(new Position3D(1.0, 1.0, 5.0));
            points1.add(new Position3D(-1.0, 1.0, 5.0));
        
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
            desiredClipPoints.add(new Position3D(1.343, 0.57732, 1));
            desiredClipPoints.add(new Position3D(-1.343, 0.57732, 1));
            desiredClipPoints.add(new Position3D(-1.343, -0.57732, 1));
            desiredClipPoints.add(new Position3D(1.343, -0.57732, 1));

        Face desiredClippedFace = new Face(desiredClipPoints, null);
        
        assertEquals(clippedFace2, desiredClippedFace);
    }
}
