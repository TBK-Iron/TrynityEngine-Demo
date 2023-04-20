package no.uib.inf101.sem2.gameEngine.view.pipeline;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.model.Camera;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.model.shape.Shape3D;

public interface IPipeline {
    /**
     * Transforms the input shapes into world space by applying their position and rotation.
     *
     * @param shapes The input shapes to be transformed.
     * @return A new list of transformed shapes in world space.
     */
    ArrayList<Shape3D> worldTransform(ArrayList<Shape3D> shapes);

    /**
     * Transforms the input shapes based on the provided camera's position and rotation.
     *
     * @param shapes The input shapes to be transformed.
     * @param camera The camera providing the view transformation.
     * @return A new list of transformed shapes in camera space.
     */
    ArrayList<Shape3D> cameraTransform(ArrayList<Shape3D> shapes, Camera camera);

    /**
     * Performs backface culling and view frustum culling on the input shapes.
     *
     * @param shapes The input shapes to be culled.
     * @return A new list of culled shapes.
     */
    ArrayList<Shape3D> cull(ArrayList<Shape3D> shapes);

    /**
     * Clips the faces of the input shapes based on the view frustum.
     *
     * @param shapes The input shapes to be clipped.
     * @return A new list of clipped faces.
     */
    ArrayList<Face> clip(ArrayList<Shape3D> shapes);

    /**
     * Transforms the input faces using the projection transformation.
     *
     * @param faces The input faces to be transformed.
     * @return A new list of transformed faces in clip space.
     */
    ArrayList<Face> projectTransform(ArrayList<Face> faces);

    /**
     * Transforms the input faces from clip space to normalized device coordinates (NDC) space.
     *
     * @param faces The input faces to be transformed.
     * @return A new list of transformed faces in NDC space.
     */
    ArrayList<Face> NDCTransform(ArrayList<Face> faces);

    /**
     * Casts the input faces from 3D space to 2D space.
     *
     * @param faces The input faces to be casted.
     * @return A new list of faces casted to 2D space.
     */
    ArrayList<Face> castTo2D(ArrayList<Face> faces);

    /**
     * Rasterizes the input faces using the provided textures.
     *
     * @param faces The input faces to be rasterized.
     * @param textures The textures to be applied to the faces.
     * @return A BufferedImage representing the rasterized scene.
     */
    BufferedImage rastarizeFaces(ArrayList<Face> faces);

}
