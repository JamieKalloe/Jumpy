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

    @Override
    public void populate() {

        //Creates a new MenuScene object and sets the background color.
        MenuScene menuScene = new MenuScene(camera);
        menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);

        //Initializes the Menu Items (passes the font, text and colors).
        playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0, res.font, "PLAY", vbom), Color.CYAN, Color.WHITE);

        //Adds the menu items to the game's menu scene.
        menuScene.addMenuItem(playMenuItem);

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
     * Gets called when the user presses the device's back key, in this case it closes the game (when the game menu is showed).
     */
    @Override
    public void onBackKeyPressed() {
        activity.finish();
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

        switch (pMenuItem.getID()) {
            case 0:
                SceneManager.getInstance().showGameScene();
                return true;

            default:
                return false;
        }
    }
}
