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

public class SoundPlayer {
    Map<String, Clip> sounds = new HashMap<String, Clip>();

    public SoundPlayer(){
        sounds.put("zombie_ambient", loadClip("src/main/resources/sounds/zombie_ambient.wav"));
        sounds.put("zombie_hurt", loadClip("src/main/resources/sounds/zombie_hurt.wav"));
        /* sounds.put("final_boss_ambient", loadClip("src/main/resources/sounds/final_boss_ambient.wav"));
        sounds.put("final_boss_death", loadClip("src/main/resources/sounds/final_boss_hurt.wav"));

        sounds.put("music1", loadClip("src/main/resources/sounds/music1.wav"));

        sounds.put("player_hurt", loadClip("src/main/resources/sounds/player_hurt.wav"));
        sounds.put("player_death", loadClip("src/main/resources/sounds/player_death.wav"));
        sounds.put("player_shoot", loadClip("src/main/resources/sounds/player_shoot.wav"));
        sounds.put("player_walk", loadClip("src/main/resources/sounds/player_walk.wav")); */
    }

    private Clip loadClip(String path){
        try {
            File soundFile = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void playSoundOnce(String soundKey, float volume){
        if(soundKey != null && volume > 0){
            try {
                Clip clip = sounds.get(soundKey);
                clip.stop();
                clip.setFramePosition(0);
                clip.flush();
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startSoundLoop(String soundKey, float volume){
        if(soundKey != null && volume > 0){
            try {
                Clip clip = sounds.get(soundKey);
                clip.stop();
                clip.setFramePosition(0);
                clip.flush();
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void endSoundLoop(String soundKey, float volume){
        if(soundKey != null && volume > 0){
            try {
                Clip clip = sounds.get(soundKey);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                clip.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


