package net.kalloe.jumpy.scene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.CollectableEntity;
import net.kalloe.jumpy.entity.CollidableEntity;
import net.kalloe.jumpy.entity.Enemy;
import net.kalloe.jumpy.entity.KillableEntity;
import net.kalloe.jumpy.entity.Platform;
import net.kalloe.jumpy.entity.Player;

/**
 * Created by Jamie on 5-3-2016.
 */
public class CollisionContactListener implements ContactListener {

    //Variables
    private Player player;

    /**
     * Initializes a new contact listener (collision events detector).
     * @param player Player entity.
     */
    public CollisionContactListener(Player player) {
        this.player = player;
    }

    /**
     * The beginContact method is called when two fixtures from two different bodies start to overlap.
     * @param contact A contact object is passed when a collision is detected (in the physics world).
     */
    @Override
    public void beginContact(Contact contact) {
        //TODO: extend check for powerup shield, (e.g !player.getPowerUp().getType().equals(PowerUp.SHIELD))
        if(checkContact(contact, Player.TYPE, Enemy.TYPE)) {
            if(player.getHealth() != 0) {
                ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundHit);
                player.dealDamage();

                //Kill the collided enemy.
                if(!(contact.getFixtureA().getBody().getUserData() instanceof KillableEntity)) {
                    ((KillableEntity) contact.getFixtureB().getBody().getUserData()).die();
                } else {
                    ((KillableEntity) contact.getFixtureA().getBody().getUserData()).die();
                }
            } else {
                player.die();
            }
        }

        if(checkContact(contact, Player.TYPE, "COLLECTABLE")) {
            if(!player.isDead()) {
                if (!(contact.getFixtureA().getBody().getUserData() instanceof CollectableEntity)) {
                    ((CollectableEntity) contact.getFixtureB().getBody().getUserData()).obtain(this.player);
                } else {
                    ((CollectableEntity) contact.getFixtureA().getBody().getUserData()).obtain(this.player);
                }
            }
        }
        //TODO: extend for in-game powerups / cash
    }

    /**
     * The endContact method is called when two fixtures stop overlapping.
     * @param contact
     */
    @Override
    public void endContact(Contact contact) {

    }

    /**
     * The preSolve method is called after the collision is detected, but before any calculation of the result is made.
     * @param contact
     * @param oldManifold
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if(checkContact(contact, Player.TYPE, Platform.TYPE)) {

            //On contact (after the fall) the platform propels the player upwards.
            //Also a sound is played which resembles the jump action of the player.
            if(!player.isDead() && player.getBody().getLinearVelocity().y < 0) {
                player.getBody().setLinearVelocity(new Vector2(0, 33));
                ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundJump);
            } else {
                contact.setEnabled(false);
            }
        }
    }

    /**
     * The postSolve method is called after all the collision calculations have been made.
     * @param contact
     * @param impulse
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    /**
     * Checks if both fixtures belong to bodies that are pard of CollidableEntities.
     * @param contact contact object that gets passed when a collison is detected
     * @param typeA Type of a collidable entity.
     * @param typeB Type of a collidable entity
     * @return boolean
     */
    private boolean checkContact(Contact contact, String typeA, String typeB) {
        if(contact.getFixtureA().getBody().getUserData() instanceof CollidableEntity &&
                contact.getFixtureB().getBody().getUserData() instanceof CollidableEntity) {
            CollidableEntity ceA = (CollidableEntity) contact.getFixtureA().getBody().getUserData();
            CollidableEntity ceB = (CollidableEntity) contact.getFixtureB().getBody().getUserData();

            if(typeA.equals(ceA.getType()) && typeB.equals(ceB.getType()) ||
                    typeA.equals(ceB.getType()) && typeB.equals(ceA.getType())) {
                return true;
            }
        }
        return false;
    }
}
