package no.uib.inf101.sem2.gameEngine.model.shape;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import no.uib.inf101.sem2.gameEngine.model.collision.BoundingSphere;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.model.shape.positionData.Position3D;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

/**
 * The Shape3D class represents a 3D shape in the game engine.
 * It is defined by its faces, anchor position, and rotation.
 * The class provides methods for parsing a 3D shape from a file,
 * getting its bounding sphere, and calculating its distance to the origin.
 */
public class Shape3D {
    protected ArrayList<Face> faces;
    protected GridPosition anchoredPos;
    protected RelativeRotation rotation;

    /**
     * Constructs a new Shape3D from the provided ShapeData.
     *
     * @param shapeData The ShapeData containing the position, rotation, and file to parse.
     */
    public Shape3D(ShapeData shapeData){
        this.faces = new ArrayList<>();
        this.anchoredPos = shapeData.position();
        this.rotation = shapeData.rotation();
        this.faces = parseTrymFile(shapeData.file());
    }

    /**
     * Constructs a new Shape3D with the given list of faces.
     * This constructor should only be used when the shape is already in world space.
     *
     * @param faces A list of faces that make up the 3D shape.
     */
    public Shape3D(ArrayList<Face> faces){
        this.faces = faces;
        this.anchoredPos = new Position3D(0, 0, 0);
        this.rotation = new RelativeRotation(0, 0);
    }

    /**
     * Parses a .trym file to create a list of Face objects that represent the 3D shape.
     *
     * @param file The .trym file to parse.
     * @return A list of Face objects representing the 3D shape.
     */
    private static ArrayList<Face> parseTrymFile(File file) {
        validateFileType(file);

        try (Scanner myReader = new Scanner(file, "UTF-8")) {
            ArrayList<Face> faces = new ArrayList<>();
            int line = 0;
            while (myReader.hasNextLine()) {
                line++;
                String[] face = myReader.nextLine().split(":");
                if (face.length != 0) {
                    ArrayList<GridPosition> positions = parsePoints(face, file, line);
                    FaceTexture texture = parseFaceTexture(face, file, line, positions);
                    faces.add(new Face(positions, texture));
                }
            }
            return faces;
        } catch (FileNotFoundException e) {
            System.out.println("file not found: " + e.getMessage());
        }
        return null;
    }

    private static void validateFileType(File file) {
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!fileType.equals("trym")) {
            throw new UnsupportedOperationException("Files of filetype " + fileType + " are not supported. Only .trym files are parsable");
        }
    }

    /**
     * Parses the points of a face from the .trym file.
     *
     * @param points A string array containing the point data.
     * @return A list of GridPosition objects representing the points of a face.
     * @throws UnsupportedOperationException If the points have an invalid format.
     */
    private static ArrayList<GridPosition> parsePoints(String[] face, File file, int line) {
        String[] points = face[0].split(";");
        ArrayList<GridPosition> positions = new ArrayList<>();
        for (String point : points) {
            float[] dPoint = new float[3];
            String[] sPoint = point.replace("(", "").replace(")", "").split(",");
            if (sPoint.length != 3) {
                throw new UnsupportedOperationException("Points must be 3 values long (x, y, z), error in file " + file + ", at line " + line);
            }
            for (int j = 0; j < 3; j++) {
                dPoint[j] = Float.parseFloat(sPoint[j]);
            }
            positions.add(new Position3D(dPoint[0], dPoint[1], dPoint[2]));
        }
        return positions;
    }

    /**
     * Parses the texture of a face from the .trym file.
     *
     * @param face A string array containing the texture data.
     * @param positions A list of GridPosition objects representing the points of a face.
     * @param file The .trym file being parsed.
     * @param line The current line number in the .trym file.
     * @return A FaceTexture object representing the texture of a face.
     * @throws UnsupportedOperationException If the texture has an invalid format.
     */
    private static FaceTexture parseFaceTexture(String[] face, File file, int line, ArrayList<GridPosition> positions) {
        FaceTexture texture;
        if (face[1].equals("COLOR")) {
            texture = createColorTexture(face, positions);
        } else if (face[1].equals("TEXTURE")) {
            texture = createTexture(face, file, line, positions);
        } else {
            throw new UnsupportedOperationException("Face must be either COLOR or TEXTURE, error in file " + file + ", at line " + line);
        }
        return texture;
    }

    private static FaceTexture createColorTexture(String[] face, ArrayList<GridPosition> positions) {
        String colorTexture = face[2];
        float[] uv = new float[positions.size() * 2];
        for (int i = 0; i < uv.length; i++) {
            uv[i] = 0.5f;
        }
        return new FaceTexture(colorTexture, uv);
    }

    private static FaceTexture createTexture(String[] face, File file, int line, ArrayList<GridPosition> positions) {
        String textureKey = face[2];
        String[] uv = face[3].split(";");
        float[] fuv = new float[uv.length * 2];
        for (int i = 0; i < uv.length; i++) {
            String[] uvi = uv[i].replace("(", "").replace(")", "").split(",");
            fuv[i * 2] = Float.parseFloat(uvi[0]);
            fuv[i * 2 + 1] = Float.parseFloat(uvi[1]);
        }
        if (positions.size() * 2 != fuv.length) {
            throw new UnsupportedOperationException("Texture must have the same number of uv values as points, error in file " + file + ", at line " + line);
        }
        return new FaceTexture(textureKey, fuv);
    }

    /**
     * Returns the relative rotation of the 3D shape.
     *
     * @return The relative rotation of the 3D shape.
     */
    public RelativeRotation getRotation(){
        return this.rotation;
    }

    /**
     * Returns the anchor position of the 3D shape.
     *
     * @return The anchor position of the 3D shape.
     */
    public GridPosition getPosition(){
        return this.anchoredPos;
    }

    /**
     * Calculates and returns the bounding sphere of the 3D shape.
     *
     * @return The bounding sphere of the 3D shape.
     */
    public BoundingSphere getBoundingSphere(){
        float x = 0;
        float y = 0;
        float z = 0;

        int pointCount = 0;
        for(Face face : this.faces){
            for(GridPosition point : face.getPoints()){
                x += point.x();
                y += point.y();
                z += point.z();
                pointCount++;
            }
        }
        x /= pointCount;
        y /= pointCount;
        z /= pointCount;
        GridPosition center = new Position3D(x, y, z);
        float radius = 0;
        for(Face face : this.faces){
            for(GridPosition point : face.getPoints()){
                float distance = (float) Math.sqrt(Math.pow(point.x() - center.x(), 2) + Math.pow(point.y() - center.y(), 2) + Math.pow(point.z() - center.z(), 2));
                if(distance > radius){
                    radius = distance;
                }
            }
        }
        return new BoundingSphere(center, radius);
    }

    /**
     * Returns a list of faces that make up the 3D shape.
     *
     * @return A list of faces that make up the 3D shape.
     */
    public ArrayList<Face> getFaces(){
        return this.faces;
    }

    

    /**
     * Calculates and returns the squared distance from the origin to the closest point in the 3D shape.
     * The reason it is not the real distance is because it is faster to not calculate a square root.
     *
     * @return The squared distance from the origin to the closest point in the 3D shape.
     */
    public float getDistanceToOriginSquared(){
        float minDist = Float.MAX_VALUE;
        for(Face face : this.faces){
            GridPosition point = face.getPointClosestToOrigin();
            float dist = (float) Math.pow(point.x(), 2) + (float) Math.pow(point.y(), 2) + (float) Math.pow(point.z(), 2);
            if(dist < minDist){
                minDist = dist;
            }
        }

        return minDist;
    }
}
