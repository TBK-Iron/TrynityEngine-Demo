package no.uib.inf101.sem2.gameEngine.view.textures;

import com.aparapi.Kernel;
/**
 * RasterizerKernel is a class that extends the Kernel class and implements
 * rasterization of triangles with texture mapping using Aparapi.
 * The class calculates the color of each pixel in the output buffer based on
 * the given triangle vertices, texture coordinates, and texture image.
 */
public class RasterizerKernel extends Kernel{

    private float[] vertices; // Triangle vertices (xA, yA, xB, yB, xC, yC)
    private float[] texCoords; // Texture coordinates (uA, vA, uB, vB, uC, vC)
    private int[] texture; // Texture image as a 1D int array (width * height)
    private int[] output; // Output color buffer as a 1D int array (width * height)
    private float[] zBuffer; // Z buffer as a 1D float array (width * height)

    private int textureWidth;
    private int textureHeight;
    private int faceWidth;
    private int totalWidth;

    private int dispX;
    private int dispY;

    @Override
    public void run() {
        int gid = getGlobalId();

        int xP = gid % this.faceWidth + this.dispX;
        int yP = gid / this.faceWidth + this.dispY;

        // Calculate barycentric coordinates
        float xA = this.vertices[0];
        float yA = this.vertices[1];
        
        float xB = this.vertices[3];
        float yB = this.vertices[4];
        
        float xC = this.vertices[6];
        float yC = this.vertices[7];
        

        float areaABC = ((yB - yC) * (xA - xC) + (xC - xB) * (yA - yC));

        float alpha = ((yB - yC) * (xP - xC) + (xC - xB) * (yP - yC)) / areaABC;
        float beta  = ((yC - yA) * (xP - xC) + (xA - xC) * (yP - yC)) / areaABC;
        float gamma = 1 - alpha - beta;

        // Check if point is inside triangle
        if (alpha >= 0 && beta >= 0 && gamma >= 0) {

            float zA_inv = 1 / this.vertices[2];
            float zB_inv = 1 / this.vertices[5];
            float zC_inv = 1 / this.vertices[8];

            float zP_inv = alpha * zA_inv + beta * zB_inv + gamma * zC_inv;

            int pixelNr = xP + yP * totalWidth;

            if(1/zP_inv < this.zBuffer[pixelNr]){
                //Correct the barycentric coordinates for perspective
                this.zBuffer[pixelNr] = 1/zP_inv;

                alpha = alpha * zA_inv / zP_inv;
                beta = beta * zB_inv / zP_inv;
                gamma = gamma * zC_inv / zP_inv;

                //Calculate texture at the point, these are the barycentric coordinates for the texture in normalized space
                float u = alpha * this.texCoords[0] + beta * this.texCoords[2] + gamma * this.texCoords[4];
                u = u - (int) u;

                float v = alpha * this.texCoords[1] + beta * this.texCoords[3] + gamma * this.texCoords[5];
                v = v - (int) v;

                //Get the color that corresponds to the texture coordinates
                int textureX = (int) (u * (this.textureWidth - 1));
                int textureY = (int) (v * (this.textureHeight - 1));
                int pixelColor = this.texture[textureX + textureY * this.textureWidth];

                this.output[pixelNr] = pixelColor;
            }

            
        }
    }

    /**
     * Sets the texture image data, width, and height.
     *
     * @param texture      Texture image as a 1D int array (width * height).
     * @param textureWidth The width of the texture image.
     * @param textureHeight The height of the texture image.
     */
    public void setTexture(int[] texture, int textureWidth, int textureHeight) {
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    /**
     * Sets the triangle vertices and texture coordinates.
     *
     * @param vertices  Triangle vertices (xA, yA, xB, yB, xC, yC) as a float array.
     * @param texCoords Texture coordinates (uA, vA, uB, vB, uC, vC) as a float array.
     */
    public void setVertices(float[] vertices, float[] texCoords) {
        this.vertices = vertices;
        this.texCoords = texCoords;
    }

    /**
     * Sets the output color buffer and the total width of the output buffer.
     *
     * @param output     Output color buffer as a 1D int array (width * height).
     * @param totalWidth The total width of the output buffer.
     */
    public void setOutput(int[] output, int totalWidth) {
        this.output = output;
        this.totalWidth = totalWidth;
    }

    /**
     * Sets the width of the rasterized face.
     *
     * @param width The width of the face.
     */
    public void setWidth(int width) {
        this.faceWidth = width;
    }

    /**
     * Sets the displacement of the rasterized face on the x and y axes.
     *
     * @param dispX Displacement on the x-axis.
     * @param dispY Displacement on the y-axis.
     */
    public void setDisp(int dispX, int dispY) {
        this.dispX = dispX;
        this.dispY = dispY;
    }

    /**
     * Sets the z-buffer as a 1D float array (width * height).
     *
     * @param zBuffer Z buffer as a 1D float array.
     */
    public void setZBuffer(float[] zBuffer) {
        this.zBuffer = zBuffer;
    }
}
