package no.uib.inf101.sem2.gameEngine.view.textures;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.aparapi.Range;
import com.aparapi.Kernel.EXECUTION_MODE;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.view.pipeline.linearMath.Vector;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Rasterizer is a class that handles rasterization of 3D objects in the game engine.
 * It takes a list of faces and rasterizes them into a BufferedImage, handling
 * texture mapping, mipmapping, and multithreading for performance.
 */
public class Rasterizer {

    private static int KERNEL_POOL_SIZE = 256; 
    private final RasterizerKernel[] kernelPool = new RasterizerKernel[KERNEL_POOL_SIZE];
    private static EXECUTION_MODE MODE = EXECUTION_MODE.JTP;
    private static int LOCAL_SIZE = 256;
    Mipmapper mipmapper;
    Config config;

    /**
     * Constructs a Rasterizer with a map of textures and a configuration object.
     *
     * @param textures A map containing texture keys and their corresponding BufferedImages.
     * @param config   A configuration object containing game engine settings.
     */
    public Rasterizer(Map<String, BufferedImage> textures, Config config){
        this.mipmapper = new Mipmapper(textures);
        this.config = config;

        for(int i = 0; i < KERNEL_POOL_SIZE; i++){
            kernelPool[i] = new RasterizerKernel();
        }
    }

    /**
     * Rasterizes a list of faces into a BufferedImage.
     *
     * @param faces A list of faces to be rasterized.
     * @return A BufferedImage containing the rasterized faces.
     */
    public BufferedImage rastarize(ArrayList<Face> faces) {

        int[] rastarizedImageData = new int[this.config.screenWidth() * this.config.screenHeight()];
        Arrays.fill(rastarizedImageData, this.config.skyboxColor());

        float[] zBuffer = new float[this.config.screenWidth() * this.config.screenHeight()];
        Arrays.fill(zBuffer, Float.MAX_VALUE);

        for(RasterizerKernel kernel : kernelPool){
            kernel.setOutput(rastarizedImageData, this.config.screenWidth());
            kernel.setZBuffer(zBuffer);
        }
        

        int numThreads = Runtime.getRuntime().availableProcessors(); // Get the number of available CPU cores
        ExecutorService executor = Executors.newFixedThreadPool(numThreads); // Create a fixed thread pool

        ArrayList<Callable<Void>> tasks = new ArrayList<>();

        for(int j = 0; j < faces.size(); j++){
            final Face face = faces.get(j);
            final RasterizerKernel kernel = kernelPool[j % KERNEL_POOL_SIZE];
            tasks.add(() -> {
                //System.out.println(face);
                int[] textureData;
                int textureWidth;
                int textureHeight;

                Vector[] AABB = face.getAABB_xy();

                int dispX = (int) AABB[0].get(0) - 3;
                int dispY = (int) AABB[0].get(1) - 3;

                int faceWidth = (int) ((AABB[1].get(0) - AABB[0].get(0))) + 6;
                int faceHeight = (int) ((AABB[1].get(1) - AABB[0].get(1))) + 6;

                //Check if face is textured or just has a color
                if(face.getTexture().textureKey().startsWith("#")){
                    Color color = Color.decode(face.getTexture().textureKey().trim());

                    textureData = new int[]{color.getRGB()};
                    textureWidth = 1;
                    textureHeight = 1;
                } else {

                    float minU = 1;
                    float maxU = 0;
                    float minV = 1;
                    float maxV = 0;

                    float[] uvMap = face.getTexture().uvMap();

                    for(int i = 0; i < face.getTexture().uvMap().length/2; i++){
                        if(uvMap[i*2] < minU){
                            minU = uvMap[i*2];
                        }
                        if(uvMap[i*2] > maxU){
                            maxU = uvMap[i*2];
                        }

                        if(uvMap[i*2 + 1] < minV){
                            minV = uvMap[i*2 + 1];
                        }
                        if(uvMap[i*2 + 1] > maxV){
                            maxV = uvMap[i*2 + 1];
                        }
                    }

                    float uvWidth = maxU - minU;
                    float uvHeight = maxV - minV;
                 
                    int totalWidth = (int) (faceWidth / uvWidth);
                    int totalHeight = (int) (faceHeight / uvHeight);

                    BufferedImage texture = this.mipmapper.getMipmappedTexture(face.getTexture().textureKey(), totalWidth, totalHeight);
                    textureWidth = texture.getWidth();
                    textureHeight = texture.getHeight();
                    textureData = texture.getRGB(0, 0, textureWidth, textureHeight, null, 0, textureWidth);
                }


                float[] vertices = new float[face.getPoints().size() * 3];
                for(int i = 0; i < face.getPoints().size(); i++){
                    vertices[i*3] = face.getPoints().get(i).x();
                    vertices[i*3 + 1] = face.getPoints().get(i).y();
                    vertices[i*3 + 2] = face.getPoints().get(i).z();
                }

                kernel.setWidth(faceWidth);

                kernel.setDisp(dispX, dispY);
                kernel.setTexture(textureData, textureWidth, textureHeight);
                kernel.setVertices(vertices, face.getTexture().uvMap());
        
                kernel.setExecutionMode(Rasterizer.MODE);

                int localSize = Rasterizer.LOCAL_SIZE;
                //Taking the closest multiple of localSize to the number of pixels to process, this avoids an error which makes the kernel not run on the gpu.
                int globalSize = (int) Math.ceil((double) faceWidth * faceHeight / localSize) * localSize;
                Range range = Range.create(Math.max(globalSize, localSize), localSize);

                
                kernel.execute(range);

                kernel.get(rastarizedImageData);
                
                return null;
            });
        }

        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            System.err.println("Rastarization tasks were interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Preserve the interrupt status
        }

        executor.shutdown(); // Shutdown the executor when all tasks have been submitted
        try {
            // Wait for all tasks to complete, with a timeout
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BufferedImage rastarizedImage = new BufferedImage(this.config.screenWidth(), this.config.screenHeight(), BufferedImage.TYPE_INT_ARGB);
        rastarizedImage.setRGB(0, 0, this.config.screenWidth(), this.config.screenHeight(), rastarizedImageData, 0, this.config.screenWidth());

        return rastarizedImage;
    }
}
