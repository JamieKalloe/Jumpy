package net.kalloe.jumpy;

import net.kalloe.jumpy.entity.Player;

/**
 * Created by Jamie on 27-7-2016.
 */
public class Achievements {

    /**
     * Checks if the an achievement in unlockable based on the amount of points achieved.
     * @param resourceManager ResourceManager
     * @param player Player entity
     */
    public static void track(ResourceManager resourceManager, Player player){
        int totalScore = (player.getScore() + player.getBonusPoints());

        //Checks if the player has unlocked an achievement based on the total score.
        if(totalScore >= 1000) {
//            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
//                    resourceManager.activity.getString(R.string.achievement_beginner));
        }

        if(totalScore >= 5000) {
//            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
//                    resourceManager.activity.getString(R.string.achievement_novice));
        }

        if(totalScore >= 10000) {
//            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
//                    resourceManager.activity.getString(R.string.achievement_novice));
        }

        if(totalScore >= 25000) {
//            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
//                    resourceManager.activity.getString(R.string.achievement_novice));
        }

        if(totalScore >= 50000) {
//            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
//                    resourceManager.activity.getString(R.string.achievement_novice));
        }

        if(totalScore >= 75000) {
//            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
//                    resourceManager.activity.getString(R.string.achievement_novice));
        }

        if(totalScore >= 100000) {
//            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
//                    resourceManager.activity.getString(R.string.achievement_novice));
        }

        if(totalScore >= 1000000) {
//            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
//                    resourceManager.activity.getString(R.string.achievement_novice));
        }

        //Checks if the player has unlocked an achievement based on the bonuspoints (received from PowerUps).
        if(player.getBonusPoints() >= 5000) {

        }

        if(player.getBonusPoints() >= 10000) {

        }

        //Checks if the player has unlocked an achievement based on the number of enemies killed (in total).
        //TODO: increment enemies killed, 10, 25, 100, 250, 500, 1000
        //Killer, Serial Killer, crusher, MAD, Assassin, Psycho

        //TODO: increment bonus points, 5000, 10000
        //
    }
}
