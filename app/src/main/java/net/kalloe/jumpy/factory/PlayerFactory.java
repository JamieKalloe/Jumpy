package net.kalloe.jumpy.factory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.Player;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Jamie on 28-2-2016.
 */
public class PlayerFactory {

    //Variables
    private static PlayerFactory INSTANCE = new PlayerFactory();
    private VertexBufferObjectManager vbom;
    private PhysicsWorld physicsWorld;
    public static final FixtureDef PLAYER_FIXTURE = PhysicsFactory.createFixtureDef(1f, 0f, 1f, false);

    //Singleton
    private PlayerFactory() { }

    /**
     * Returns the singleton instance of the PlayerFactory.
     * @return
     */
    public static PlayerFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Sets the VertexBufferObjectManager for the PlayerFactory.
     * @param vbom Uploads vertex data (points, colors, vectors) into the video memory.
     */
    public void create(PhysicsWorld physicsWorld, VertexBufferObjectManager vbom) {
        this.physicsWorld = physicsWorld;
        this.vbom = vbom;
    }

    /**
     * Creates a new instance of the Player with specifed x and y coordinates.
     * @param x x coordinates of the player.
     * @param y y coordinates of the player.
     * @return new instance of a player.
     */
    public Player createPlayer(float x, float y) {
        //Creates a new Player object with specified x and y coordinates.
        Player player = new Player(x, y, ResourceManager.getInstance().playerTextureRegion, vbom);

        //Sets the z-index (z-coordinate) of the player (on which texture layer the player is displayed).
        player.setZIndex(2);

        //Creates the physical Body of the Player entity (resembles the sprite, optimized shape for collision).
        Body playerBody = PhysicsFactory.createBoxBody(physicsWorld, player, BodyDef.BodyType.DynamicBody, PLAYER_FIXTURE);
        playerBody.setLinearDamping(1f);
        playerBody.setFixedRotation(true);

        //Binds the Player object to the physics body of the player in the physics world (whole simulation).
        playerBody.setUserData(player);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(player, playerBody));

        //Set the playerBody to the Player entity.
        player.setBody(playerBody);

        return player;
    }


}
