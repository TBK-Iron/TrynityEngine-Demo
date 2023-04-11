package no.uib.inf101.sem2.game.model.resourceLoaders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class TextureLoader {
    Map<String, BufferedImage> textures = new HashMap<String, BufferedImage>();

    BufferedImage logoImage;

    public TextureLoader(){
        try {
            textures.put("trollface", ImageIO.read(new File("src/main/resources/textures/trollface.png")));
            textures.put("checkered", ImageIO.read(new File("src/main/resources/textures/checkered.png")));
            textures.put("grass", ImageIO.read(new File("src/main/resources/textures/grass.jpg")));
            textures.put("bark", ImageIO.read(new File("src/main/resources/textures/bark.jpg")));
            textures.put("leaves", ImageIO.read(new File("src/main/resources/textures/leaves.jpg")));
            textures.put("brick", ImageIO.read(new File("src/main/resources/textures/brick.jpg")));

            logoImage = ImageIO.read(new File("src/main/resources/Trynity_Logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, BufferedImage> getTextures(){
        return textures;
    }

    public BufferedImage getLogo(){
        return this.logoImage;
    }
}
