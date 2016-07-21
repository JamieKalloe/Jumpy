package net.kalloe.jumpy.entity.powerups;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.Player;

/**
 * Created by Jamie on 20-7-2016.
 */
public class Gold implements PowerUpBehaviour {

    /**
     * Grants the player a random amount of gold between 100.000 and 1.000.000.
     * @param player entity which receives the PowerUp.
     */
    @Override
    public void executePowerUp(Player player) {
        player.addBonusPoints((int) (Math.random() * 10000));
        ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundCash);
    }
}
