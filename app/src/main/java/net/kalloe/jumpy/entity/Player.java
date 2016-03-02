package net.kalloe.jumpy.entity;

import com.badlogic.gdx.physics.box2d.Body;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Jamie on 28-2-2016.
 */
public class Player extends TiledSprite implements CollidableEntity {

    //Variables
    boolean dead = false;
    private Body body;
    public static final String TYPE = "Player";

    /**
     * Creates a new instance of the Player entity.
     * @param pX X coordinates / postion of the player sprite.
     * @param pY Y coordinates / position of the player sprite.
     * @param pTiledTextureRegion Player sprite / texture
     * @param pVertexBufferObjectManager vbom manager.
     */
    public Player(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    /**
     * Changes the Player entity sprite based on the up or down movement.
     * @param pSecondsElapsed
     */
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        if(getCurrentTileIndex() < 2) {
            if(body.getLinearVelocity().y < 0) {
                fall();
            } else {
                fly();
            }
        }
    }

    /**
     * Sets the physics body of the Player entity.
     * @param body physics body of the Player.
     */
    @Override
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Sets the physics body of the Player entity.
     * @return the physics body of the Player.
     */
    @Override
    public Body getBody() {
        return this.body;
    }

    /**
     * Returns the String type of the Player entity.
     * @return String type
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * Returns the (playing) state of the Player object.
     * @return Boolean dead
     */
    public boolean isDead() {
        return this.dead;
    }

    /**
     * Sets the (playing) state of the Player object.
     * @param dead Boolean
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Mirrors / flips the displayed Player Sprite (tile) to the left.
     */
    public void turnLeft() {
        setFlippedHorizontal(true);
    }

    /**
     * Mirrors  / flips the displayed Player Sprite (tile) to the right.
     */
    public void turnRight() {
        setFlippedHorizontal(false);
    }

    /**
     * Sets the displayed Player Sprite tile to the first (0) image.
     */
    public void fly() {
        setCurrentTileIndex(0);
    }

    /**
     * Sets the displayed Player Sprite tile to the second (1) image.
     */
    public void fall() {
        setCurrentTileIndex(1);
    }

    /**
     * Sets the (playing) state of the Player to false.
     * Sets the displayed Sprite of the Player to the last sprite.
     */
    public void die() {
        this.setDead(true);
        setCurrentTileIndex(2);
    }
}
