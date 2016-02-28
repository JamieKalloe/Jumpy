package net.kalloe.jumpy;

import android.graphics.Typeface;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObject;
import org.andengine.util.adt.color.Color;

/**
 * Created by Jamie on 24-2-2016.
 */
public class ResourceManager {

    //Variables
    public GameActivity activity;
    public Engine engine;
    public Camera camera;
    public VertexBufferObject vbom;

    //Game textures
    public ITiledTextureRegion playerTextureRegion;
    public ITiledTextureRegion enemyTextureRegion;
    public ITextureRegion platformTextureRegion;
    public ITextureRegion cloud1TextureRegion;
    public ITextureRegion cloud2TextureRegion;

    //A collection of textures (subimages) on a single image.
    private BuildableBitmapTextureAtlas gameTextureAtlas;

    //Sounds
    public Sound soundFall;
    public Sound soundJump;

    //Music
    public Music music;

    //Font
    public Font font;

    //Singleton, object only gets created once.
    private static final ResourceManager INSTANCE = new ResourceManager();

    //Constructor is private, to ensure no new instance can be created.
    private ResourceManager() {}

    /**
     *
     * @param activity The Game (or main) Activity of the game.
     * @param engine Instance of the GameEngine object.
     * @param camera Instance of the Camera object.
     * @param vbom Uploads vertex data (points, colors, vectors) into the video memory.
     */
    public void create(GameActivity activity, Engine engine, Camera camera, VertexBufferObject vbom) {
        this.activity = activity;
        this.engine = engine;
        this.camera = camera;
        this.vbom = vbom;
    }

    /**
     * Creates a texture atlas / spritesheet of all the assets and loads it into specific resources.
     */
    public void loadGameGraphics() {
        //Selects the directory in which the assets (game graphics) are saved.
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        //Creates a full texture atlas / spritesheet of all the elements in out assets folder.
        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(),
                1024, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
                //RGBA_8888 has 31-bit textures (highest quality and stores alpha channel (transparency).

        //Creates the player (atlas) texture, from player.png, from the GameTextureAtlas object.
        playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
                activity.getAssets(), "player.png", 3, 1);

        //Creates the enemy (atlas) texture, from the enemy.png, from the GameTextureAtlas object.
        enemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
                activity.getAssets(), "enemy.png", 1, 2);

        //Creates the platform (atlas) texture, from the platform.png, from the GameTextureAtlas object.
        platformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "platform.png");

        //Creates the cloud number 1 (atlas) texture, from the cloud1.png, from the GameTextureAtlas object.
        cloud1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "cloud1.png");

        //Creates the cloud number 2 (atlas) texture, from the enemy.png, from the GameTextureAtlas object.
        cloud2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "cloud2.png");

        try {
            gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 3));
            gameTextureAtlas.load();
        } catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            throw new RuntimeException("Error while loading the game textures", e);
        }
    }

    /**
     * Loads the audio files from the assets folder into specified audio (Sound / Music) objects.
     */
    public void loadGameAudio() {
        try {
            //Selects the directory in which the assets (game audio) are saved.
            SoundFactory.setAssetBasePath("sfx/");
            MusicFactory.setAssetBasePath("mfx/");

            //Loads the audio files data (from the sfx directory) into the specified Sound objects.
            soundJump = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "jumping.ogg");
            soundFall = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "falling.ogg");

            //Loads the audio file data (form the mfx directory) into the music object.
            music = MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity, "music.ogg");
        } catch (Exception e) {
            throw new RuntimeException("Error while loading the game audio", e);
        }
    }

    /**
     * Load the system font and stores it in textures (similair to loading graphics).
     * Every character will be defined as a small texture region on a big texture atlas.
     * The result of this font is a character with white text and a black stroke.
     * The texture size is 256x256px and the font size is 50px.
     */
    public void loadFont() {
        font = FontFactory.createStroke(activity.getFontManager(), activity.getTextureManager(),
                256, 256, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 50, true, Color.WHITE_ABGR_PACKED_INT,
                2, Color.BLACK_ABGR_PACKED_INT);
        font.load();
    }

    /**
     * Returns the singleton instance of the ResourceManager.
     * @return Instance of the ResourceManager.
     */
    public static ResourceManager getInstance() {
        return INSTANCE;
    }
}
