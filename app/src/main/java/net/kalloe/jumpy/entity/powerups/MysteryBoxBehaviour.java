package net.kalloe.jumpy.entity.powerups;

import android.util.Log;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.Player;

/**
 * Created by Jamie on 20-7-2016.
 */
public class MysteryBoxBehaviour implements PowerUpBehaviour {

    /**
     * Grants the player a random buff or debuff.
     * @param player entity which receives the PowerUp.
     */
    @Override
    public void executePowerUp(Player player) {

        switch ((int)(Math.random() * 10)) {
            case 0:
                Log.d("powerup", "Player received a life or (random) gold");
                new LifeBehaviour().executePowerUp(player);
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
                new GoldBehaviour().executePowerUp(player);
                break;

            case 3:
                new SuperJumpBehaviour().executePowerUp(player);
                break;

            default:
                Log.d("powerup", "Mysterybox default was called");
                ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundHit);
                if(player.getHealth() == 0) {
                    player.die();
                } else {
                    player.dealDamage();
                }
                break;
        }
    }
}
