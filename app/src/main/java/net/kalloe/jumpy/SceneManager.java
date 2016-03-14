package net.kalloe.jumpy;

import android.os.AsyncTask;

import net.kalloe.jumpy.scene.AbstractScene;
import net.kalloe.jumpy.scene.GameScene;
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
        setCurrentScene(splashScene);

        //Creates a new async task (background thread) to initialize and show the splash scene.
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
                setCurrentScene(nextScene);

                //Destroys the SplashScene and unloads the SplashScene graphics from the Engine to free memory.
                splashScene.destory();
                res.unloadSplashGraphics();

                return null;
            }
        }.execute();
        return splashScene;
    }

    /**
     * Creates a extra thread to initialize and start a new instance of the game scene.
     */
    public void showGameScene() {
        //Gets the current scene and sets the loading as the current scene.
        final AbstractScene previousScene = getCurrentScene();
        setCurrentScene(loadingScene);

        //Creates a new async task (background thread) to initialize and start the game scene.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    Debug.e("Interrupted", e);
//                }

                //Initializes and sets new instance of the game scene as the current scene.
                GameScene gameScene = new GameScene();
                gameScene.populate();
                setCurrentScene(gameScene);

                //Destroys the previous scene to free memory from the engine.
                previousScene.destory();

                return null;
            }
        }.execute();
    }

    /**
     * Creates a extra thread to initialize and start a new instance of the menu scene.
     */
    public void showMenuScene() {
        //Gets the current scene and sets the loading as the current scene.
        final AbstractScene previousScene = getCurrentScene();
        setCurrentScene(loadingScene);

        //Creates a new async task (background thread) to initialize and start the menu scene.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //Creates and initializes a new instance of the menu scene.
                MenuSceneWrapper menuSceneWrapper = new MenuSceneWrapper();
                menuSceneWrapper.populate();
                setCurrentScene(menuSceneWrapper);

                //Destroys the previous scene to free memory from the engine.
                previousScene.destory();

                return null;
            }
        }.execute();
    }

    /**
     * Sets the specified scene to the engine.
     * @param currentScene the current scene.
     */
    public void setCurrentScene(AbstractScene currentScene) {
        this.currentScene = currentScene;
        res.engine.setScene(currentScene);
        Debug.i("Current scene: " + currentScene.getClass().getName());
    }
}
