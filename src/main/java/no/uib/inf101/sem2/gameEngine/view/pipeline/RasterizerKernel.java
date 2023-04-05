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

    public RasterizerKernel(float[] vertices, float[] texCoords, int[] texture, int textureWidth, int textureHeight, int outputWidth, int outputHeight) {
        this.vertices = vertices;
        this.texCoords = texCoords;
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
        this.output = new int[outputWidth * outputHeight];
    }

    @Override
    public void run() {
        int gid = getGlobalId();

        int x = gid % this.outputWidth;
        int y = gid / this.outputWidth;


        
    }
    

}
