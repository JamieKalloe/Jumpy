package net.kalloe.jumpy.entity.powerups;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.CollectableEntity;
import net.kalloe.jumpy.entity.CollidableEntity;
import net.kalloe.jumpy.entity.Player;
import net.kalloe.jumpy.entity.Utils;
import net.kalloe.jumpy.shop.MysteryBoxData;
import net.kalloe.jumpy.shop.ShopData;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Random;

/**
 * Created by Jamie on 16-7-2016.
 */
public class MysteryBox extends Sprite implements CollidableEntity, CollectableEntity  {

    //Variables
    public static final String TYPE = "COLLECTABLE";
    private Body body;
    private ShopData shopData;
    private Random random;

    public MysteryBox(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.shopData = new MysteryBoxData();
        this.random = new Random();
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
        this.body.setActive(false);
        this.setVisible(false);

        if(!player.isDead()) {
            switch (random.nextInt(3 - 0 + 1) + 0) {
                case 0:
                    Log.d("powerup", "Player received a life or (random) gold");
                    if(player.getHealth() != 3) {
                        player.setHealth((player.getHealth() + 1));
                        //TODO: obtain / life sound
                    } else {
                        player.addCoins((shopData.getPrice()));
                        ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundCash);
                    }
                    break;

                case 1:
                    Log.d("powerup", "Player got hit, receiving 1 damage (or dying)");
                    ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundHit);
                    if(player.getHealth() == 0) {
                        player.die();
                    } else {
                        player.dealDamage();
                    }
                    break;

                case 2:
                    Log.d("powerup", "Player received random gold");
                    player.addCoins((shopData.getPrice()));
                    ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundCash);
                    break;

                case 3:
                    Log.d("powerup", "Player received a super jump");
                    player.getBody().setLinearVelocity(new Vector2(0, 65));
                    ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundJump);
                    break;
            }
        }
    }
}
