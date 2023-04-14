package no.uib.inf101.sem2.gameEngine.view.textures;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Mipmapper class generates a mipmapping of textures, which are
 * lower-resolution versions of input images, for efficient rendering and memory usage.
 */
public class Mipmapper {
    private Map<String, ArrayList<BufferedImage>> mipmappedTextures;

    /**
     * Constructs a new Mipmapper instance and generates mipmaps for the given textures.
     *
     * @param textures A map of textures with keys as their identifiers.
     */
    public Mipmapper(Map<String, BufferedImage> textures){
        mipmappedTextures = new HashMap<>();
        mipmapTextures(textures);
    }

    /**
     * Generates mipmaps for the given textures and stores them in the mipmappedTextures map.
     *
     * @param textures A map of textures with keys as their identifiers.
     */
    private void mipmapTextures(Map<String, BufferedImage> textures){
        for(String key : textures.keySet()){
            mipmappedTextures.put(key, mipmapTexture(textures.get(key)));
        }
    }

    /**
     * Generates a list of mipmaps for the given texture.
     *
     * @param texture The input texture to generate mipmaps for.
     * @return A list of mipmapped versions of the input texture.
     */
    private ArrayList<BufferedImage> mipmapTexture(BufferedImage texture){
        ArrayList<BufferedImage> mipmaps = new ArrayList<>();
        int count = (int) (Math.log(Math.min(texture.getHeight(), texture.getWidth())) / Math.log(2));
        for(int i = 0; i < count; i++){
            mipmaps.add(divideResolutionOfImage(texture, (int) Math.pow(2, i)));
        }

        return mipmaps;
    }

     /**
     * Creates a scaled version of the input image with high-quality scaling.
     * @author ChatGPT
     *
     * @param image The input image to scale.
     * @param factor The inverse scaling factor to apply to the input image.
     * @return A new BufferedImage object representing the scaled image.
     */
    private BufferedImage divideResolutionOfImage(BufferedImage image, int factor){
        int targetWidth = image.getWidth() / factor;
        int targetHeight = image.getHeight() / factor;

        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, image.getType());
        Graphics2D graphics = scaledImage.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics.dispose();

        return scaledImage;
    }

    /**
     * Returns the most appropriate mipmapped texture for the given key and expanded dimensions.
     *
     * @param key The identifier of the texture in the mipmappedTextures map.
     * @param expandedWidth The full width of the texture after being expanded with the u-coordinates of the face.
     * @param expandedHeight The full height of the texture after being expanded with the v-coordinates of the face.
     * @return The mipmapped texture that best matches the target dimensions.
     */
    public BufferedImage getMipmappedTexture(String key, int expandedWidth, int expandedHeight){
        for(int i = this.mipmappedTextures.get(key).size() - 1; i >= 0; i--){
            BufferedImage texture = this.mipmappedTextures.get(key).get(i);
            if(texture.getWidth() >= expandedWidth && texture.getHeight() >= expandedHeight){
                return texture;
            }
        }
        return this.mipmappedTextures.get(key).get(0);
    }
}
