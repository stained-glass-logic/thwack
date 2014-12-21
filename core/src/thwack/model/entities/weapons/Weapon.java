package thwack.model.entities.weapons;

import thwack.model.entity.Entity;
import thwack.model.entity.Updateable;

public abstract class Weapon extends Entity implements Updateable {

	protected boolean attacking;
	protected float attackStart;
	protected Entity weilder;
	public int damage = 1;
	
    protected float stateTime = 0f;
	public abstract void beginAttack();
	
	public boolean isAttacking() {
	    return attacking;
	}
	
	@Override
	public void increaseStateTime(float deltaTime) {
		this.stateTime += deltaTime;
	}

	@Override
	public float getStateTime() {
		return stateTime;
	}

	@Override
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
}
