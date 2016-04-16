package net.kalloe.jumpy.factory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import net.kalloe.jumpy.ResourceManager;
import net.kalloe.jumpy.entity.Enemy;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Random;

/**
 * Created by Jamie on 5-3-2016.
 */
public class EnemyFactory {

    //Variables
    public static final FixtureDef ENEMY_FIXTURE = PhysicsFactory.createFixtureDef(1f, 0f, 1f, true);
    private static EnemyFactory INSTANCE = new EnemyFactory();
    private PhysicsWorld physicsWorld;
    private VertexBufferObjectManager vbom;
    private Random random = new Random();

    //Singleton
    private EnemyFactory() {}

    /**
     * Returns the singleton instance of the EnemyFactory.
     * @return EnemyFactory
     */
    public static EnemyFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Sets the physicsworld and vertexbufferobjectmanager to the EnemyFactory.
     * @param physicsWorld physics world.
     * @param vbom vertex buffer object manager.
     */
    public void create(PhysicsWorld physicsWorld, VertexBufferObjectManager vbom) {
        this.physicsWorld = physicsWorld;
        this.vbom = vbom;
    }

    /**
     * Creates a new enemy entity with the specified x and y coordinates.
     * @param x x coordinates of the enemy.
     * @param y y coordinates of the enemy.
     * @return new instance of the enemy entity.
     */
    public Enemy createEnemy(float x, float y) {
        //Create a new instance of the enemy entity with the given x and y coordinates, setting the sprite.
        Enemy enemy = new Enemy(x, y, ResourceManager.getInstance().enemyTextureRegion, vbom);

        //Creates the physical Body of the Enemy entity (resembles the sprite, optimized shape for collision).
        Body enemyBody = PhysicsFactory.createBoxBody(physicsWorld, enemy, BodyDef.BodyType.KinematicBody, ENEMY_FIXTURE);

        //Binds the Enemy object to the physics body of the enemy in the physics world (whole simulation).
        enemyBody.setUserData(enemy);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(enemy, enemyBody));

        enemy.setBody(enemyBody);
        enemyBody.setLinearVelocity(-1, 0);
        enemy.animate(75);
        enemy.setZIndex(1);

        return enemy;
    }

    /**
     * Creates a new slime enemy entity with the specified x and y coordinates.
     * @param x x coordinates of the slime enemy.
     * @param y y coordinates of the slime enemy.
     * @return new instance of the slime enemy entity.
     */
    public Enemy createSlimeEnemy(float x, float y) {
        //Create a new instance of the enemy entity with the given x and y coordinates, setting the sprite.
        Enemy enemy = new Enemy((x + random.nextInt((40 - -40) + 1) + -40), (y - 5), ResourceManager.getInstance().slimeEnemyTextureRegion, vbom);

        //Creates the physical Body of the Enemy entity (resembles the sprite, optimized shape for collision).
        Body enemyBody = PhysicsFactory.createBoxBody(physicsWorld, enemy, BodyDef.BodyType.KinematicBody, ENEMY_FIXTURE);

        //Binds the Enemy object to the physics body of the enemy in the physics world (whole simulation).
        enemyBody.setUserData(enemy);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(enemy, enemyBody));

        enemy.setBody(enemyBody);
//        enemyBody.setLinearVelocity(-1, 0);
//        enemy.animate(75);
        enemy.setZIndex(1);

        return enemy;
    }
}
