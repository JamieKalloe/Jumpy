package net.kalloe.jumpy.scene;

import net.kalloe.jumpy.GameActivity;
import net.kalloe.jumpy.ResourceManager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

/**
 * Created by Jamie on 28-2-2016.
 */
public abstract class AbstractScene extends Scene {

    //Variables
    protected ResourceManager res = ResourceManager.getInstance();
    protected Engine engine = res.engine;
    protected GameActivity activity = res.activity;
    protected VertexBufferObjectManager vbom = res.vbom;
    protected Camera camera = res.camera;

    //Methods
    public abstract void populate();

    public void destory() {

    }

    public void onBackKeyPressed() {
        Debug.d("Back key pressed");
    }

    public abstract void onPause();

    public abstract void onResume();
}
