package net.kalloe.jumpy.scene;

import net.kalloe.jumpy.GameActivity;

import org.andengine.entity.sprite.Sprite;
import org.andengine.util.adt.color.Color;

/**
 * Created by Jamie on 12-3-2016.
 */
public class SplashScene extends AbstractScene {

    @Override
    public void populate() {
        //Loads the SplashScreen resources into the splashSprite object.
        Sprite splashSprite = new Sprite(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2,
                res.splashTextureRegion, vbom);

        getBackground().setColor(Color.WHITE);

        //Attaches the splashSprite object to the SplashScene scene.
        attachChild(splashSprite);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
