package net.kalloe.jumpy.scene;

import net.kalloe.jumpy.SceneManager;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.adt.color.Color;

/**
 * Created by Jamie on 12-3-2016.
 */
public class MenuSceneWrapper extends AbstractScene implements MenuScene.IOnMenuItemClickListener {

    //Variables
    private IMenuItem playMenuItem;
    private MenuSceneTextItemDecorator soundMenuItem;

    @Override
    public void populate() {

        //Creates a new MenuScene object and sets the background color.
        MenuScene menuScene = new MenuScene(camera);
        menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);

        //Initializes the Menu Items (passes the font, text and colors).
        playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0, res.font, "PLAY", vbom), Color.CYAN, Color.WHITE);
        soundMenuItem = new MenuSceneTextItemDecorator(new TextMenuItem(1, res.font, getSoundLabel(), vbom), Color.CYAN, Color.WHITE);

        //Adds the menu items to the game's menu scene.
        menuScene.addMenuItem(playMenuItem);
        menuScene.addMenuItem(soundMenuItem);

        //Enables animation of the game's menu scene.
        menuScene.buildAnimations();
        menuScene.setBackgroundEnabled(true);

        menuScene.setOnMenuItemClickListener(this);

        //Creates and attaches a player sprite into the game's menu scene.
        Sprite player = new Sprite(240, 280, res.playerTextureRegion, vbom);
        menuScene.attachChild(player);

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

            default:
                return false;
        }
    }
}
