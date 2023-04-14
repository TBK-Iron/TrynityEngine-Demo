package no.uib.inf101.sem2.gameEngine.view.textures;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mipmapper {
    private Map<String, ArrayList<BufferedImage>> mipmappedTextures;

    public Mipmapper(Map<String, BufferedImage> textures){
        mipmappedTextures = new HashMap<>();
        mipmapTextures(textures);
    }

    private void mipmapTextures(Map<String, BufferedImage> textures){
        for(String key : textures.keySet()){
            mipmappedTextures.put(key, mipmapTexture(textures.get(key)));
        }
    }

    private ArrayList<BufferedImage> mipmapTexture(BufferedImage texture){
        ArrayList<BufferedImage> mipmaps = new ArrayList<>();
        int count = (int) (Math.log(Math.min(texture.getHeight(), texture.getWidth())) / Math.log(2));
        mipmaps.add(texture);
        for(int i = count - 1; i >= 0; i--){
            mipmaps.add(divideResolutionOfImage(mipmaps.get(mipmaps.size() - 1), 2));
        }

        return mipmaps;
    }

    private BufferedImage divideResolutionOfImage(BufferedImage image, int factor){
        int width = image.getWidth() / factor;
        int height = image.getHeight() / factor;
        BufferedImage newImage = new BufferedImage(width, height, image.getType());
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                newImage.setRGB(x, y, image.getRGB(x * factor, y * factor));
            }
        }
        return newImage;
    }

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
