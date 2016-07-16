package net.kalloe.jumpy.factory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.powerups.Life;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Random;

/**
 * Created by Jamie on 16-7-2016.
 */
public class PowerUpFactory {

    //Variables
    public static final FixtureDef POWER_UP_FIXTURE = PhysicsFactory.createFixtureDef(1f, 0f, 1f, true);
    private static PowerUpFactory INSTANCE = new PowerUpFactory();
    private PhysicsWorld physicsWorld;
    private VertexBufferObjectManager vbom;
    private Random random = new Random();

    //Singleton
    private PowerUpFactory() {}

    public void create(PhysicsWorld physicsWorld, VertexBufferObjectManager vbom) {
        this.physicsWorld = physicsWorld;
        this.vbom = vbom;
    }

    //TODO: create method for creating random powerup

    public Life createLife(float x, float y) {
        int platformSide = random.nextInt((40 - -40) + 1) + -40;

        //Create a new instance of the Life PowerUp entity with the given x and y coordinates, setting the sprite.
        Life life = new Life((x + platformSide), (y - 5), ResourceManager.getInstance().mushroomTextureRegion, vbom);

        //Creates the physical Body of the Life PowerUp entity (resembles the sprite, optimized shape for collision).
        Body lifeBody = PhysicsFactory.createBoxBody(physicsWorld, life, BodyDef.BodyType.KinematicBody, POWER_UP_FIXTURE);

        //Binds the Life object to the physics body of the life in the physics world (whole simulation).
        lifeBody.setUserData(life);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(life, lifeBody));

        life.setBody(lifeBody);
        life.setZIndex(1);

        return life;
    }
}
