package thwack.model.entity;

import com.badlogic.gdx.math.Vector2;

public abstract class Movable extends Entity implements Updateable{

	public Direction direction = Direction.DOWN;
	public Vector2 velocity = new Vector2(0f, 0f);
	float speed = 0f; 
	protected float stateTime;
	
	/**
	 * The direction this movable object is traveling.
	 */
	public static enum Direction
	{
		UP, DOWN, LEFT, RIGHT;
	}	
	
	/**
	 * @param f delta time to update the move logic
	 */
	public void moveLogic(float f)
	{
		
	}
	
	/**
	 * @param f Speed of the movable object
	 */
	public void setSpeed(float f)
	{
		this.speed = f;
	}
	
	/**
	 * @param velocity velocity of the object
	 */
	public void setVelocity(Vector2 velocity)
	{
		this.entityBody.setLinearVelocity(velocity);
	}
	
	
	@Override
	public void increaseStateTime(float deltaTime)
	{
		this.stateTime += deltaTime;
	}
	
	@Override
	public float getStateTime()
	{
		return stateTime;
	}
}
