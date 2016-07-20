package net.kalloe.jumpy.entity.powerups;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.Player;

/**
 * Created by Jamie on 20-7-2016.
 */
public class LifeBehaviour implements PowerUpBehaviour {

    @Override
    public void executePowerUp(Player player) {
        ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundCash);

        //If the health of the player is not full, the player will gain 1 health.
        if(player.getHealth() != 3) {
            player.setHealth((player.getHealth() + 1));
        } else {
            player.addCoins(10000);
        }
    }
}
