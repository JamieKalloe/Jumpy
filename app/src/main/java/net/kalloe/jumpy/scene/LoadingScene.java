package net.kalloe.jumpy.scene;

import net.kalloe.jumpy.GameActivity;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.text.Text;

/**
 * Created by Jamie on 12-3-2016.
 */
public class LoadingScene extends AbstractScene {

    @Override
    public void populate() {

    }

    @Override
    public void onPause() {
        CameraScene cameraScene = new CameraScene(camera);

        Text text = new Text(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2,
                res.font, "LOADING", vbom);
        cameraScene.attachChild(text);

        setChildScene(cameraScene);
    }

    @Override
    public void onResume() {

    }
}
