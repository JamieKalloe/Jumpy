package net.kalloe.jumpy;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.IEntity;

/**
 * Created by Jamie on 5-3-2016.
 */
public class EntityCamera extends SmoothCamera {

    //Variables
    private IEntity chaseEntity;
    private boolean gameOver = false;

    /**
     * Creates a new instance of the EntityCamera (which enherits the SmoothCamera).
     * @param pX x coordinates of the entity camera.
     * @param pY y coordinate of the entity camera.
     * @param pWidth width (view) of the entity camera.
     * @param pHeight height (view) of the entity camera.
     */
    public EntityCamera(float pX, float pY, float pWidth, float pHeight) {
        super(pX, pY, pWidth, pHeight, 3000f, 1000f, 1f);
    }

    /**
     * Sets the entity (or game object) which the player focusses on.
     * @param pChaseEntity game entity which requires focus.
     */
    @Override
    public void setChaseEntity(IEntity pChaseEntity) {
        super.setChaseEntity(pChaseEntity);
        this.chaseEntity = pChaseEntity;
    }

    /**
     * Sets the view of the camera according to certain actions the chosen entity makes.
     */
    @Override
    public void updateChaseEntity() {
        if(chaseEntity != null) {
            if(chaseEntity.getY() > getCenterY()) {
                setCenter(getCenterX(), chaseEntity.getY());
            }

            //player dies
            else if(chaseEntity.getY() < getYMin() && !gameOver) {
                setCenter(getCenterX(), chaseEntity.getY() - getHeight());
                gameOver = true;
            }
        }
    }
}
