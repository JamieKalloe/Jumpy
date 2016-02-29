package net.kalloe.jumpy.entity;

import com.badlogic.gdx.physics.box2d.Body;

import org.andengine.entity.IEntity;

/**
 * Created by Jamie on 29-2-2016.
 */
public interface CollidableEntity extends IEntity {

    public void setBody(Body body);
    public Body getBody();
    public String getType();

}
