package net.kalloe.jumpy.scene;

import com.google.android.gms.games.Games;

import net.kalloe.jumpy.R;
import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.SceneManager;

import org.andengine.entity.particle.BatchedSpriteParticleSystem;
import org.andengine.entity.particle.emitter.RectangleOutlineParticleEmitter;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

/**
 * Created by Jamie on 12-3-2016.
 */
public class MenuSceneWrapper extends AbstractScene implements MenuScene.IOnMenuItemClickListener {

    //Variables
    private IMenuItem playMenuItem, shopMenuItem, playLeaderboards;
    private MenuSceneTextItemDecorator soundMenuItem;

    @Override
    public void populate() {

        //Creates a new MenuScene object and sets the background color.
        MenuScene menuScene = new MenuScene(camera);
        menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);

        //Create a particle system of clouds (animated).
        float timeToLive = 12f;
        final BatchedSpriteParticleSystem particleSystem = new BatchedSpriteParticleSystem(
                new RectangleOutlineParticleEmitter(192, 900, 300, 0), 1, 4, 25, res.cloud1TextureRegion, vbom);
        particleSystem.addParticleInitializer(new VelocityParticleInitializer<UncoloredSprite>(0, 0, -50, -90));
        particleSystem.addParticleInitializer(new ExpireParticleInitializer<UncoloredSprite>(timeToLive));

        particleSystem.addParticleInitializer(new ColorParticleInitializer<UncoloredSprite>(Color.WHITE,
                new Color(0.9f, 0.9f, 0.9f)));
        particleSystem.addParticleModifier(new AlphaParticleModifier<UncoloredSprite>(timeToLive - 1, timeToLive, 1f, 0f));

        menuScene.attachChild(particleSystem);

        //Initializes the Menu Items (passes the font, text and colors).
        playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0, res.font, "PLAY", vbom), Color.CYAN, Color.WHITE);
        shopMenuItem = new ColorMenuItemDecorator(new TextMenuItem(2, res.font, "SHOP", vbom), Color.CYAN, Color.WHITE);
        soundMenuItem = new MenuSceneTextItemDecorator(new TextMenuItem(1, res.font, getSoundLabel(), vbom), Color.CYAN, Color.WHITE);
        playLeaderboards = new ColorMenuItemDecorator(new TextMenuItem(3, res.font, "LEADERBOARD", vbom), Color.CYAN, Color.WHITE);

        //Adds the menu items to the game's menu scene.
        menuScene.addMenuItem(playMenuItem);
//        menuScene.addMenuItem(shopMenuItem);
        menuScene.addMenuItem(soundMenuItem);
        menuScene.addMenuItem(playLeaderboards);

        //Enables animation of the game's menu scene.
        menuScene.buildAnimations();
        menuScene.setBackgroundEnabled(true);

        menuScene.setOnMenuItemClickListener(this);

        //Creates and attaches a player sprite into the game's menu scene.
        Sprite player = new Sprite(240, 230, res.playerTextureRegion, vbom);
        menuScene.attachChild(player);

        //Retrieves the player's high score and displays it in an Text object which is attached to the menu scene.
        Text highScoreTextLabel = new Text(240, 640, res.font, "HIGH SCORE", vbom);
        Text highScoreText = new Text(240, 585, res.font, String.valueOf(activity.getHighScore()), vbom);

        //Retrieves the player's coins and displays it with a sprite and text object.
//        Sprite coinSprite = new Sprite(240, 600, res.coinTextureRegion, vbom);
//        Text coinText = new Text(240, 550, res.font, String.valueOf(activity.getCoins()), vbom);

        //Attach all the text / sprite objects to the menu scene HUD.
        menuScene.attachChild(highScoreTextLabel);
        menuScene.attachChild(highScoreText);
//        menuScene.attachChild(coinSprite);
//        menuScene.attachChild(coinText);

        setChildScene(menuScene);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    /**
     * Returns a char sequence based on the settings for sound (on or off).
     * @return char sequennce text on or off.
     */
    private CharSequence getSoundLabel() {
        return activity.isSound() ? "SOUND ON" : "SOUND OFF";
    }

    /**
     * Gets called when the user presses the device's back key, in this case it closes the game (when the game menu is showed).
     */
    @Override
    public void onBackKeyPressed() {
        activity.finish();
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

        switch (pMenuItem.getID()) {
            //When this menu item is clicked, the game (scene) will start.
            case 0:
                SceneManager.getInstance().showGameScene();
                return true;

            //When this menu item is clicked, the sound is turned on or off.
            case 1:
                boolean soundState = activity.isSound();
                soundState = !soundState;
                activity.setSound(soundState);
                soundMenuItem.setText(getSoundLabel());
                return true;

            //The user opens the shop
            case 2:
                SceneManager.getInstance().showShopScene();
                return true;

            case 3:
                ResourceManager.getInstance().activity.startActivityForResult(
                        Games.Leaderboards.getLeaderboardIntent(ResourceManager.getInstance().activity.getGoogleApiClient(),
                                ResourceManager.getInstance().activity.getString(R.string.leaderboard_highscores)), 101);
                return true;

            default:
                return false;
        }
    }
}
