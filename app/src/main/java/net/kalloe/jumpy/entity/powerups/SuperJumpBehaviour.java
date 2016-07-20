package net.kalloe.jumpy.entity.powerups;

import com.badlogic.gdx.math.Vector2;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.Player;

/**
 * Created by Jamie on 20-7-2016.
 */
public class SuperJumpBehaviour implements PowerUpBehaviour {

    /**
     * Grants the player one high jump.
     * @param player entity which receives the PowerUp.
     */
    @Override
    public void executePowerUp(Player player) {
        player.getBody().setLinearVelocity(new Vector2(0, 65));
        ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundJump);
    }
}
