package net.kalloe.jumpy.entity;

import net.kalloe.jumpy.GameActivity;

import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;

/**
 * Created by Jamie on 5-3-2016.
 */
public class Utils {

    /**
     * This method allows the player entity to reappear at the other side of the screen (when leaving right of left).
     * @param collidableEntity game collidable entity
     */
    public static void wrapAround(CollidableEntity collidableEntity) {
        if(collidableEntity.getX() + collidableEntity.getWidth() / 2 < 0) {
            collidableEntity.getBody().setTransform((GameActivity.CAMERA_WIDTH + collidableEntity.getWidth() / 2) /
                    PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, collidableEntity.getBody().getPosition().y, 0);
        }

        else if(collidableEntity.getX() - collidableEntity.getWidth() / 2 > GameActivity.CAMERA_WIDTH) {
            collidableEntity.getBody().setTransform((- collidableEntity.getWidth() / 2) /
            PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, collidableEntity.getBody().getPosition().y, 0);
        }
    }

}
