package net.kalloe.jumpy.scene;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

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
     * @param contact
     */
    @Override
    public void beginContact(Contact contact) {

    }

    /**
     * The endContac method is called when two fixtures stop overlapping.
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

    }

    /**
     * The postSolve method is called after all the collision calculations have been made.
     * @param contact
     * @param impulse
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
