package net.kalloe.jumpy.scene;

import android.hardware.SensorManager;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;

import net.kalloe.jumpy.GameActivity;
import net.kalloe.jumpy.MusicPlayer;
import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.SceneManager;
import net.kalloe.jumpy.entity.CollidableEntity;
import net.kalloe.jumpy.entity.Enemy;
import net.kalloe.jumpy.entity.Platform;
import net.kalloe.jumpy.entity.Player;
import net.kalloe.jumpy.entity.VerticalParallaxEntity;
import net.kalloe.jumpy.entity.powerups.Life;
import net.kalloe.jumpy.entity.powerups.MysteryBox;
import net.kalloe.jumpy.factory.EnemyFactory;
import net.kalloe.jumpy.factory.PlatformFactory;
import net.kalloe.jumpy.factory.PlayerFactory;
import net.kalloe.jumpy.factory.PowerUpFactory;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jamie on 28-2-2016.
 */
public class GameScene extends AbstractScene implements IAccelerationListener, IOnSceneTouchListener {

    //Variables
    private Player player;
    private float lastX = 0;
    private PhysicsWorld physicsWorld;
    Random rand = new Random();
    private LinkedList<Platform> platforms = new LinkedList<>();
    private LinkedList<Enemy> enemies = new LinkedList<>();
    private LinkedList<CollidableEntity> powerUps = new LinkedList<>();
    private TiledSprite lifePoints;

    private ParallaxBackground parallaxBackground;

    private static final float MIN = 50f;
    private static final float MAX = 250f;

    private Text scoreText, coinsText;
    private int score;
    private boolean cal = true;
    private int oldScore = 0;
    private int pointsAchieved = 0;
    private final int coinsThreshold = 4000;

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

        //Initializes the EnemyFactory with the physicsworld and vertex buffer object manager.
        PowerUpFactory.getInstace().create(physicsWorld, vbom);

        //Registers the scene to the touch listener to listen for user input.
        this.setOnSceneTouchListener(this);
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

        //Checks if the player has touched the screen and if the player is dead.
        //If the condition is met, the game will clear all it's entities and restart.
        if(pSceneTouchEvent.isActionUp() && player.isDead()) {
            restartGame();
            cal = true;
            return true;
        }

//        if(pSceneTouchEvent.isActionUp() && !player.isDead()) {
//            if(pSceneTouchEvent.getX() > 400 && pSceneTouchEvent.getY() > 700) {
//                Log.i("toucharea", "yes");
//            } else {
//                Log.i("toucharea", "no");
//            }
//        }

        return false;
    }

    /**
     * Redirects the user to the game menu when the user presses the back key in the game scene.
     */
    @Override
    public void onBackKeyPressed() {
        if(!player.isDead()) {
            showToast("You're in a game!", Toast.LENGTH_SHORT);
        } else {
            endGameText.setVisible(false);
            SceneManager.getInstance().showMenuScene();
            clearGame();
        }
    }

    /**
     * Creates and loads the entities for the scene.
     */
    @Override
    public void populate() {
        try {

            //Allow calculation of coins.
            cal = true;
            //Create and load the game entities.
//            createBackground();
            createParallaxBackground();
            createPlayer();
            camera.setChaseEntity(player);
            createHUD();

            addPlatform(240, 100, false);
            addPlatform(340, 400, false);
//            addPowerUp(240, 200);
            addEnemy(140, 400);

            //Enables the accelerometer and registers the listener to the engine.
            engine.enableAccelerationSensor(activity, this);

            //Register the physics world to the engine (as an update handler / thread).
            registerUpdateHandler(physicsWorld);

            //Sets the Contact Listener (responsible for collision detection) to the Physics World (game simulation).
            physicsWorld.setContactListener(new CollisionContactListener(this.player));

            //Starts the playing of the background music.
            MusicPlayer.getInstance().play();
        } catch (Exception e) {
            SceneManager.getInstance().showMenuScene();
            clearGame();
        }
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        parallaxBackground.setParallaxValue(camera.getCenterY());

        boolean added = false;

        //Calculates the lives the player has remaining.
        calculateLifes();

        //the player is below the last platform (will fall and die).
        if(player.getY() < platforms.getFirst().getY() && !platforms.isEmpty()) {
            player.die();
            cal = false;
            pointsAchieved = 0;
        }

        //Shows a message if the player dies and save the high score if achieved.
        if(player.isDead()) {
            endGameText.setVisible(true);

            //Saves the score of the player, if a new high score is achieved.
            if(score > activity.getHighScore()) {
                activity.setHighScore(score);
                score = 0;
            }

            //Saves the amount of coins the player was awarded.
            if(player.getCoins() > 0) {
                activity.setCoins(activity.getCoins() + player.getCoins());
                player.setCoins(0);
            }
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

            //10% chance to add enemy on the platform.
            if(rand.nextFloat() < 0.1) {
                addEnemy(tx, ty);
            }

            //Add a random moving platform.
            boolean moving = rand.nextBoolean();
            addPlatform(tx, ty, moving);
            added = true;

            //create a slime enemy on a newly generated platform.
            if(rand.nextFloat() < 0.2 && !moving) {
                addSlimeEnemy(tx, ty);
            }

            //create a power up on a newly generated platform.
            if(rand.nextFloat() < 0.07 && !moving) {
                addPowerUp(tx, ty);
            }

            if(added) {
                sortChildren();
            }

            //Calculates the score the player has reached.
            calculateScore();
            calculateCoins(cal);

            //Clean up (remove) the unused entities from the game scene (no longer in view).
            cleanEntities(platforms, camera.getYMin());
            cleanEntities(enemies, camera.getYMin());
            cleanEntities(powerUps, camera.getYMin());
        }
    }


    @Override
    public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {

    }

    @Override
    public void onAccelerationChanged(final AccelerationData pAccelerationData) {
        //The following code is responsible for calculating the direction the player is facing.
        //According to the result, the player will turn left or right (the sprite will be flipped).
        if (Math.abs(pAccelerationData.getX() - lastX) > 0.5) {
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
        MusicPlayer.getInstance().pause();
    }

    @Override
    public void onResume() {
        //Enables the accelerometer sensor when the activity is being resumed.
        engine.enableAccelerationSensor(activity, this);
        MusicPlayer.getInstance().play();
    }

    @Override
    public void destory() {
//        camera.reset();
//        camera.setHUD(null);
        clearGame();
        MusicPlayer.getInstance().stop();
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
     * Creates a simple parallax background entity containing several Sprite object entities, which are passed in
     */
    private void createParallaxBackground() {
        parallaxBackground = new ParallaxBackground(0.82f, 0.96f, 0.97f);

        //Layers in the back.
        Entity clouds = new Entity();
        clouds.setSize(480, 800);
        clouds.setAnchorCenter(0, 0);

        //Cloud sprites.
        Sprite cloud1 = new Sprite(200, 300, res.cloud1TextureRegion, vbom);
        Sprite cloud2 = new Sprite(300, 600, res.cloud2TextureRegion, vbom);

        //Attach the cloud sprites to the clouds entity object.
        clouds.attachChild(cloud1);
        clouds.attachChild(cloud2);

        //Pass in the clouds entity (containing the sprites) to our own parallax background entity and attach it to the parallax background object.
        VerticalParallaxEntity cloudsLayer = new VerticalParallaxEntity(-0.1f, clouds);
        parallaxBackground.attachParallaxEntity(cloudsLayer);

        //Layer in the front.
        Entity platforms = new Entity();
        platforms.setSize(480, 800);
        platforms.setAnchorCenter(0, 0);

        //Platform sprites
        Sprite platform1 = new Sprite(150, 200, res.platformTextureRegion, vbom);
        Sprite platform2 = new Sprite(250, 550, res.platformTextureRegion, vbom);
        Sprite platform3 = new Sprite(350, 450, res.platformTextureRegion, vbom);

        //Platforms are made slightly transparent with the alpha channel.
        platform1.setColor(0.3f, 0.3f, 0.3f, 0.3f);
        platform2.setColor(0.3f, 0.3f, 0.3f, 0.3f);
        platform3.setColor(0.3f, 0.3f, 0.3f, 0.3f);

        //Platforms are made slightly smaller to enhance the difference of the real platforms.
        platform1.setScale(0.8f);
        platform2.setScale(0.8f);
        platform3.setScale(0.8f);

        //Attach the platform sprites to the clouds entity object.
        platforms.attachChild(platform1);
        platforms.attachChild(platform2);
        platforms.attachChild(platform3);

        //Pass in the platform entity (containing the sprites) to our own parallax background entity and attach it to the parallax background object.
        VerticalParallaxEntity platformsLayer = new VerticalParallaxEntity(-0.5f, platforms);
        parallaxBackground.attachParallaxEntity(platformsLayer);

        //Finally set the parallax background, as background to the game scene.
        setBackground(parallaxBackground);
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

    private void addSlimeEnemy(float tx, float ty) {
        Enemy enemy = EnemyFactory.getInstance().createSlimeEnemy(tx, ty);

        //Attaches the enemy to the scene.
        attachChild(enemy);

        //Adds the enemy to the list of enemies.
        enemies.add(enemy);
    }

    //TODO: make addPowerUp random (random powerup instead of just life)
    private void addPowerUp(float tx, float ty) {
        //TODO: implement a random with numbers / switch case later, for more than 2 power ups in factory.
        if(rand.nextBoolean()) {
            Life life = PowerUpFactory.getInstace().createLife(tx, ty);
            attachChild(life);
            powerUps.add(life);
        } else {
            MysteryBox mysteryBox = PowerUpFactory.getInstace().createMysteryBox(tx, ty);
            attachChild(mysteryBox);
            powerUps.add(mysteryBox);
        }
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

        //Creates a new text label for the amount of points, using the font from the ResourceManager.
        scoreText = new Text(16, 789, res.font, "0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
        scoreText.setAnchorCenter(0, 1);

        //Creates a new text label for the amount of coins, using the font from the ResourceManager.
        coinsText = new Text(93, 700, res.font, "0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);

        //Create a new sprite for the coin icon.
        Sprite coinSprite = new Sprite(40, 700, res.coinTextureRegion, vbom);

        //Create a new text tiled sprite for the amount of lives.
        lifePoints = new TiledSprite(430, 755, res.lifeTextureRegion, vbom);

        //Initializes the score and sets it as the HUD score text.
        score = 0;
        scoreText.setText(String.valueOf(score));
        coinsText.setText(String.valueOf(player.getCoins()));

        //Attaches the text objects to the hud.
        hud.attachChild(scoreText);
        hud.attachChild(coinsText);
        hud.attachChild(coinSprite);
        hud.attachChild(lifePoints);

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

    /**
     * Calculates and sets the players's score to the HUD text.
     */
    private void calculateScore() {
        if(camera.getYMin() > score) {
            oldScore = score;
            score = Math.round(camera.getYMin());
            pointsAchieved += (score - oldScore);
            scoreText.setText(String.valueOf(score));
        }
    }

    /**
     * Awards the player an x amount of coins for every y amount of points achieved.
     * @param allow boolean, award coins.
     */
    private void calculateCoins(boolean allow) {
        if(allow) {
            if(pointsAchieved >= coinsThreshold) {
                pointsAchieved = 0;
                player.addCoins((int)((Math.random() * (10 - 1)) + 1) * 10);
                coinsText.setText(String.valueOf(player.getCoins()));

                //Play cash sound
                ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundCash);

                //Adjust placement of Text object, when the coins score increases.
                if(coinsText.getText().length() > 2) {
                    coinsText.setPosition(93 + (((coinsText.getText().length() - 2) * 13)),700);
                }
            }
        }
    }

    /**
     * Retrieves the health (hit points) of the player, and shows them in a tiled sprite.
     */
    private void calculateLifes() {
        lifePoints.setCurrentTileIndex(player.getHealth());
    }

    private void clearGame() {
        setIgnoreUpdate(true);
        unregisterUpdateHandler(physicsWorld);
        enemies.clear();
        platforms.clear();
        physicsWorld.clearForces();
        physicsWorld.clearPhysicsConnectors();

        //Loops through all entity bodies and clears them all from the physicsworld.
        while(physicsWorld.getBodies().hasNext()) {
            physicsWorld.destroyBody(physicsWorld.getBodies().next());
        }

        //Resets the view of the camera to the center.
        //Clears the HUD.
        //Clears the chase entity (which is the player) from the camera.
        camera.reset();
        camera.setHUD(null);
        camera.setChaseEntity(null);
        detachChildren();
    }

    /**
     * Clears all entities from the game and loads / recreates them all for a new game.
     */
    private void restartGame() {
        //Clears and empties all lists containing game entities.
        setIgnoreUpdate(true);
        unregisterUpdateHandler(physicsWorld);
        enemies.clear();
        platforms.clear();
        physicsWorld.clearForces();
        physicsWorld.clearPhysicsConnectors();

        //Loops through all entity bodies and clears them all from the physicsworld.
        while(physicsWorld.getBodies().hasNext()) {
            physicsWorld.destroyBody(physicsWorld.getBodies().next());
        }

        //Resets the view of the camera to the center.
        //Clears the HUD.
        //Clears the chase entity (which is the player) from the camera.
        camera.reset();
        camera.setHUD(null);
        camera.setChaseEntity(null);
        detachChildren();

        //Creates and loads all the entities for the game.
        this.populate();
        setIgnoreUpdate(false);
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
