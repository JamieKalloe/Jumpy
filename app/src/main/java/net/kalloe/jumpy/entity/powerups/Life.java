package net.kalloe.jumpy.entity.powerups;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.Player;

/**
 * Created by Jamie on 20-7-2016.
 */
public class Life implements PowerUpBehaviour {

    /**
     * Grants the player +1 health point or 10.000 gold if the health is full (3).
     * @param player entity which receives the PowerUp.
     */
    @Override
    public void executePowerUp(Player player) {
        ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundPowerUp);

        //If the health of the player is not full, the player will gain 1 health.
        if(player.getHealth() != 3) {
            player.setHealth((player.getHealth() + 1));
        } else {
            player.addBonusPoints(5000);
        }
    }
}
