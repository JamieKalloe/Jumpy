package net.kalloe.jumpy.scene;

import android.hardware.SensorManager;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;

import net.kalloe.jumpy.GameActivity;
import net.kalloe.jumpy.entity.CollidableEntity;
import net.kalloe.jumpy.entity.Enemy;
import net.kalloe.jumpy.entity.Platform;
import net.kalloe.jumpy.entity.Player;
import net.kalloe.jumpy.factory.EnemyFactory;
import net.kalloe.jumpy.factory.PlatformFactory;
import net.kalloe.jumpy.factory.PlayerFactory;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.util.adt.align.HorizontalAlign;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jamie on 28-2-2016.
 */
public class GameScene extends AbstractScene implements IAccelerationListener {

    //Variables
    private Player player;
    private float lastX = 0;
    private PhysicsWorld physicsWorld;
    Random rand = new Random();
    private LinkedList<Platform> platforms = new LinkedList<>();
    private LinkedList<Enemy> enemies = new LinkedList<>();

    private static final float MIN = 50f;
    private static final float MAX = 250f;

    private Text scoreText;
    private int score;

    private Text endGameText;

    /**
     * Creates a new instance of the GameScene (main scene).
     */
    public GameScene() {
        super();
        //Initializes the (Box2D) Physics World (the whole simulation including all bodies / entities).
        physicsWorld = new PhysicsWorld(new Vector2(0, -SensorManager.GRAVITY_EARTH), false);

        //Initializes the PlayerFactory with the physicsworld and vertex buffer object manager.
        PlayerFactory.getInstance().create(physicsWorld, vbom);

        //Initializes the PlatformFactory with the physicsworld and vertex buffer object manager.
        PlatformFactory.getInstance().create(physicsWorld, vbom);

        //Initializes the EnemyFactory with the physicsworld and vertex buffer object manager.
        EnemyFactory.getInstance().create(physicsWorld, vbom);
    }

    /**
     * Creates and loads the entities for the scene.
     */
    @Override
    public void populate() {
        //Create and load the game entities.
        createBackground();
        createPlayer();
        camera.setChaseEntity(player);
        createHUD();

        addPlatform(240, 100, false);
        addPlatform(340, 400, false);
        addEnemy(140, 400);

        //Enables the accelerometer and registers the listener to the engine.
        engine.enableAccelerationSensor(activity, this);

        //Register the physics world to the engine (as an update handler / thread).
        registerUpdateHandler(physicsWorld);

        //Sets the Contact Listener (responsible for collision detection) to the Physics World (game simulation).
        physicsWorld.setContactListener(new CollisionContactListener(this.player));
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        boolean added = false;

        //Shows a message if the player dies.
        if(player.isDead()) {
            endGameText.setVisible(true);
        }

        while(camera.getYMax() > platforms.getLast().getY()) {

            //Shows a message if the player dies.
            if(player.isDead()) {
                endGameText.setVisible(true);
            }

            //x position of the next platform.
            float tx = rand.nextFloat() * GameActivity.CAMERA_WIDTH;

            //y position of the next platform.
            float ty = platforms.getLast().getY() + MIN + rand.nextFloat() * (MAX - MIN);

            //the player is below the last platform (will fall and die).
            if(player.getY() < platforms.getFirst().getY()) {
                player.die();
            }

            //10% chance to add enemy on the platform.
            if(rand.nextFloat() < 0.1) {
                addEnemy(tx, ty);
            }

            //Add a random moving platform.
            boolean moving = rand.nextBoolean();
            addPlatform(tx, ty, moving);
            added = true;

            if(added) {
                sortChildren();
            }

            //Calculates the score the player has reached.
            calculateScore();

            //Clean up (remove) the unused entities from the game scene (no longer in view).
            cleanEntities(platforms, camera.getYMin());
            cleanEntities(enemies, camera.getYMin());
        }
    }

    @Override
    public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {

    }

    @Override
    public void onAccelerationChanged(final AccelerationData pAccelerationData) {
        //The following code is responsible for calculating the direction the player is facing.
        //According to the result, the player will turn left or right (the sprite will be flipped).
        if(Math.abs(pAccelerationData.getX() - lastX) > 0.5) {
            if(pAccelerationData.getX() > 0) {
                player.turnRight();
            } else {
                player.turnLeft();
            }
            lastX = pAccelerationData.getX();
        }

        //Obtain a vector object (use and recycle) from a pool of objects (prevents garbage collection).
        final  Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX() * 8, -SensorManager.GRAVITY_EARTH * 4);
        this.physicsWorld.setGravity(gravity);
        Vector2Pool.recycle(gravity);
    }

    @Override
    public void onPause() {
        //Disables the accelerometer sensor when the activity is not active.
        engine.disableAccelerationSensor(activity);
    }

    @Override
    public void onResume() {
        //Enables the accelerometer sensor when the activity is being resumed.
        engine.enableAccelerationSensor(activity, this);
    }

    /**
     * Creates a simple background entity containing several Sprite object entities, which are passed in
     * into a EntityBackground object which sets the background object as the background of the Scene.
     */
    private void createBackground() {
        Entity background = new Entity();

        //Load the cloud1 and cloud2 texture into the Sprite objects from the ResourceManager.
        Sprite cloud1 = new Sprite(200, 300, res.cloud1TextureRegion, vbom);
        Sprite cloud2 = new Sprite(300, 600, res.cloud2TextureRegion, vbom);

        //Attach the 2 Sprite (Entity) objects to the background entity/object.
        background.attachChild(cloud1);
        background.attachChild(cloud2);

        //Set an EntityBackground (simple entity) to the Scene passing in the background object,
        //Containing the 2 Sprite object / entities.
        setBackground(new EntityBackground(0.82f, 0.96f, 0.97f, background));
    }

    /**
     * Creates a new instance of the Player entity and adds it as a child to the scene.
     */
    private void createPlayer() {
        //Creates a new instance of the Player class with specified coordinates.
        this.player = PlayerFactory.getInstance().createPlayer(240, 400);

        //Attaches the Player entity (object) to the Scene entity.
        attachChild(player);
    }

    private void addEnemy(float tx, float ty) {
        Enemy enemy = EnemyFactory.getInstance().createEnemy(tx, ty);

        //Attaches the enemy to the scene.
        attachChild(enemy);

        //Adds the enemy to the list of enemies.
        enemies.add(enemy);
    }

    /**
     * Creates a new instance of the Platform entity (static of moving) and adds it as a child to the scene.
     * @param tx x coordinates of the platform.
     * @param ty y coordinates of the platform.
     * @param moving boolean moving (results in a static or moving platform).
     */
    private void addPlatform(float tx, float ty, boolean moving) {
        Platform platform;

        //Checks if the the requested platform is a moving or static platform.
        if(moving) {
            platform = PlatformFactory.getInstance().createMovingPlatform(tx, ty, (rand.nextFloat() - 0.5f) * 10f);

        } else {
            platform = PlatformFactory.getInstance().createPlatform(tx, ty);
        }

        //Attaches the platform to the scene.
        attachChild(platform);

        //Adds the platform to the list of platforms.
        platforms.add(platform);
    }

    /**
     * Creates a new heads-up display and attaches it to the camera (has a special camera-scene).
     */
    private void createHUD() {
        //Creates a new heads-up display (HUD)
        HUD hud = new HUD();

        //Creates a new text label using the font from the ResourceManager.
        scoreText = new Text(16, 789, res.font, "0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
        scoreText.setAnchorCenter(0, 1);

        //Initializes the score and sets it as the HUD score text.
        score = 0;
        scoreText.setText(String.valueOf(score));

        //Attaches the text object to the hud.
        hud.attachChild(scoreText);

        //Initialize the game over message for when the player dies.
        endGameText = new Text(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2, res.font,
                "GAME OVER! TAP TO CONTINUE", new TextOptions(HorizontalAlign.CENTER), vbom);
        endGameText.setAutoWrap(AutoWrap.WORDS);
        endGameText.setAutoWrapWidth(300f);
        endGameText.setVisible(false);
        hud.attachChild(endGameText);

        //A HUD needs to be attached to the camera, because it uses a special camera-scene.
        //If the HUD is attached to the game scene, the text would leave the screen when the player moves.
        camera.setHUD(hud);
    }

    /**
     * Removes entities from a collection of entities if it meets the criteria of a certian threshold.
     * @param list list of collidables entities.
     * @param bound threshold.
     */
    private void cleanEntities(List<? extends CollidableEntity> list, float bound) {
        Iterator<? extends CollidableEntity> iterator = list.iterator();
        while(iterator.hasNext()) {
            CollidableEntity collidableEntity = iterator.next();

            //If the collidable entities's y coordinate is lower the the specified bound.
            //The entity will be removed from the list, the sprite detached from the scene.
            //And the physics body of the collidable entity is destroyed.
            if(collidableEntity.getY() < bound) {
                iterator.remove();
                collidableEntity.detachSelf();
                physicsWorld.destroyBody(collidableEntity.getBody());
            }
        }
    }

    private void calculateScore() {
        if(camera.getYMin() > score) {
            score = Math.round(camera.getYMin());
            scoreText.setText(String.valueOf(score));
        }
    }

    /**
     * Creates and shows a Toast message using the Android Toast widget.
     * A Toast is run on te UI Thread which every activity has.
     * @param text Text to be displayed by the Toast.
     * @param length Length of the Toast to be displayed.
     */
    public void showToast(final String text, final int length) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, length).show();
            }
        });
    }
}
