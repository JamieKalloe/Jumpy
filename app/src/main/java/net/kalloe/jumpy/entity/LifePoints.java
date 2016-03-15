package net.kalloe.jumpy.entity;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Jamie on 15-3-2016.
 */
public class LifePoints extends TiledSprite {

    //Variables
    private int lifePoints = 3;
    private int tileIndex = 0;

    public LifePoints(ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(0, 0, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    public void dealDamage(int damage) {
        this.lifePoints--;
        tileIndex++;
        setCurrentTileIndex(tileIndex);
    }

    public int getLifePoints() {
        return this.lifePoints;
    }
}
