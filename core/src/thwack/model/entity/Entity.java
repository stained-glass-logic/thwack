package thwack.model.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public abstract class Entity extends Stateable implements Disposable{

	/**
	 * The body associated with this entity.
	 */
	public Body entityBody;
	
	/**
	 * The world associated with this entity.
	 */
	public World world;
	
	/**
	 * The size of the entity.
	 */
	public final Vector2 size = new Vector2(0f, 0f);
	
	/**
	 * The position of the entity.
	 */
	//public final Vector2 position = new Vector2(0f, 0f);

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @return the entityBody
	 */
	public Body getBody() {
		return entityBody;
	}


	/**
	 * @param entityBody the entityBody to set
	 */
	public void setBody(Body entityBody) {
		this.entityBody = entityBody;
	}
}
