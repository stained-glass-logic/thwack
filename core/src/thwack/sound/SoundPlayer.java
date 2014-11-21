package thwack.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import thwack.Constants;

/**
 * Date: 8-6-14
 * Time: 10:07
 */
public enum SoundPlayer {
    SLAP("sound/Slap-SoundMaster13-49669815.mp3", 0.5f);

    private String filename;
    private float volume;

    private Sound sound;

    SoundPlayer(String filename, float volume) {
        this.filename = filename;
        this.volume = volume;
    }

    public void load() {
        if (sound == null) {
            sound = Gdx.audio.newSound(Gdx.files.internal(filename));
        }
    }

    public void play() {
        play(volume);
    }

    public void play(float volume) {
        if (sound == null) {
            sound = Gdx.audio.newSound(Gdx.files.internal(filename));
        }

        if (Constants.AUDIO_ON) {
            sound.play(volume * Constants.GLOBAL_VOLUME);
        }
    }

    public void dispose() {
        if (sound != null) {
            sound.dispose();
            sound = null;
        }
    }

}
