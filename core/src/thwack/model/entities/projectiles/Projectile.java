package thwack.model.entities.projectiles;

import thwack.model.entity.Entity;
import thwack.model.entity.Movable;
import thwack.model.entity.Updateable;

public abstract class Projectile extends Movable implements Updateable{
	
	public float speed = 1f;
	public float radius = .5f;
	public ProjectileState projectileState = ProjectileState.Active;
	
	public Entity shooter;
	
	public float stateTime = 0f;
	public boolean isActive = true;
	
	public static enum ProjectileState
	{
		Active,
		HitWall,
		HitPlayer,
		HitEnemy,
		HitProjectile;
	}
	
	public void shootToward(float targetX, float targetY)
	{
		velocity.set(targetX - entityBody.getPosition().x, targetY - entityBody.getPosition().y).nor().scl(Math.min(entityBody.getPosition().dst(targetX, targetY), speed * 8));
		setVelocity(velocity);
	}
	
	public boolean active() {
		return EntityState.ACTIVE == getPublicState();
	}
	

	@Override
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	@Override
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
}
