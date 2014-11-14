package thwack.model;

import java.util.Map;

import thwack.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Mob implements Updateable{
	
	//private AI ai;
	public static enum State {
		STANDING,
		BORED,
		RUNNING
	}
	
	public static enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	
	private final float speed = 0f;
	protected final Vector2 center = new Vector2(0,0);
	protected final Vector2 position = new Vector2(0,0);
	protected final Vector2 size = new Vector2(0,0);
	protected Vector2 direction = new Vector2(0.0f, 0.0f);
	protected Vector2 velocity = new Vector2(0,0).limit(getSpeed());

	protected float stateTime = MathUtils.random(10.0f);

	public Mob() {
	}
	
	@Override
	public void update(float deltaTime, Map<String, Object> context) {
	}
	
	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}
	
	public float getStateTime() {
		return stateTime;
	}
	
	public void increaseStateTime(float delta) {
		this.stateTime += delta;
	}

	protected float getSpeed() {
		return speed;
	}


}
