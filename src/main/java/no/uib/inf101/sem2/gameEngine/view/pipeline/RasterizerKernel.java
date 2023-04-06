package no.uib.inf101.sem2.gameEngine.view.pipeline;

import com.aparapi.Kernel;

public class RasterizerKernel extends Kernel{

    private final float[] vertices; // Triangle vertices (xA, yA, xB, yB, xC, yC)
    private final float[] texCoords; // Texture coordinates (uA, vA, uB, vB, uC, vC)
    private final int[] texture; // Texture image as a 1D int array (width * height)
    private final int[] output; // Output color buffer as a 1D int array (width * height)

    private final int textureWidth;
    private final int textureHeight;
    private final int outputWidth;
    private final int outputHeight;

    public RasterizerKernel(float[] vertices, float[] texCoords, int[] texture, int[] output, int textureWidth, int textureHeight, int outputWidth, int outputHeight) {
        this.vertices = vertices;
        this.texCoords = texCoords;
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
        this.output = output;
    }

    @Override
    public void run() {
        int gid = getGlobalId();

        int xP = gid % this.outputWidth;
        int yP = gid / this.outputWidth;

        // Calculate barycentric coordinates
        float xA = this.vertices[0];
        float yA = this.vertices[1];
        float xB = this.vertices[2];
        float yB = this.vertices[3];
        float xC = this.vertices[4];
        float yC = this.vertices[5];

        float alpha = ((yB - yC) * (xP - xC) + (xC - xB) * (yP - yC)) / ((yB - yC) * (xA - xC) + (xC - xB) * (yA - yC));
        float beta  = ((yC - yA) * (xP - xC) + (xA - xC) * (yP - yC)) / ((yB - yC) * (xA - xC) + (xC - xB) * (yA - yC));
        float gamma = 1 - alpha - beta;

        // Check if point is inside triangle
        if (alpha >= 0 && beta >= 0 && gamma >= 0) {
            //Calculate texture at the point, these are the barycentric coordinates for the texture in normalized space
            float u = alpha * this.texCoords[0] + beta * this.texCoords[2] + gamma * this.texCoords[4];
            float v = alpha * this.texCoords[1] + beta * this.texCoords[3] + gamma * this.texCoords[5];

            //Get the color that corresponds to the texture coordinates
            int pixelColor = this.texture[(int) (u * this.textureWidth) + (int) (v * this.textureHeight) * this.textureWidth];

            this.output[gid] = pixelColor;
        }
    }
}
