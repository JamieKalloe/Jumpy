package net.kalloe.jumpy;

import android.os.AsyncTask;

import net.kalloe.jumpy.scene.AbstractScene;
import net.kalloe.jumpy.scene.LoadingScene;
import net.kalloe.jumpy.scene.MenuSceneWrapper;
import net.kalloe.jumpy.scene.SplashScene;

import org.andengine.util.debug.Debug;

/**
 * Created by Jamie on 12-3-2016.
 */
public class SceneManager {

    //Only a single instance of the scene manager gets created.
    private static final SceneManager INSTANCE = new SceneManager();
    public static final long SPLASH_DURATION = 2000;

    private ResourceManager res = ResourceManager.getInstance();
    private AbstractScene currentScene;
    private LoadingScene loadingScene = null;

    //Singleton
    private SceneManager() {}

    /**
     * Returns the singleton instance of the SceneManager.
     * @return Instance of the SceneManager.
     */
    public static SceneManager getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the current Scene.
     * @return the current scene.
     */
    public AbstractScene getCurrentScene() {
        return currentScene;
    }

    /**
     * This methods creates and extra thread to initialize the SplashScene and show it.
     * @return the splash scene.
     */
    public AbstractScene showSplashAndMenuScene() {
        final SplashScene splashScene = new SplashScene();
        splashScene.populate();
        setCurentScene(splashScene);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //Loads and initializes all the game resources from the ResourceManager.
                long timeStamp = System.currentTimeMillis();
                res.loadFont();
                res.loadGameAudio();
                res.loadGameGraphics();

                //Creates a new instance of the LoadingScene and initializes it.
                loadingScene = new LoadingScene();
                loadingScene.populate();

                AbstractScene nextScene = new MenuSceneWrapper();

                //Freezes the SplashScene as long as it has not reached 2 full seconds.
                if(System.currentTimeMillis() - timeStamp < SPLASH_DURATION) {
                    try {
                        Thread.sleep(SPLASH_DURATION - (System.currentTimeMillis() - timeStamp));
                    } catch (InterruptedException e) {
                        Debug.e("Interrupted", e);
                    }
                }

                //Initializes the next scene.
                nextScene.populate();
                setCurentScene(nextScene);

                //Destroys the SplashScene and unloads the SplashScene graphics from the Engine to free memory.
                splashScene.destory();
                res.unloadSplashGraphics();

                return null;
            }
        }.execute();
        return splashScene;
    }

    /**
     * Sets the specified scene to the engine.
     * @param curentScene the current scene.
     */
    public void setCurentScene(AbstractScene curentScene) {
        this.currentScene = curentScene;
        res.engine.setScene(curentScene);
        Debug.i("Current scene: " + curentScene.getClass().getName());
    }
}
