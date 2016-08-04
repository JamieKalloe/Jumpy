package net.kalloe.jumpy;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import java.io.IOException;

public class GameActivity extends BaseGameActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //Variables
    public static final int CAMERA_WIDTH = 480;
    public static final int CAMERA_HEIGHT = 800;

    //SharedPreferences (for saving settings and scores)
    private final String KEY_SOUND = "Sound";
    private final String KEY_HIGHSCORE = "Highscore";
    private final String KEY_COINS = "Coins";

    private SharedPreferences settings;
    private GoogleApiClient googleApiClient;
    private AdView banner;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            this.googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                    .setViewForPopups(findViewById(android.R.id.content))
                    .build();
        }

        if(this.banner != null) {
            this.banner.loadAd(adRequest);
        }
    }

    @Override
    protected void onSetContentView() {
        final FrameLayout frameLayout = new FrameLayout(this);
        final FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.FILL);
        final FrameLayout.LayoutParams adViewLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.BOTTOM);


        this.banner = new AdView(this);
        this.adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4")  // An example device ID
                .build();

        this.mRenderSurfaceView = new RenderSurfaceView(this);
        mRenderSurfaceView.setRenderer(mEngine, this);



        final FrameLayout.LayoutParams surfaceViewLayoutParams = new FrameLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        surfaceViewLayoutParams.gravity = Gravity.CENTER;

        frameLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);

        this.setContentView(frameLayout, frameLayoutLayoutParams);

    }

    /**
     * This method defines the (AndEngine) engine options. It's run by the onCreate method first.
     * @return EngineOptions engine options of AndEngine.
     */
    @Override
    public EngineOptions onCreateEngineOptions() {
        //Sets the visible (screen) area, passing in the required screen resolution.
        Camera visibleArea = new EntityCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        //Scales the visible (screen) area to fill the entire screen.
        IResolutionPolicy sceneScaling = new FillResolutionPolicy();

        //Sets the screen rotation / mode to portrait (disables landscape rotation mode).
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                sceneScaling, visibleArea);

        //Enables the use of game audio / music.
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);

        //Sets the wake / lock options; the screen will never dim or turn off after x time.
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

        //Improves the image rendering, the engine will now use 32-bit colors using small dot sof 16-bit colors.
        engineOptions.getRenderOptions().setDithering(true);

        //Initializes the shared preferences for the game.
        settings = getSharedPreferences("jumpy_game_prefs", MODE_PRIVATE);

        Debug.i("Engine configured");

        return engineOptions;
    }

    /**
     * The onCreateResources is used to initialize game resources (graphics, fonts, sounds and music).
     * This method is called after the engine options are created.
     * @param pOnCreateResourcesCallback
     * @throws IOException
     */
    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        //Instantiate the singleton ResourceManager, passing all the important objects.
        ResourceManager.getInstance().create(this, getEngine(), getEngine().getCamera(), getVertexBufferObjectManager());

        //Load in the game splash resources from the ResourceManager.
        ResourceManager.getInstance().loadSplashGraphics();

        Debug.i("Successfully loaded all the game splash resources");

        //Call the callback, indication we are done loading the game resources.
        pOnCreateResourcesCallback.onCreateResourcesFinished();
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
        pOnCreateSceneCallback.onCreateSceneFinished(null);
        Debug.i("Scene configured");
    }

    /**
     * This method is used to populate the scene with entities.
     * @param pScene
     * @param pOnPopulateSceneCallback
     * @throws IOException
     */
    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        //Initializes and shows the SplashScreen scene and the game menu scene.
        SceneManager.getInstance().showSplashAndMenuScene();

        //Tells the engine the scene is done populating (loading of resources for the scene).
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    /**
     * Handles the user input of the back key.
     * @param keyCode code for the generated event.
     * @param event event object.
     * @return boolean pressed.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //Calls the appropriate scene when the user presses the back key.
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public synchronized void onResumeGame() {
        super.onResumeGame();
        SceneManager.getInstance().getCurrentScene().onResume();
    }

    @Override
    public synchronized void onPauseGame() {
        super.onPauseGame();
        SceneManager.getInstance().getCurrentScene().onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            this.googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            this.googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        showToast("You have signed in", Toast.LENGTH_SHORT);
    }

    @Override
    public void onConnectionSuspended(int i) {
        this.googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, connectionResult.getErrorCode());
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED && requestCode == 101) {
            this.getGoogleApiClient().disconnect();
            this.showToast("You have signed out", 100);
        }

        if(resultCode == RESULT_OK) {
            this.googleApiClient.connect();
        }
    }

    public GoogleApiClient getGoogleApiClient() {
        return this.googleApiClient;
    }

    /**
     * Enables or disables the game's background music settings (with sharedpreferences).
     * @param sound true / false indicating if the sound should be set on or off.
     */
    public void setSound(boolean sound) {
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putBoolean(KEY_SOUND, sound);
        settingsEditor.commit();
    }

    /**
     * Retrieves the user's settings for sound (game background music).
     * @return boolean indicating if the user has sound enabled or disabled.
     */
    public boolean isSound() {
        return settings.getBoolean(KEY_SOUND, true);
    }

    /**
     * Plays the given sound resource.
     * @param soundToPlay sound resource.
     */
    public void playSound(Sound soundToPlay) {
        if(isSound()) {
            soundToPlay.play();
        }
    }

    /**
     * Sets (saves) the highscore the player has achieved.
     * @param score score the player has achieved.
     */
    public void setHighScore(int score) {
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putInt(KEY_HIGHSCORE, score);
        settingsEditor.commit();
    }

    /**
     * Retrieves the highscore of the player.
     * @return int highscore.
     */
    public int getHighScore() {
        return settings.getInt(KEY_HIGHSCORE, 0);
    }

    /**
     * Sets (saves) the coins the player was awarded.
     * @param coins coins the player was awarded.
     */
    public void setCoins(int coins) {
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putInt(KEY_COINS, coins);
        settingsEditor.commit();
    }

    /**
     * Retrieves the coins of the player.
     * @return int coins.
     */
    public int getCoins() {
        return settings.getInt(KEY_COINS, 0);
    }

    /**
     * Creates and shows a Toast message using the Android Toast widget.
     * A Toast is run on te UI Thread which every activity has.
     * @param text Text to be displayed by the Toast.
     * @param length Length of the Toast to be displayed.
     */
    public void showToast(final String text, final int length) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, length).show();
            }
        });
    }
}
