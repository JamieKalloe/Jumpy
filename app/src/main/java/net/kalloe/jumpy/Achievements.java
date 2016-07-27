package net.kalloe.jumpy;

import android.os.AsyncTask;

import com.google.android.gms.games.Games;

import net.kalloe.jumpy.entity.Player;

import org.andengine.util.debug.Debug;

/**
 * Created by Jamie on 27-7-2016.
 */
public class Achievements {

    /**
     * Checks if the an achievement in unlockable based on the amount of points achieved.
     * @param resourceManager ResourceManager
     * @param player Player entity
     */
    public static void track(final ResourceManager resourceManager, final Player player){
        final int totalScore = (player.getScore() + player.getBonusPoints());
        final int enemiesKilled = player.getEnemiesKilled();
        final int bonusPoints = player.getBonusPoints();

        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {

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

                    if(bonusPoints >= 5000) {
                        Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_lucky));
                    }

                    if(bonusPoints >= 10000) {
                        Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_hunter));
                    }

                    if(bonusPoints >= 25000) {
                        Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_the_chosen_one));
                    }

                    if(player.getHealth() == 0) {
                        Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_killed_in_action));
                    }

                    if(player.getHealth() != 0 && player.getHealth() < 3) {
                        Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_wounded));
                    }

                    if(player.getScore() <= 1000) {
                        Games.Achievements.unlock(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_bad_luck));
                    }

                    //Checks if the player has unlocked an achievement by incrementing the achievements requirement for enemies killed.
                    if(enemiesKilled > 0) {
                        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_killer), enemiesKilled);

                        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_serial_killer), enemiesKilled);

                        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_crusher), enemiesKilled);

                        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_mad), enemiesKilled);

                        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_assassin), enemiesKilled);

                        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_psycho), enemiesKilled);

                        Games.Achievements.increment(resourceManager.activity.getGoogleApiClient(),
                                resourceManager.activity.getString(R.string.achievement_warlord), enemiesKilled);
                    }
                    Debug.i("Achievements", "Updated achievements");
                    return null;
                }
            }.execute();
        }

        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
