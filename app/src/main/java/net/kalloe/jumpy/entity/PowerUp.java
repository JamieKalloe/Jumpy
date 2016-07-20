package net.kalloe.jumpy.entity;

import com.badlogic.gdx.physics.box2d.Body;

import net.kalloe.jumpy.entity.powerups.PowerUpBehaviour;
import net.kalloe.jumpy.shop.ShopData;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Jamie on 19-7-2016.
 */
public class PowerUp extends Sprite implements CollidableEntity, CollectableEntity {

    //Variables
    public static final String TYPE = "COLLECTABLE";
    private Body body;
    private ShopData shopData;
    private PowerUpBehaviour powerUpBehaviour;

    /**
     * Creates a new instance of the Gold PowerUp entity.
     * A Gold PowerUp grants the player 10.000 to 100.000 coins worth of gold.
     * @param pX X coordinates of the Gold object / sprite.
     * @param pY Y coordinates of the Gold object / sprite.
     * @param pTextureRegion Life sprite / texture.
     * @param pVertexBufferObjectManager vbom manager.
     */
    public PowerUp(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
    }

    /**
     * Updates the Gold sprite.
     * @param pSecondsElapsed
     */
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        Utils.wrapAround(this);
    }

    /**
     * Sets the physics body of the Gold entity.
     * @param body physics body of the Gold.
     */
    @Override
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Gets the physics body of the Gold entity.
     * @return physics body of the Gold.
     */
    @Override
    public Body getBody() {
        return body;
    }

    /**
     * Returns the String type of the Gold entity.
     * @return String type.
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * Returns the ShopData of the Gold entity.
     * @return
     */
    public ShopData getShopData() {
        return this.shopData;
    }

    /**
     * Sets the ShopData of the Gold entity.
     * @param data ShopData data.
     */
    public void setShopData(ShopData data) {
        this.shopData = data;
    }

    /**
     * Returns the PowerUp behaviour of the PowerUp entity.
     * @return behaviour of the PowerUp entity.
     */
    public PowerUpBehaviour getPowerUpBehaviour() {
        return this.powerUpBehaviour;
    }

    /**
     * Sets the behaviour of the PowerUp entity.
     * @param powerUpBehaviour behaviour of the PowerUp entity.
     */
    public void setPowerUpBehaviour(PowerUpBehaviour powerUpBehaviour) {
        this.powerUpBehaviour = powerUpBehaviour;
    }

    /**
     * Grants the player 10.000 to 100.000 coins worth of gold.
     * @param player playing player.
     */
    @Override
    public void obtain(Player player) {
        this.body.setActive(false);
        this.setVisible(false);
        this.powerUpBehaviour.executePowerUp(player);
    }
}
