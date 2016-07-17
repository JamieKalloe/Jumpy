package net.kalloe.jumpy.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Jamie on 5-3-2016.
 */
public class Enemy extends AnimatedSprite implements CollidableEntity, KillableEntity {

    //Variables
    public static final String TYPE = "ENEMY";
    private Body body;
    private boolean stationary;

    /**
     * Creates a new instance of the enemy entity.
     * @param pX coordinates / postion of the enemy animated sprite.
     * @param pY coordinates / postion of the enemy animated sprite.
     * @param pITiledTextureRegion enemy animated sprite / texture
     * @param pVertexBufferObjectManager vertex buffer object manager
     */
    public Enemy(float pX, float pY, ITiledTextureRegion pITiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pITiledTextureRegion, pVertexBufferObjectManager);
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        Utils.wrapAround(this);
    }

    /**
     * Sets the physics body of the Enemy entity.
     * @param body physics body of the Enemy.
     */
    @Override
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Returns the physics body of the Enemy entity.
     * @return physics body of the Enemy.
     */
    @Override
    public Body getBody() {
        return this.body;
    }

    /**
     * Returns the String type of the Enemy entity.
     * @return String type
     */
    @Override
    public String getType() {
        return TYPE;
    }

    public boolean getStationary() {
        return this.stationary;
    }

    public void setStationary(boolean stationary) {
        this.stationary = stationary;
    }

    /**
     * Removes the enemy from the game.
     */
    @Override
    public void die() {
        if(this.stationary) {
            this.body.setActive(false);
            setCurrentTileIndex(1);
        } else {
            this.setFlipped(true, true);
            this.body.setLinearVelocity(new Vector2(0, -20));
        }
    }
}
