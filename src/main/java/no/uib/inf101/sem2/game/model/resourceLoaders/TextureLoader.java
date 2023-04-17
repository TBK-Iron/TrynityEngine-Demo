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
    BufferedImage menuBackgroundImage;

    public TextureLoader(){
        try {
            textures.put("trollface", ImageIO.read(new File("src/main/resources/textures/trollface.png")));
            textures.put("checkered", ImageIO.read(new File("src/main/resources/textures/checkered.png")));
            textures.put("grass", ImageIO.read(new File("src/main/resources/textures/grass.jpg")));
            textures.put("bark", ImageIO.read(new File("src/main/resources/textures/bark.jpg")));
            textures.put("leaves", ImageIO.read(new File("src/main/resources/textures/leaves.jpg")));
            textures.put("brick", ImageIO.read(new File("src/main/resources/textures/brick.jpg")));
            textures.put("stone_floor", ImageIO.read(new File("src/main/resources/textures/stone_floor.jpg")));
            textures.put("stone_wall", ImageIO.read(new File("src/main/resources/textures/stone_wall.jpg")));
            textures.put("metal_door", ImageIO.read(new File("src/main/resources/textures/metal_door.jpg")));

            textures.put("zombie_head", ImageIO.read(new File("src/main/resources/textures/zombie_head.png")));
            textures.put("zombie_torso", ImageIO.read(new File("src/main/resources/textures/zombie_torso.png")));
            textures.put("zombie_appendages", ImageIO.read(new File("src/main/resources/textures/zombie_appendages.png")));
            textures.put("trym", ImageIO.read(new File("src/main/resources/textures/trymImage.jpg")));
            
            /* textures.put("trollface", ImageIO.read(new File("src/main/resources/textures/trymImage.jpg")));
            textures.put("checkered", ImageIO.read(new File("src/main/resources/textures/trymImage.jpg")));
            textures.put("grass", ImageIO.read(new File("src/main/resources/textures/trymImage.jpg")));
            textures.put("bark", ImageIO.read(new File("src/main/resources/textures/trymImage.jpg")));
            textures.put("leaves", ImageIO.read(new File("src/main/resources/textures/trymImage.jpg")));
            textures.put("brick", ImageIO.read(new File("src/main/resources/textures/trymImage.jpg")));

            textures.put("zombie_head", ImageIO.read(new File("src/main/resources/textures/trymImage.jpg")));
            textures.put("zombie_torso", ImageIO.read(new File("src/main/resources/textures/trymImage.jpg")));
            textures.put("zombie_appendages", ImageIO.read(new File("src/main/resources/textures/trymImage.jpg")));
            textures.put("trym", ImageIO.read(new File("src/main/resources/textures/trymImage.jpg"))); */

            logoImage = ImageIO.read(new File("src/main/resources/Trynity_Logo.png"));
            menuBackgroundImage = ImageIO.read(new File("src/main/resources/background.png"));
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

    public BufferedImage getMenuBackground(){
        return this.menuBackgroundImage;
    }
}
