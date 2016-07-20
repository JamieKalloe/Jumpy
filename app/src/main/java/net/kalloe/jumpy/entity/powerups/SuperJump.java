package net.kalloe.jumpy.entity.powerups;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.CollectableEntity;
import net.kalloe.jumpy.entity.CollidableEntity;
import net.kalloe.jumpy.entity.Player;
import net.kalloe.jumpy.entity.Utils;
import net.kalloe.jumpy.shop.ShopData;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Jamie on 19-7-2016.
 */
public class SuperJump extends Sprite implements CollidableEntity, CollectableEntity {

    private static final String TYPE = "COLLECTABLE";
    private Body body;
    private ShopData shopData;

    /**
     * Creates a new instance of the SuperJump PowerUp entity.
     * A SuperJump PowerUp grants the player a very high jump once.
     * @param pX X coordinates of the SuperJump object / sprite.
     * @param pY Y coordinates of the SuperJump object / sprite.
     * @param pTextureRegion SuperJump sprite / texture.
     * @param pVertexBufferObjectManager vbom manager.
     */
    public SuperJump(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
    }

    /**
     * Updates the SuperJump sprite.
     * @param pSecondsElapsed
     */
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        Utils.wrapAround(this);
    }

    /**
     * Sets the physics body of the SuperJump entity.
     * @param body physics body of the SuperJump.
     */
    @Override
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Gets the physics body of the SuperJump entity.
     * @return physics body of the SuperJump.
     */
    @Override
    public Body getBody() {
        return this.body;
    }

    /**
     * Returns the String type of the SuperJump entity.
     * @return String type.
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * Returns the ShopData of the SuperJump entity.
     * @return
     */
    @Override
    public ShopData getShopData() {
        return this.shopData;
    }

    /**
     * Sets the ShopData of the SuperJump entity.
     * @param data ShopData data.
     */
    @Override
    public void setShopData(ShopData data) {
        this.shopData = data;
    }

    /**
     * Grants the player a very high jump once.
     * @param player playing player.
     */
    @Override
    public void obtain(Player player) {
        this.body.setActive(false);
        this.setVisible(false);
        player.getBody().setLinearVelocity(new Vector2(0, 65));
        ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundJump);
    }
}
