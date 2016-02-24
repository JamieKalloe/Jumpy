package net.kalloe.jumpy;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
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

    public void loadGameGraphics() {
        //Selects the directory in which the assets are saved.
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        //Creates a full texture atlas / spritesheet of all the elements in out assets folder.
        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(),
                1024, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        //Creates the player (atlas) texture, from player.png, from the GameTextureAtlas object.
        playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
                activity.getAssets(), "player.png", 3, 1);

        //Creates the enemy (atlas) texture, from the enemy.png, from the GameTextureAtlas object.
        enemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(, gameTextureAtlas,
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
     * Returns the singleton instance of the ResourceManager.
     * @return Instance of the ResourceManager.
     */
    public static ResourceManager getInstance() {
        return INSTANCE;
    }
}
