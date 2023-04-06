package no.uib.inf101.sem2.game.model.resourceLoaders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class TextureLoader {
    Map<String, BufferedImage> textures = new HashMap<String, BufferedImage>();

    public TextureLoader(){
        try {
            textures.put("trollface", ImageIO.read(new File("src/main/resources/textures/trollface.png")));




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, BufferedImage> getTextures(){
        return textures;
    }
}
