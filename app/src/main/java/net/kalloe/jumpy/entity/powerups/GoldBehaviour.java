package net.kalloe.jumpy.entity.powerups;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.Player;

/**
 * Created by Jamie on 20-7-2016.
 */
public class GoldBehaviour implements PowerUpBehaviour {

    @Override
    public void executePowerUp(Player player) {
        player.addCoins((int)(Math.random() * 100000));
        ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundCash);
    }
}
