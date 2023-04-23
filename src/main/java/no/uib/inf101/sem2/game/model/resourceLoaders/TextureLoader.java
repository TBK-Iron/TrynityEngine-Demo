package no.uib.inf101.sem2.game.model.resourceLoaders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Class responsible for loading textures and images from files and storing them in a map for easy access.
 * Textures can be used in the game by accessing the map using the appropriate key.
 */
public class TextureLoader {
    private Map<String, BufferedImage> textures = new HashMap<String, BufferedImage>();

    private BufferedImage logoImage;
    private BufferedImage menuBackgroundImage;

    /**
     * Constructs a new TextureLoader and initializes the map with the available textures.
     */
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
        textures.put("beast", readFile("src/main/resources/textures/trym_cube.png"));
        
        textures.put("gravel", readFile("src/main/resources/textures/gravel.jpg"));
        textures.put("arena_wall", readFile("src/main/resources/textures/arena_wall.jpg"));

        logoImage = readFile("src/main/resources/Trynity_Logo.png");
        menuBackgroundImage = readFile("src/main/resources/background.png");
    }

    /**
     * Reads an image file from the specified path and returns a BufferedImage.
     *
     * @param path The path of the image file to be read.
     * @return A BufferedImage containing the image data.
     */
    private BufferedImage readFile(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the map containing all the loaded textures.
     *
     * @return The textures map.
     */
    public Map<String, BufferedImage> getTextures(){
        return textures;
    }

    /**
     * Returns the logo image.
     *
     * @return The logo image as a BufferedImage.
     */
    public BufferedImage getLogo(){
        return this.logoImage;
    }

    /**
     * Returns the menu background image.
     *
     * @return The menu background image as a BufferedImage.
     */
    public BufferedImage getMenuBackground(){
        return this.menuBackgroundImage;
    }
}
