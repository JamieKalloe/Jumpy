package net.kalloe.jumpy.scene;

import android.widget.Toast;

import net.kalloe.jumpy.SceneManager;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.util.adt.color.Color;

/**
 * Created by Jamie on 16-4-2016.
 */
public class ShopScene extends AbstractScene implements MenuScene.IOnMenuItemClickListener {

    //Variables
    private IMenuItem option1;

    @Override
    public void populate() {

        //Creates a new MenuScene object and sets the background color.
        MenuScene shopScene = new MenuScene(camera);
        shopScene.getBackground().setColor(0.82f, 0.96f, 0.97f);

        //Initializes the Menu (shop) Items (passes the font, text and colors).
        option1 = new ColorMenuItemDecorator(new TextMenuItem(0, res.font, "1 EXTRA LIFE 1000G", vbom), Color.CYAN, Color.WHITE);

        //Adds the menu (shop) items to the game's menu scene.
        shopScene.addMenuItem(option1);

        //Make it fancy
        shopScene.setBackgroundEnabled(true);
        shopScene.setOnMenuItemClickListener(this);

        setChildScene(shopScene);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

        switch (pMenuItem.getID()) {
            case 0:
                showToast("1 Life was purchased for 1000 gold", Toast.LENGTH_LONG);
                res.activity.setCoins((res.activity.getCoins() - 1000));
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onBackKeyPressed() {
        super.onBackKeyPressed();
        SceneManager.getInstance().showMenuScene();
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
