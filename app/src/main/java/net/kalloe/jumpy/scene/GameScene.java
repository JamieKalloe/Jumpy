package net.kalloe.jumpy.scene;

import android.widget.Toast;

import net.kalloe.jumpy.entity.Player;
import net.kalloe.jumpy.factory.PlayerFactory;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.collision.CollisionHandler;
import org.andengine.engine.handler.collision.ICollisionCallback;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

/**
 * Created by Jamie on 28-2-2016.
 */
public class GameScene extends AbstractScene implements IAccelerationListener {

    //Variables
    private Player player;
    private Text scoreText;
    private AnimatedSprite fly;

    public GameScene() {
        //Creates a new instance of the Player
        PlayerFactory.getInstance().create(vbom);
    }

    /**
     * Creates and loads the entities for the scene.
     */
    @Override
    public void populate() {
        //Create and load the game entities.
        createBackground();
        createPlayer();
        createEnemy();
        createHUD();

        //Enables touch events for the player object (sprite).
        registerTouchArea(player);

        //Enables the accelerometer and registers the listener to the engine.
        engine.enableAccelerationSensor(activity, this);

        setOnSceneTouchListener(new IOnSceneTouchListener() {

            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
                //Checks whether the TouchEvent is an ACTION_DOWN event (tap on the screen).
                if (pSceneTouchEvent.isActionDown()) {
                    player.clearEntityModifiers();
                    player.registerEntityModifier(new MoveModifier(1, player.getX(), player.getY(),
                            pSceneTouchEvent.getX(), pSceneTouchEvent.getY()));
                    return true;
                }
                return false;
            }

        });

        //Collision detection of the player and enemies.
        ICollisionCallback playerCollision = new ICollisionCallback() {

            @Override
            public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
                fly.setColor(Color.RED);
                return false;
            }

        };

        //Binds the player and enemy objects to check for collision of the sprites.
        CollisionHandler collisionHandler = new CollisionHandler(playerCollision, fly, player);
        registerUpdateHandler(collisionHandler);
    }

    @Override
    public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {

    }

    float lastX = 0;
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

        player.setX(player.getX() + pAccelerationData.getX());
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

    private void createPlayer() {
        //Creates a new instance of the Player class with specified coordinates.
        this.player = PlayerFactory.getInstance().createPlayer(240, 400);

        //Attaches the Player entity / object to the Scene entity.
        attachChild(player);
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

        //Attaches the text object to the hud.
        hud.attachChild(scoreText);

        //A HUD needs to be attached to the camera, because it uses a special camera-scene.
        //If the HUD is attached to the game scene, the text would leave the screen when the player moves.
        camera.setHUD(hud);
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


    public void createEnemy() {
        fly = new AnimatedSprite(240, 200, res.enemyTextureRegion, vbom) {

            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);

                if(collidesWith(player)) {
                    setScale(2);
                } else {
                    setScale(1);
                }
            }
        };

        fly.animate(125);
        attachChild(fly);
    }
}
