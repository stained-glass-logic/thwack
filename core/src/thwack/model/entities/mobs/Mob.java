package thwack.model.entities.mobs;

import thwack.model.entity.Damageable;
import thwack.model.entity.EntityStats;
import thwack.model.entity.Lifebar;
import thwack.model.entity.Movable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Mob extends Movable implements Damageable{

	public Vector2 lastContact = new Vector2(0f, 0f);
	public int maxHealth = 0;
	public int currentHealth = 0;
	public boolean isActive = true;
	
	public Lifebar lifebar;
	
	//States
	public State state = State.STANDING;
	public DamageState damageState = DamageState.OK;
	public float damageStateTime = 0f;
	
	public enum State {
		STANDING,
		WALKING,
		ATTACKING,
		BORED,
		BORED2,
		RUNNING,
		TAKINGDAMAGE,
		DEFENDING,
		DEAD;
	}

	
	protected Mob(World world, Vector2 pos, Vector2 size)
	{
		this.world = world;
		//this.position.set(pos);
		//this.size.set(size);
		this.stats = new EntityStats();
	}
	
	/**
	 * @param f Sets the last contacted x location
	 */
	public void setLastContactX(float f)
	{
		lastContact.x = f;
	}
	
	/**
	 * @param f Sets the last contacted y location 
	 */
	public void setLastContactY(float f)
	{
		lastContact.y = f;
	}
	
	/**
	 * @return the last contacted x location 
	 */
	public float getLastContactX()
	{
		return lastContact.x;
	}
	
	/**
	 * @return the last contacted y location 
	 */
	public float getLastContactY()
	{
		return lastContact.y;
	}
	
	@Override
	public void increaseDamageStateTime(float deltaTime)
	{
		this.damageStateTime += deltaTime;
	}
	
	@Override
	public void setStateTime(float time)
	{
		this.stateTime = time;
	}
	
	@Override
	public float getDamageStateTime()
	{
		return damageStateTime;
	}
	

	@Override
	public boolean active() {
		return (getPublicState() == EntityState.ACTIVE);
	}
	
	@Override
	public void setActive(boolean isActive) {
		
	}
	
	/**
	 * @param Sets the state of the mob
	 */
	public void setState(State state)
	{
		this.state = state;
	}
	
	@Override
	public void setDamageStateTime(float time)
	{
		damageStateTime = time;
	}
	
	
}
