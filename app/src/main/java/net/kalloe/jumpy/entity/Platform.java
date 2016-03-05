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
    private static final String TYPE = "Platform";

    public Platform(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
    }

    @Override
    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public Body getBody() {
        return this.body;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
