package no.uib.inf101.sem2.game.model.resourceLoaders;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class handles the loading and playing of sound files.
 */
public class SoundPlayer {
    /**
     * A map of sounds that are cloned each time a sound is played once,
     * this means a sound can be played multiple times at the same time
     * and overlap
     */
    Map<String, File> clonableSounds = new HashMap<String, File>();

    /**
     * A map of sounds that are looped when they are played.
     * When they start playing they start from the beginning,
     * and when they stop they reset to the beginning.
     * 
     * Sounds in this map cannot overlap with themselves.
     */
    Map<String, Clip> loopSounds = new HashMap<String, Clip>();

    /**
     * Initializes the SoundPlayer by loading various sound files.
     */
    public SoundPlayer(){
        initSound("zombie_ambient", "src/main/resources/sounds/zombie_ambient.wav");
        initSound("zombie_hurt", "src/main/resources/sounds/zombie_hurt.wav");
        /* initSound("final_boss_hurt", "src/main/resources/sounds/final_boss_hurt.wav"));
        initSound("final_boss_death", "src/main/resources/sounds/final_boss_death.wav"));*/

        initSound("player_death", "src/main/resources/sounds/player_death.wav");
        initSound("player_shoot", "src/main/resources/sounds/gun_sound.wav");
        //initSound("player_walk", "src/main/resources/sounds/player_walk.wav"));

        /**
         * Credit to Valve Software
         * https://store.steampowered.com/app/323140/HalfLife_2_Soundtrack/
         * Composer - Kelly Bailey
         */
        initSound("triage_at_dawn", "src/main/resources/sounds/triage_at_dawn.wav");
        initSound("ravenholm_reprise", "src/main/resources/sounds/ravenholm_reprise.wav");
    }

    /**
     * Initializes a sound and adds it to the clonableSounds and loopSounds maps.
     *
     * @param soundKey  The key to associate with the sound.
     * @param filename  The filename of the sound file.
     */
    private void initSound(String soundKey, String filename){
        File soundFile = new File(filename);
        
        loopSounds.put(soundKey, loadSound(soundFile));
        clonableSounds.put(soundKey, soundFile);
       
    }

    /**
     * Loads a sound file into a Clip object.
     *
     * @param file  The file to load the sound from.
     * @return      A Clip object containing the loaded sound.
     */
    private Clip loadSound(File file){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Plays a sound once with the specified volume.
     *
     * @param soundKey  The key associated with the sound.
     * @param volume    The volume level to play the sound at.
     */
    public void playSoundOnce(String soundKey, float volume) {
        if (soundKey != null && volume > 0) {
            try {
                Clip clip = loadSound(clonableSounds.get(soundKey));
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts a sound loop with the specified volume.
     *
     * @param soundKey  The key associated with the sound.
     * @param volume    The volume level to play the sound at.
     */
    public void startSoundLoop(String soundKey, float volume) {
        if (soundKey != null && volume > 0) {
            try {
                Clip clip = loopSounds.get(soundKey);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ends a sound loop associated with the specified soundKey.
     *
     * @param soundKey  The key associated with the sound loop.
     */
    public void endSoundLoop(String soundKey) {
        if (soundKey != null) {
            try {
                Clip clip = loopSounds.get(soundKey);
                clip.stop();
                clip.setFramePosition(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



