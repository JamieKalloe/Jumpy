package net.kalloe.jumpy.scene;

import android.widget.Toast;

import net.kalloe.jumpy.entity.Player;
import net.kalloe.jumpy.factory.PlayerFactory;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

/**
 * Created by Jamie on 28-2-2016.
 */
public class GameScene extends AbstractScene {

    //Variables
    private Player player;
    private Text scoreText;

    public GameScene() {
        //Creates a new instance of the Player
        PlayerFactory.getInstance().create(vbom);
    }

    /**
     * Creates and loads the entities for the scene.
     */
    @Override
    public void populate() {
        createBackground();
        createPlayer();
        createHUD();

        //Test toast
//        this.showToast(activity.getString(R.string.hello_world), Toast.LENGTH_LONG);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

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
}
