package thwack.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import thwack.Constants;

/**
 * Date: 8-6-14
 * Time: 10:07
 */
public enum MusicPlayer {
    GAME_RAGTIME("music/577067_Nice-Work-If-You-Ca.mp3", 0.75f);

    private String filename;
    private float volume;

    private transient Music music = null;

    MusicPlayer(String filename, float volume) {
        this.filename = filename;
        this.volume = volume;
    }

    public void load() {
        if (music == null) {
            music = Gdx.audio.newMusic(Gdx.files.internal(filename));
        }
    }

    public void play() {
        play(volume);
    }

    public void play(float volume) {
        load();

        music.setVolume(volume * Constants.GLOBAL_VOLUME);
        music.play();
        music.setLooping(true);
    }

    public void pause() {
        music.pause();
    }

    public void stop() {
        music.stop();
    }

    public void setVolume(float volume) {
        music.setVolume(volume);
    }

    public void dispose() {
        if (music != null) {
            music.dispose();
            music = null;
        }
    }

}
