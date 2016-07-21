package net.kalloe.jumpy.factory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.PowerUp;
import net.kalloe.jumpy.entity.powerups.Gold;
import net.kalloe.jumpy.entity.powerups.Life;
import net.kalloe.jumpy.entity.powerups.MysteryBox;
import net.kalloe.jumpy.entity.powerups.PowerUpType;
import net.kalloe.jumpy.entity.powerups.SuperJump;
import net.kalloe.jumpy.shop.GoldShopData;
import net.kalloe.jumpy.shop.LifeShopData;
import net.kalloe.jumpy.shop.MysteryBoxShopData;
import net.kalloe.jumpy.shop.SuperJumpShopData;

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
    public final static PowerUpType[] POWER_UPS = PowerUpType.values();


    //Singleton
    private PowerUpFactory() {}

    /**
     * Returns the singleton instance of the PowerUpFactory.
     * @return PowerUpFactory
     */
    public static PowerUpFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Sets the physicsworld and vertexbufferobjectmanager to the PowerUpFactory.
     * @param physicsWorld physics world.
     * @param vbom vertex buffer object manager.
     */
    public void create(PhysicsWorld physicsWorld, VertexBufferObjectManager vbom) {
        this.physicsWorld = physicsWorld;
        this.vbom = vbom;
    }

    public PowerUp createPowerUp(PowerUpType type, float x, float y) {
        switch (type) {

            case LIFE:
                return this.createLife(x, y);

            case MYSTERYBOX:
                return this.createMysteryBox(x, y);

            case SUPERJUMP:
                return this.createSuperJump(x, y);

            case GOLD:
                return this.createGold(x, y);
        }

        return createLife(x, y);
    }

    /**
     * Creates a random PowerUp.
     * @param x X coordinates of the PowerUp entity.
     * @param y Y coordinates of the PowerUp entity.
     * @return random PowerUp.
     */
    public PowerUp createRandomPowerUp(float x, float y) {
        return this.createPowerUp(POWER_UPS[random.nextInt((POWER_UPS.length - 1) - 0 + 1) + 0], x, y);
    }

    /**
     * Creates a new Life entity with the specified x and y coordinates.
     * @param x x coordinates of the Life.
     * @param y y coordinates of the Life.
     * @return new instance of the Life entity.
     */
    private PowerUp createLife(float x, float y) {
        int platformSide = random.nextInt((40 - -40) + 1) + -40;

        //Create a new instance of the Life PowerUp entity with the given x and y coordinates, setting the sprite.
        PowerUp life = new PowerUp((x + platformSide), (y - 5), ResourceManager.getInstance().mushroomTextureRegion, vbom);
        life.setShopData(new LifeShopData());
        life.setPowerUpBehaviour(new Life());

        //Creates the physical Body of the Life PowerUp entity (resembles the sprite, optimized shape for collision).
        Body lifeBody = PhysicsFactory.createBoxBody(physicsWorld, life, BodyDef.BodyType.KinematicBody, POWER_UP_FIXTURE);

        //Binds the Life object to the physics body of the life in the physics world (whole simulation).
        lifeBody.setUserData(life);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(life, lifeBody));

        life.setBody(lifeBody);
        life.setZIndex(1);

        return life;
    }

    /**
     * Creates a new MysteryBox entity with the specified x and y coordinates.
     * @param x x coordinates of the MysteryBox.
     * @param y y coordinates of the MysteryBox.
     * @return new instance of the MysteryBox entity.
     */
    private PowerUp createMysteryBox(float x, float y) {
        int platformSide = random.nextInt((40 - -40) + 1) + -40;

        //Create a new instance of the Life PowerUp entity with the given x and y coordinates, setting the sprite.
        PowerUp mysteryBox = new PowerUp((x + platformSide), (y + 3), ResourceManager.getInstance().mysteryboxTextureRegion, vbom);
        mysteryBox.setShopData(new MysteryBoxShopData());
        mysteryBox.setPowerUpBehaviour(new MysteryBox());

        //Creates the physical Body of the Life PowerUp entity (resembles the sprite, optimized shape for collision).
        Body mysteryBoxBody = PhysicsFactory.createBoxBody(physicsWorld, mysteryBox, BodyDef.BodyType.KinematicBody, POWER_UP_FIXTURE);

        //Binds the Life object to the physics body of the life in the physics world (whole simulation).
        mysteryBoxBody.setUserData(mysteryBox);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(mysteryBox, mysteryBoxBody));

        mysteryBox.setBody(mysteryBoxBody);
        mysteryBox.setZIndex(1);

        return mysteryBox;
    }

    /**
     * Creates a new SuperJump entity with the specified x and y coordinates.
     * @param x x coordinates of the SuperJump.
     * @param y y coordinates of the SuperJump.
     * @return new instance of the SuperJump entity.
     */
    private PowerUp createSuperJump(float x, float y) {
        int platformSide = random.nextInt((40 - -40) + 1) + -40;

        //Create a new instance of the Life PowerUp entity with the given x and y coordinates, setting the sprite.
        PowerUp superJump = new PowerUp((x + platformSide), (y - 5), ResourceManager.getInstance().mushroomJumpTextureRegion, vbom);
        superJump.setShopData(new SuperJumpShopData());
        superJump.setPowerUpBehaviour(new SuperJump());

        //Creates the physical Body of the Life PowerUp entity (resembles the sprite, optimized shape for collision).
        Body superJumpBody = PhysicsFactory.createBoxBody(physicsWorld, superJump, BodyDef.BodyType.KinematicBody, POWER_UP_FIXTURE);

        //Binds the Life object to the physics body of the life in the physics world (whole simulation).
        superJumpBody.setUserData(superJump);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(superJump, superJumpBody));

        superJump.setBody(superJumpBody);
        superJump.setZIndex(1);

        return superJump;
    }

    /**
     * Creates a new Gold entity with the specified x and y coordinates.
     * @param x x coordinates of the Life.
     * @param y y coordinates of the Life.
     * @return new instance of the Life entity.
     */
    private PowerUp createGold(float x, float y) {
        int platformSide = random.nextInt((40 - -40) + 1) + -40;

        //Create a new instance of the Life PowerUp entity with the given x and y coordinates, setting the sprite.
        PowerUp gold = new PowerUp((x + platformSide), (y - 5), ResourceManager.getInstance().goldTextureRegion, vbom);
        gold.setShopData(new GoldShopData());
        gold.setPowerUpBehaviour(new Gold());

        //Set the way the gold is facing
        if(platformSide > 0) {
            gold.setFlippedHorizontal(true);
        }

        //Creates the physical Body of the Life PowerUp entity (resembles the sprite, optimized shape for collision).
        Body goldBody = PhysicsFactory.createBoxBody(physicsWorld, gold, BodyDef.BodyType.KinematicBody, POWER_UP_FIXTURE);

        //Binds the Life object to the physics body of the life in the physics world (whole simulation).
        goldBody.setUserData(gold);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(gold, goldBody));

        gold.setBody(goldBody);
        gold.setZIndex(1);

        return gold;
    }
}
