package net.kalloe.jumpy;

/**
 * Created by Jamie on 14-3-2016.
 */
public class MusicPlayer {

    //Variables
    private static MusicPlayer INSTANCE = new MusicPlayer();
    private ResourceManager res = ResourceManager.getInstance();

    //Singleton
    private MusicPlayer() {
        res = ResourceManager.getInstance();
    }

    /**
     * Returns the singleton instance of the MusicPlayer.
     * @return Instance of the MusicPlayer.
     */
    public static MusicPlayer getInstance() {
        return INSTANCE;
    }

    /**
     * Starts playing the background music of the game (if sound is enabled).
     */
    public void play() {
        if(res.activity.isSound() && !res.music.isPlaying()) {
            res.music.play();
        }
    }

    /**
     * Pauses the background music of the MusicPlayer (if the music is playing).
     */
    public void pause() {
        if(res.music.isPlaying()) {
            res.music.pause();
        }
    }

    /**
     * Stops / reset the background music audio track (if the music is playing).
     */
    public void stop() {
        if(res.music.isPlaying()) {
            res.music.pause();
            res.music.seekTo(0);
        }
    }
}
