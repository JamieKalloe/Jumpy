package net.kalloe.jumpy.factory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.CollidableEntity;
import net.kalloe.jumpy.entity.powerups.Life;
import net.kalloe.jumpy.entity.powerups.MysteryBox;

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
    private String[] powerUps;

    //Singleton
    private PowerUpFactory() {}

    public static PowerUpFactory getInstace() {
        return INSTANCE;
    }

    public void create(PhysicsWorld physicsWorld, VertexBufferObjectManager vbom) {
        this.physicsWorld = physicsWorld;
        this.vbom = vbom;
        this.powerUps = new String[] {"Life", "MysteryBox"};
    }

    public String[] getAvailablePowerUps() {
        return this.powerUps;
    }

    public CollidableEntity createRandomPowerUp(float x, float y) {
        switch (random.nextInt((this.powerUps.length - 1) - 0 + 1) + 0) {
            case 0:
                return createLife(x, y);

            case 1:
                return createMysteryBox(x, y);
        }

        return createLife(x, y);
    }

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

    public MysteryBox createMysteryBox(float x, float y) {
        int platformSide = random.nextInt((40 - -40) + 1) + -40;

        //Create a new instance of the Life PowerUp entity with the given x and y coordinates, setting the sprite.
        MysteryBox mysteryBox = new MysteryBox((x + platformSide), (y + 3), ResourceManager.getInstance().mysteryboxTextureRegion, vbom);

        //Creates the physical Body of the Life PowerUp entity (resembles the sprite, optimized shape for collision).
        Body mysteryBoxBody = PhysicsFactory.createBoxBody(physicsWorld, mysteryBox, BodyDef.BodyType.KinematicBody, POWER_UP_FIXTURE);

        //Binds the Life object to the physics body of the life in the physics world (whole simulation).
        mysteryBoxBody.setUserData(mysteryBox);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(mysteryBox, mysteryBoxBody));

        mysteryBox.setBody(mysteryBoxBody);
        mysteryBox.setZIndex(1);

        return mysteryBox;
    }
}
