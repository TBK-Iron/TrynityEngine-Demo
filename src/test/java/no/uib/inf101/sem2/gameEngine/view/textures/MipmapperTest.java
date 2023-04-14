package no.uib.inf101.sem2.gameEngine.view.textures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MipmapperTest {
    private Mipmapper mipmapper;
    private BufferedImage testImage;

    @BeforeEach
    public void setUp() throws IOException {
        testImage = ImageIO.read(new File("src/main/resources/textures/trymImage.jpg"));

        Map<String, BufferedImage> textures = new HashMap<>();
        textures.put("test", testImage);

        mipmapper = new Mipmapper(textures);
    }

    @Test
    public void testMipmapTexture() {
        BufferedImage mipmappedTexture = mipmapper.getMipmappedTexture("test", testImage.getWidth() / 2, testImage.getHeight() / 2);
        assertNotNull(mipmappedTexture);
        assertEquals(testImage.getWidth() / 2, mipmappedTexture.getWidth());
        assertEquals(testImage.getHeight() / 2, mipmappedTexture.getHeight());
    }

    @Test
    public void testMipmapTextureSameSize() {
        BufferedImage mipmappedTexture = mipmapper.getMipmappedTexture("test", testImage.getWidth(), testImage.getHeight());
        assertNotNull(mipmappedTexture);
        assertEquals(testImage.getWidth(), mipmappedTexture.getWidth());
        assertEquals(testImage.getHeight(), mipmappedTexture.getHeight());
    }

    @Test
    public void testMipmapTextureLargerSize() {
        BufferedImage mipmappedTexture = mipmapper.getMipmappedTexture("test", testImage.getWidth() * 2, testImage.getHeight() * 2);
        assertNotNull(mipmappedTexture);
        assertEquals(testImage.getWidth(), mipmappedTexture.getWidth());
        assertEquals(testImage.getHeight(), mipmappedTexture.getHeight());
    }

    @Test
    public void testMipmapTextureNonexistentKey() {
        assertThrows(NullPointerException.class, () -> mipmapper.getMipmappedTexture("nonexistent", testImage.getWidth() / 2, testImage.getHeight() / 2));
    }

    @Test
    public void testMipmapTextureAspectRatio() {
        BufferedImage mipmappedTexture = mipmapper.getMipmappedTexture("test", testImage.getWidth() / 3, testImage.getHeight() / 2);
        assertNotNull(mipmappedTexture);
        assertTrue(mipmappedTexture.getWidth() <= testImage.getWidth() / 2);
        assertTrue(mipmappedTexture.getHeight() <= testImage.getHeight() / 2);
    }
}