package no.uib.inf101.sem2.gameEngine.view.raycaster.linearMath;

import no.uib.inf101.sem2.gameEngine.model.shape.Position3D;
import no.uib.inf101.sem2.gameEngine.view.raycaster.RelativeRotation;
import no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {
    
    @Test
    public void testGetVectorRotation(){
        Vector v1 = new Vector(new double[] {0, 0, 1});
        RelativeRotation r1 = new RelativeRotation(0.0, 0.0);
        assertEquals(Vector.getVectorRotation(v1), r1);

        Vector v2 = new Vector(new double[] {0, 1, 0});
        RelativeRotation r2 = new RelativeRotation(Math.PI/2, 0.0);
        assertEquals(Vector.getVectorRotation(v2), r2);

        Vector v3 = new Vector(new double[] {0, 1, -1});
        RelativeRotation r3 = new RelativeRotation(Math.PI/4, Math.PI);
        assertEquals(Vector.getVectorRotation(v3), r3);

    }
}
