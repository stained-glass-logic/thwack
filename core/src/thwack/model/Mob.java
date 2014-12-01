package thwack.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class Mob extends Entity implements Updateable{

	private float speed = 0f;
	private Body mobBody;
	private BodyDef mobBodyDef;
	private FixtureDef mobBodyFixtureDef;
	protected final Vector2 size = new Vector2(0,0);
	private Vector2 velocity = new Vector2(0,0).limit(getSpeed());
	protected static float lastContactx;
	protected static float lastContacty;
	protected float stateTime = MathUtils.random(10.0f);

	public Mob() {
	}

	@Override
	public void update(float deltaTime) {
	}

	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	protected float getSpeed() {
		return speed;
	}

	public void setSpeed(float speedTarget){
		this.speed = speedTarget;
	}

	public void setBody(Body body){
		this.mobBody = body;
	}

	public void setBodyDef(BodyDef bodyDef){
		this.mobBodyDef = bodyDef;
	}

	public void setBodyFixtureDef(FixtureDef bodyFixtureDef){
		this.mobBodyFixtureDef = bodyFixtureDef;
	}

	public void mobMoveLogic(float f) {
		// TODO Auto-generated method stub
		
	}

	public static void setLastContactx(float f) {
		lastContactx = f;
	}

	public static void setLastContacty(float f) {
		lastContacty = f;
	}

	public static float getLastContactx(){
		return lastContactx;
	}
	
	public static float getLastContacty(){
		return lastContacty;
	}
}
