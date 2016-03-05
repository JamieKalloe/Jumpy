package net.kalloe.jumpy.entity;

import com.badlogic.gdx.physics.box2d.Body;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Jamie on 5-3-2016.
 */
public class Platform extends Sprite implements CollidableEntity {

    //Variables
    private Body body;
    public static final String TYPE = "Platform";

    /**
     * Creates a new instance of the Platform entity.
     * @param pX X coordinates / postion of the platform sprite.
     * @param pY Y coordinates / position of the platform sprite.
     * @param pTextureRegion platform sprite / texture
     * @param pVertexBufferObjectManager Vertex Buffer Object Manager
     */
    public Platform(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        Utils.wrapAround(this);
    }

    /**
     * Sets the physics body of the Platform entity.
     * @param body physics body of the Player.
     */
    @Override
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Gets the physics body of the Platform entity.
     * @return physics body of the Player.
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
}
