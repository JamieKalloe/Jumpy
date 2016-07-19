package net.kalloe.jumpy.entity.powerups;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import net.kalloe.jumpy.entity.CollectableEntity;
import net.kalloe.jumpy.entity.CollidableEntity;
import net.kalloe.jumpy.entity.Player;
import net.kalloe.jumpy.entity.Utils;
import net.kalloe.jumpy.shop.ShopData;
import net.kalloe.jumpy.shop.SuperJumpData;

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

    public SuperJump(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.shopData = new SuperJumpData();
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        Utils.wrapAround(this);
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

    @Override
    public ShopData getShopData() {
        return this.shopData;
    }

    @Override
    public void setShopData(ShopData data) {
        this.shopData = data;
    }

    @Override
    public void obtain(Player player) {
        this.body.setLinearVelocity(new Vector2(0, 40));
    }
}
