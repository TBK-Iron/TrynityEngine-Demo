package no.uib.inf101.sem2.gameEngine.view.pipeline;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import no.uib.inf101.sem2.gameEngine.model.shape.*;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.*;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Projection;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.RotateTransform;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.Transformation;
import no.uib.inf101.sem2.gameEngine.view.pipeline.transformations.TranslateTransform;

class CullingTest {
    private ArrayList<Shape3D> shapes;
    private Frustum cameraFrustum;

    @BeforeEach
    void setUp() {
        shapes = new ArrayList<>();

        // Add some 3D shapes to the list
        shapes.add(new Shape3D(new ShapeData(new Position3D(0, 0, 5), new RelativeRotation(0, (float) Math.PI/4), new File("src/main/resources/shapes/brick_cube.trym"))));
        shapes.add(new Shape3D(new ShapeData(new Position3D(0, 0, -5), new RelativeRotation(0, 0), new File("src/main/resources/shapes/brick_cube.trym"))));

        // Create a frustum using a projection matrix, near and far clipping distances
        float ar = 16f / 9f;
        float fov = (float) Math.toRadians(75);
        float near = 0.1f;
        float far = 100.0f;

        Matrix projectionMatrix = new Projection(fov, ar, near, far).getMatrix();
        cameraFrustum = new Frustum(projectionMatrix, near, far);

        ArrayList<Shape3D> worldSpaceShapes = new ArrayList<>();

        for(int i = 0; i < shapes.size(); i++){
            Transformation translateTransform = new TranslateTransform(new Vector((Position3D) shapes.get(i).getPosition()));
            Transformation rotateTransform = new RotateTransform(shapes.get(i).getRotation(), false);

            ArrayList<Face> transformedFaces = new ArrayList<>();
            for(Face face : shapes.get(i).getFaces()){
                Face transformedFace = rotateTransform.transform(face);
                transformedFace = translateTransform.transform(transformedFace);
                transformedFaces.add(transformedFace);
            }
            worldSpaceShapes.add(new Shape3D(transformedFaces));
        }

        shapes = worldSpaceShapes;
    }

    @Test
    void testBackfaceCull() {
        ArrayList<Shape3D> notCulledShapes = Culling.backfaceCull(shapes);
        
        int facesLeft = 0;
        for(Shape3D shape : notCulledShapes){
            facesLeft += shape.getFaces().size();
        }

        assertEquals(3, facesLeft);
    }

    @Test
    void testViewfrustrumCull() {
        ArrayList<Shape3D> culledShapes = Culling.viewfrustrumCull(shapes, cameraFrustum);
        
        assertEquals(1, culledShapes.size());
    }
}