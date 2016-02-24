package net.kalloe.jumpy;

import android.view.MenuItem;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import java.io.IOException;

public class GameActivity extends BaseGameActivity {

    //Variables
    public static final int CAMERA_WDITH = 480;
    public static final int CAMERA_HEIGHT = 800;

    /**
     * This method defines the (AndEngine) engine options. It's run by the onCreate method first.
     * @return EngineOptions engine options of AndEngine.
     */
    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera visibleArea = new Camera(0, 0, CAMERA_WDITH, CAMERA_HEIGHT);
        IResolutionPolicy sceneScaling = new FillResolutionPolicy();
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                sceneScaling, visibleArea);
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        Debug.i("Engine configured");

        return engineOptions;
    }

    /**
     * The onCreateResources is used to initialize game resources (graphics, sounds and music).
     * This method is called after the engine options are created.
     * @param pOnCreateResourcesCallback
     * @throws IOException
     */
    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        //empty for now.
    }

    /**
     * This method is called from the onCreateResourcesCallback object when the Finished method is called.
     * This method is used to create a scene (or screen) object or objects.
     * The finished method is called from the SceneCallBack to let the engine know the scene is created.
     * @param pOnCreateSceneCallback
     * @throws IOException
     */
    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        Scene scene = new Scene();
        scene.getBackground().setColor(Color.CYAN);
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    /**
     * This method is used to populate the scene with entities.
     * @param pScene
     * @param pOnPopulateSceneCallback
     * @throws IOException
     */
    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
