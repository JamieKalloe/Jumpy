package net.kalloe.jumpy.factory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.Platform;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.Constants;

import java.util.Random;

/**
 * Created by Jamie on 5-3-2016.
 */
public class PlatformFactory {

    //Variables
    private static final FixtureDef PLATFORM_FIXTURE = PhysicsFactory.createFixtureDef(0f, 0f, 1f, false);

    private static PlatformFactory INSTANCE = new PlatformFactory();
    private PhysicsWorld physicsWorld;
    private VertexBufferObjectManager vbom;
    private Random random;

    //Singleton
    private PlatformFactory() {}

    /**
     * Returns the singleton instance of the PlatformFactory.
     * @return PlatformFactory
     */
    public static PlatformFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Sets the physicsworld and vertexbufferobjectmanager to the PlatformFactory.
     * @param physicsWorld physics world
     * @param vbom Vertex Buffer Object Manager
     */
    public void create(PhysicsWorld physicsWorld, VertexBufferObjectManager vbom) {
        this.physicsWorld = physicsWorld;
        this.vbom = vbom;
        this.random = new Random();
    }

    /**
     * Creates a static platform with the specified x and y coordinates.
     * @param x x coordinates of the platform.
     * @param y y coordienates of the platform.
     * @return static (non-moving) platform.
     */
    public Platform createPlatform(float x, float y) {
        //Creates a new static (non-moving) platform for the given x and y coordinates.
        Platform platform;
        if(this.random.nextBoolean()) {
            platform = new Platform(x, y, ResourceManager.getInstance().platformGrassTextureRegion, vbom);
        } else {
            platform = new Platform(x, y, ResourceManager.getInstance().platformSandTextureRegion, vbom);
        }

        //Sets the platform to the center.
        platform.setAnchorCenterY(1);
        platform.setFlippedHorizontal(this.random.nextBoolean());

        //Gets the x and y (center) coordinates for the given scene.
        final float[] sceneCenterCoordinates = platform.getSceneCenterCoordinates();
        final float centerX = sceneCenterCoordinates[Constants.VERTEX_INDEX_X];
        final float centerY = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y];

        //Creates the physical Body of the Platform entity (resembles the sprite, optimized shape for collision).
        Body platformBody = PhysicsFactory.createBoxBody(physicsWorld, centerX, centerY, platform.getWidth() - 20,
                1, BodyDef.BodyType.KinematicBody, PLATFORM_FIXTURE);

        //Binds the Platform object to the physics body of the platform in the physics world (whole simulation).
        platformBody.setUserData(platform);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(platform, platformBody));
        platform.setBody(platformBody);

        return platform;
    }

    /**
     * Creates a non-static (moving) platform.
     * @param x x coordinates of the platform.
     * @param y y coordinates of the platform.
     * @param velocity speed of the moving platform.
     * @return
     */
    public Platform createMovingPlatform(float x, float y, float velocity) {
        //Creates a new static (non-moving) platform for the given x and y coordinates.
        Platform platform = createPlatform(x, y);

        //Sets the moving speed (velocity) of the platform (platform now moves).
        platform.getBody().setLinearVelocity(velocity, 0);

        return platform;
    }

}
