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
        textures.put("trollface", readFile("src/main/resources/textures/trollface.png"));
        textures.put("checkered", readFile("src/main/resources/textures/checkered.png"));
        textures.put("grass", readFile("src/main/resources/textures/grass.jpg"));
        textures.put("bark", readFile("src/main/resources/textures/bark.jpg"));
        textures.put("leaves", readFile("src/main/resources/textures/leaves.jpg"));
        textures.put("brick", readFile("src/main/resources/textures/brick.jpg"));
        textures.put("stone_floor", readFile("src/main/resources/textures/stone_floor.jpg"));
        textures.put("stone_wall", readFile("src/main/resources/textures/stone_wall.jpg"));
        textures.put("metal_door", readFile("src/main/resources/textures/metal_door.jpg"));
        textures.put("sheetmetal", readFile("src/main/resources/textures/sheetmetal.png"));
        textures.put("metal_floor", readFile("src/main/resources/textures/metal_floor.jpg"));
        textures.put("lava", readFile("src/main/resources/textures/lava.jpg"));

        textures.put("zombie_head", readFile("src/main/resources/textures/zombie_head.png"));
        textures.put("zombie_torso", readFile("src/main/resources/textures/zombie_torso.png"));
        textures.put("zombie_appendages", readFile("src/main/resources/textures/zombie_appendages.png"));
        textures.put("trym", readFile("src/main/resources/textures/trymImage.jpg"));
        

        /* textures.put("trollface", readFile("src/main/resources/textures/trymImage.jpg")));
        textures.put("checkered", readFile("src/main/resources/textures/trymImage.jpg")));
        textures.put("grass", readFile("src/main/resources/textures/trymImage.jpg")));
        textures.put("bark", readFile("src/main/resources/textures/trymImage.jpg")));
        textures.put("leaves", readFile("src/main/resources/textures/trymImage.jpg")));
        textures.put("brick", readFile("src/main/resources/textures/trymImage.jpg")));

        textures.put("zombie_head", readFile("src/main/resources/textures/trymImage.jpg")));
        textures.put("zombie_torso", readFile("src/main/resources/textures/trymImage.jpg")));
        textures.put("zombie_appendages", readFile("src/main/resources/textures/trymImage.jpg")));
        textures.put("trym", readFile("src/main/resources/textures/trymImage.jpg"))); */

        logoImage = readFile("src/main/resources/Trynity_Logo.png");
        menuBackgroundImage = readFile("src/main/resources/background.png");
    }

    private BufferedImage readFile(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
