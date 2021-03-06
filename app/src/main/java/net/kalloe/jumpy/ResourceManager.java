package net.kalloe.jumpy;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

/**
 * Created by Jamie on 24-2-2016.
 */
public class ResourceManager {

    //Variables
    public GameActivity activity;
    public Engine engine;
    public Camera camera;
    public VertexBufferObjectManager vbom;

    //Game textures
    public ITiledTextureRegion playerTextureRegion;
    public ITiledTextureRegion lifeTextureRegion;
    public ITiledTextureRegion enemyTextureRegion, flyEnemyTextureRegion;
    public ITiledTextureRegion slimeEnemyTextureRegion;
    public ITextureRegion platformGrassTextureRegion, platformSandTextureRegion;
    public ITextureRegion buttonPlay, buttonAchievements, buttonLeaderboards;
    public ITextureRegion cloud1TextureRegion;
    public ITextureRegion cloud2TextureRegion;
    public ITextureRegion mushroomTextureRegion;
    public ITextureRegion mushroomJumpTextureRegion;
    public ITextureRegion mysteryboxTextureRegion;
    public ITextureRegion goldTextureRegion;

    //Splash scene graphics
    public ITextureRegion splashTextureRegion;
    private BitmapTextureAtlas splashTextureAtlas;

    //A collection of textures (subimages) on a single image.
    private BuildableBitmapTextureAtlas gameTextureAtlas;

    //Sounds
    public Sound soundFall, soundJump, soundHit, soundCash, soundHighscore, soundPowerUp, soundSpring;

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
    public void create(GameActivity activity, Engine engine, Camera camera, VertexBufferObjectManager vbom) {
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

        //Creates the life/hearts (atlas) texture, from life.png, from the GameTextureAtlas object.
        lifeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
                activity.getAssets(), "life.png", 4, 1);

        //Creates the enemy (atlas) texture, from the enemy.png, from the GameTextureAtlas object.
        enemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
                activity.getAssets(), "enemy.png", 1, 2);

        flyEnemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
                activity.getAssets(), "flyenemy.png", 1, 3);

        //Creates the slime enemy (atlas) texture, from the slimeenemy.png, from the GameTextureAtlas object.
        slimeEnemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
                activity.getAssets(), "slimeenemy.png", 1, 2);

        //Creates the platform (atlas) texture, from the platform.png, from the GameTextureAtlas object.
        platformGrassTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "platformgrass.png");

        platformSandTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "platformsand.png");

        //Initialize UI sprites
        buttonPlay = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "buttonplay.png");

        buttonAchievements = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "buttonachievements.png");

        buttonLeaderboards = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "buttonleaderboards.png");

        //Creates the cloud number 1 (atlas) texture, from the cloud1.png, from the GameTextureAtlas object.
        cloud1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "cloud1.png");

        //Creates the cloud number 2 (atlas) texture, from the enemy.png, from the GameTextureAtlas object.
        cloud2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "cloud2.png");

        mushroomTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "mushroompowerup.png");

        mushroomJumpTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "springpowerup.png");

        mysteryboxTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "boxpowerup.png");

        goldTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
                activity.getAssets(), "goldpowerup.png");

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
            soundHit = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "hit.ogg");
            soundCash = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "cash.ogg");
            soundHighscore = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "highscore.ogg");
            soundSpring = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "spring.ogg");
            soundPowerUp = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "powerup.mp3");

            //Loads the audio file data (form the mfx directory) into the music object.
            music = MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity, "bgm.ogg");
            music.setVolume(2.5f);
            music.setLooping(true);
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
//        font = FontFactory.createStroke(activity.getFontManager(), activity.getTextureManager(),
//                256, 256, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 50, true, Color.WHITE_ABGR_PACKED_INT,
//                2, Color.BLACK_ABGR_PACKED_INT);
        FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "rumpelstiltskin.ttf", 50, true, Color.WHITE_ABGR_PACKED_INT, 2, Color.BLACK_ABGR_PACKED_INT);
        font.prepareLetters("01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?".toCharArray());
        font.load();
    }

    /**
     * Loads the resources for the Splashscreen scene (shown when the game is started).
     */
    public void loadSplashGraphics() {
        //Selects the directory in which the assets (splash graphics) are saved.
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        //Loads the splashscreen logo into the TextureAtlas object with a specified width and height.
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256,
                BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        //Loads the image 'logo.png' into the TextureRegion.
        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,
                activity.getAssets(), "logo.png", 0, 0);

        //Loads the image into the game memory.
        splashTextureAtlas.load();
    }

    /**
     * Frees the memory from the Splashscreen scene resources.
     */
    public void unloadSplashGraphics() {
        splashTextureAtlas.unload();
    }

    /**
     * Returns the singleton instance of the ResourceManager.
     * @return Instance of the ResourceManager.
     */
    public static ResourceManager getInstance() {
        return INSTANCE;
    }
}
