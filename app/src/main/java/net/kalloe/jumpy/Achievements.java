package net.kalloe.jumpy;

import com.google.android.gms.games.Games;

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
            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                    resourceManager.activity.getString(R.string.achievement_beginner));
        }

        if(totalScore >= 5000) {
            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                    resourceManager.activity.getString(R.string.achievement_novice));
        }

        if(totalScore >= 10000) {
            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                    resourceManager.activity.getString(R.string.achievement_ace));
        }

        if(totalScore >= 25000) {
            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                    resourceManager.activity.getString(R.string.achievement_expert));
        }

        if(totalScore >= 50000) {
            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                    resourceManager.activity.getString(R.string.achievement_master));
        }

        if(totalScore >= 75000) {
            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                    resourceManager.activity.getString(R.string.achievement_grand_master));
        }

        if(totalScore >= 100000) {
            Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                    resourceManager.activity.getString(R.string.achievement_sage));
        }

        //Checks if the player has unlocked an achievement by incrementing the achievements requirement for bonuspoints.
        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                resourceManager.activity.getString(R.string.achievement_lucky), player.getBonusPoints());

        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                resourceManager.activity.getString(R.string.achievement_hunter), player.getBonusPoints());

        //Checks if the player has unlocked an achievement by incrementing the achievements requirement for enemies killed.
        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                resourceManager.activity.getString(R.string.achievement_killer), player.getEnemiesKilled());

        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                resourceManager.activity.getString(R.string.achievement_serial_killer), player.getEnemiesKilled());

        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                resourceManager.activity.getString(R.string.achievement_crusher), player.getEnemiesKilled());

        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                resourceManager.activity.getString(R.string.achievement_mad), player.getEnemiesKilled());

        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                resourceManager.activity.getString(R.string.achievement_assassin), player.getEnemiesKilled());

        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                resourceManager.activity.getString(R.string.achievement_psycho), player.getEnemiesKilled());

        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                resourceManager.activity.getString(R.string.achievement_warlord), player.getEnemiesKilled());
    }
}
