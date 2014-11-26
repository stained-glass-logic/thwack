package thwack.model;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	protected Direction direction = Direction.DOWN;
	public State state = State.STANDING;
	public DamageState damageState = DamageState.OK;
	protected float stateTime;
	protected Vector2 center = new Vector2(0, 0);
	protected Vector2 position = new Vector2(0, 0);
	protected final float speed = 7.0f;
	public Vector2 velocity = new Vector2(0, 0).limit(speed);
	protected Vector2 directionVector = new Vector2(0.0f, 0.0f);
	protected float damageStateTime;

	int hp; //temporary HP variable for implementing mob color
	
	public static enum State {
		STANDING,
		WALKING,
		ATTACKING,
		BORED,
		BORED2,
		RUNNING,
		TAKINGDAMAGE,
		DEAD
	}
	
	// planning for future status updates here. we needed a separate timer for damage states 
	// so animations don't get reset every time something hits --radish

	public static enum DamageState {
		PHYSICAL,
		POISON,
		ON_FIRE,
		SLOWED,
		STUNNED,
		THWACKED, 
		OK
	}
	
	public static enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public float getStateTime() {
		return stateTime;
	}
	
	public float getDamageStateTime() {
		return damageStateTime;
	}

	public void setStateTime(float f) {
		this.stateTime = f;
	}
	
	public void setDamageStateTime(float f) {
		this.damageStateTime = f;
	}


	public void increaseStateTime(float delta) {
		this.stateTime += delta;
		this.damageStateTime += delta;
	}

	public void update(float deltaTime) {
		Vector2 oldPosition = position.cpy();
	}

	public void setPosition(float f, float g) {
		this.position.set(f, g);
	}

	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	public void tookDamage(Vector2 fromPos) {
		died();
	}
	
	public void died() {
		state = State.DEAD;
	}

	public boolean isAlive() {
		return state != State.DEAD;
	}

}