package net.kalloe.jumpy.factory;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.Player;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Jamie on 28-2-2016.
 */
public class PlayerFactory {

    //Variables
    private static PlayerFactory INSTANCE = new PlayerFactory();
    private VertexBufferObjectManager vbom;

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
    public void create(VertexBufferObjectManager vbom) {
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

        return player;
    }

}
