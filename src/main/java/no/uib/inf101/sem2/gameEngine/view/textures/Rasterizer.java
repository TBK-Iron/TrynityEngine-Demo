package no.uib.inf101.sem2.gameEngine.view.textures;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.aparapi.Kernel;
import com.aparapi.Range;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.model.shape.Face;
import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Rasterizer {

    private static final int KERNEL_POOL_SIZE = 128; // Adjust according to the number of threads you want to use
    private final RasterizerKernel[] kernelPool = new RasterizerKernel[KERNEL_POOL_SIZE];
    Map<String, BufferedImage> textures;
    Config config;



    public Rasterizer(Map<String, BufferedImage> textures, Config config){
        this.textures = textures;
        this.config = config;

        for(int i = 0; i < KERNEL_POOL_SIZE; i++){
            kernelPool[i] = new RasterizerKernel();
        }
    }

    //TODO: optimize this method to make each face only use as many pixels as it actually needs.
    //TODO: implement a z-buffer
    public BufferedImage rastarize(ArrayList<Face> faces) {

        long startTime = System.nanoTime();

        int[] rastarizedImageData = new int[this.config.screenWidth() * this.config.screenHeight()];
        Arrays.fill(rastarizedImageData, this.config.skyboxColor());

        float[] zBuffer = new float[this.config.screenWidth() * this.config.screenHeight()];
        Arrays.fill(zBuffer, Float.MAX_VALUE);

        for(RasterizerKernel kernel : kernelPool){
            kernel.setOutput(rastarizedImageData, this.config.screenWidth());
            kernel.setZBuffer(zBuffer);
        }
        

        //System.out.println("\nRASTARIZING:\n");

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
                //Check if face is textured or just has a color
                if(face.getTexture().textureKey().startsWith("#")){
                    Color color = Color.decode(face.getTexture().textureKey().trim());

                    textureData = new int[]{color.getRGB()};
                    textureWidth = 1;
                    textureHeight = 1;
                } else {
                    BufferedImage texture = this.textures.get(face.getTexture().textureKey());
                    textureWidth = texture.getWidth();
                    textureHeight = texture.getHeight();
                    textureData = texture.getRGB(0, 0, textureWidth, textureHeight, null, 0, textureWidth);
                }

                

                Vector[] AABB = face.getAABB_xy();

                int dispX = (int) AABB[0].get(0);
                int dispY = (int) AABB[0].get(1);

                float[] vertices = new float[face.getPoints().size() * 3];
                for(int i = 0; i < face.getPoints().size(); i++){
                    vertices[i*3] = face.getPoints().get(i).x();
                    vertices[i*3 + 1] = face.getPoints().get(i).y();
                    vertices[i*3 + 2] = face.getPoints().get(i).z();
                }

                int faceWidth = (int) ((AABB[1].get(0) - AABB[0].get(0)));
                int faceHeight = (int) ((AABB[1].get(1) - AABB[0].get(1)));

                kernel.setWidth(faceWidth);

                //System.out.println("dispX" + dispX + " dispY: " + dispY + " faceWidth: " + faceWidth + " faceHeight: " + faceHeight);
                //System.out.println("x1: " + vertices[0] + " y1: " + vertices[1] + " x2: " + vertices[2] + " y2: " + vertices[3] + " x3: " + vertices[4] + " y3: " + vertices[5]);

                kernel.setDisp(dispX, dispY);
                kernel.setTexture(textureData, textureWidth, textureHeight);
                kernel.setVertices(vertices, face.getTexture().uvMap());

                float[] uv = face.getTexture().uvMap();

                /* System.out.println("u1: " + uv[0] + " v1: " + uv[1] + " u2: " + uv[2] + " v2: " + uv[3] + " u3: " + uv[4] + " v3: " + uv[5]);
                System.out.println("x1: " + vertices[0] + " y1: " + vertices[1] + " x2: " + vertices[2] + " y2: " + vertices[3] + " x3: " + vertices[4] + " y3: " + vertices[5]);
                System.out.println(); */
        
                int localSize = 256;

                //Taking the closest multiple of localSize to the number of pixels to process, this avoids an error which makes the kernel not run on the gpu.
                Range range = Range.create((int) Math.ceil((faceWidth * faceHeight)/localSize)*localSize, localSize); 
                
                //System.out.println("Global size: " + globalSize + " Local size: " + localSize);
                //System.out.println("Vertices: " + vertices.length + " UVs: " + face.getTexture().uvMap().length + " Texture data: " + textureData.length + " Rastarized face: " + rastarizedFace.length);
                
                kernel.execute(range);

                kernel.get(rastarizedImageData);
                
                return null;
            });
        }
        //System.out.println("\n\n\n");

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

        //kernel.get(rastarizedImageData);
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        //System.out.print("Rastarization time: " + duration + "ms, facecount: " + faces.size() + ", ");

        BufferedImage rastarizedImage = new BufferedImage(this.config.screenWidth(), this.config.screenHeight(), BufferedImage.TYPE_INT_ARGB);
        rastarizedImage.setRGB(0, 0, this.config.screenWidth(), this.config.screenHeight(), rastarizedImageData, 0, this.config.screenWidth());

        return rastarizedImage;
    }
}
