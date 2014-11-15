package thwack.model;

import thwack.Constants;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Rat extends Mob {

	public Body ratBody;
	private BodyDef ratBodyDef;
	private FixtureDef ratFixtureDef;
	private Direction direction = Direction.UP;
	private State state = State.BORED;

	public Vector2 velocity = new Vector2(0, 0).limit(getSpeed());

	public Rat(World world, Vector2 pos, Vector2 size) {
		super();
		this.setSpeed(10f);
		this.ratBodyDef = new BodyDef();
		this.ratBodyDef.type = BodyType.DynamicBody;
		this.ratBodyDef.position.set(pos);
		ratBody = world.createBody(ratBodyDef);
		ratBody.setFixedRotation(true);


		PolygonShape ratBodyShape = new PolygonShape();
		ratBodyShape.setAsBox(size.x, size.y);

		ratFixtureDef = new FixtureDef();
		ratFixtureDef.density = .5f;
		ratFixtureDef.shape = ratBodyShape;
		ratBody.createFixture(ratFixtureDef);
		ratFixtureDef.shape.dispose();
	}

	public void applyImpulse() {
		Vector2 targetVelocity = new Vector2(velocity).nor().scl(getSpeed());
		Vector2 impulse = new Vector2(targetVelocity);
		ratBody.setLinearVelocity(impulse);
	}

	public void move(Vector2 velocity) {
		if (this.state == State.BORED) {
			this.velocity.set(0, 0);
		} else {
			if (velocity.isZero(0.01f)) {
				this.velocity.set(0, 0);
				this.state = State.STANDING;
			}
			{
				this.velocity.set(velocity.nor());
				this.state = State.RUNNING;

				float epsilon = 0.5f;
				if (velocity.isCollinear(Constants.UP, epsilon)) {
					this.direction = Direction.UP;
				} else if (velocity.isCollinear(Constants.DOWN, epsilon)) {
					this.direction = Direction.DOWN;
				} else if (velocity.isCollinear(Constants.LEFT, epsilon)) {
					this.direction = Direction.LEFT;
				} else if (velocity.isCollinear(Constants.RIGHT, epsilon)) {
					this.direction = Direction.RIGHT;
				}
			}
		}
	}

	public void setState(State state) {
		if (this.state != state) {
			// reset the state time every time state changes
			stateTime = 0.0f;
		}
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void setPosition(float f, float g) {
		this.position.set(f, g);
	}

	public Direction getDirection() {
		return direction;
	}

	public Vector2 getPosition() {
		return position;
	}
}
