package no.uib.inf101.sem2.game.model.resourceLoaders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoundPlayerTest {

    private SoundPlayer soundPlayer;

    @BeforeEach
    void setUp() {
        soundPlayer = new SoundPlayer();
    }

    @Test
    void testPlaySoundOnce() {
        String soundKey = "player_death";
        float volume = 0.05f;

        assertDoesNotThrow(() -> soundPlayer.playSoundOnce(soundKey, volume));
    }

    @Test
    void testStartAndEndSoundLoop() {
        String soundKey = "triage_at_dawn";
        float volume = 5f;

        assertDoesNotThrow(() -> soundPlayer.startSoundLoop(soundKey, volume));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertDoesNotThrow(() -> soundPlayer.endSoundLoop(soundKey));
    }
}