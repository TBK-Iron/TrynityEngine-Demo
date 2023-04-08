package no.uib.inf101.sem2.gameEngine.view.textures;

import com.aparapi.Kernel;

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

    public RasterizerKernel() {}

    @Override
    public void run() {
        int gid = getGlobalId();

        int xP = gid % this.faceWidth + this.dispX;
        int yP = gid / this.faceWidth + this.dispY;

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
            if(u > 1){
                u = u - (int) u;
            }
            
            float v = alpha * this.texCoords[1] + beta * this.texCoords[3] + gamma * this.texCoords[5];
            if(v > 1){
                v = v - (int) v;
            }

            //Get the color that corresponds to the texture coordinates
            int pixelColor = this.texture[(int) (u * this.textureWidth) + (int) (v * this.textureHeight) * this.textureWidth];

            this.output[xP + yP * totalWidth] = pixelColor;
        }
    }

    public void setTexture(int[] texture, int textureWidth, int textureHeight) {
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void setVertices(float[] vertices, float[] texCoords) {
        this.vertices = vertices;
        this.texCoords = texCoords;
    }

    public void setOutput(int[] output, int totalWidth) {
        this.output = output;
        this.totalWidth = totalWidth;
    }

    public void setWidth(int width) {
        this.faceWidth = width;
    }

    public void setDisp(int dispX, int dispY) {
        this.dispX = dispX;
        this.dispY = dispY;
    }

    public void setZBuffer(float[] zBuffer) {
        this.zBuffer = zBuffer;
    }
}