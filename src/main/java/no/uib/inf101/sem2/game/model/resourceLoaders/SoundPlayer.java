package no.uib.inf101.sem2.game.model.resourceLoaders;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
    Map<String, File> clonableSounds = new HashMap<String, File>();
    Map<String, Clip> loopSounds = new HashMap<String, Clip>();

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

    private void initSound(String soundName, String filename){
        File soundFile = new File(filename);
        
        loopSounds.put(soundName, loadSound(soundFile));
        clonableSounds.put(soundName, soundFile);
       
    }

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

    public void playSoundOnce(String soundKey, float volume) {
        if (soundKey != null && volume > 0) {
            System.out.println(
                    "Playing sound " + soundKey + " with volume " + volume
            );
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

    public void startSoundLoop(String soundKey, float volume) {
        if (soundKey != null && volume > 0) {
            System.out.println(soundKey);
            try {
                System.out.println(loopSounds.get(soundKey));
                Clip clip = loopSounds.get(soundKey);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void endSoundLoop(String soundKey) {
        if (soundKey != null) {
            try {
                Clip clip = loopSounds.get(soundKey);
                clip.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



