package thwack.model;

import com.badlogic.gdx.math.Vector2;

import java.util.Map;

public abstract class Entity {

	protected Direction direction = Direction.DOWN;
	public State state = State.STANDING;
	protected float stateTime;
	protected Vector2 center = new Vector2(0, 0);
	protected Vector2 position = new Vector2(0, 0);
	protected final float speed = 7.0f;
	public Vector2 velocity = new Vector2(0, 0).limit(speed);
	protected Vector2 directionVector = new Vector2(0.0f, 0.0f);

	public static enum State {
		STANDING,
		WALKING,
		ATTACKING,
		BORED,
		RUNNING
	}

	public static enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float f) {
		this.stateTime = f;
	}

	public void increaseStateTime(float delta) {
		this.stateTime += delta;
	}

	public void update(float deltaTime, Map<String, Object> context) {
		Vector2 oldPosition = position.cpy();
	}

	public void setPosition(float f, float g) {
		this.position.set(f, g);
	}

	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	public void updatePosition() {}

}