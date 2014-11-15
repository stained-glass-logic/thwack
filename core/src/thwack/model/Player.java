package thwack.model;

import java.util.Map;

import thwack.Constants;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Player extends Entity implements Updateable {
	
	public Body playerBody;
	private BodyDef playerBodyDef = new BodyDef();
	private FixtureDef playerFixtureDef;
	 
    public void applyImpulse() {

           // Vector2 currentVelocity = playerBody.getLinearVelocity();
            Vector2 targetVelocity = new Vector2(velocity).nor().scl(speed);

            Vector2 impulse = new Vector2(targetVelocity);

            playerBody.setLinearVelocity(impulse);
    }

	public Player(World world) {
		this(0.0f, 0.0f);
		playerBodyDef.type = BodyType.DynamicBody;
		playerBodyDef.position.set(5,20);
		this.playerBody = world.createBody(playerBodyDef);
		playerBody.setFixedRotation(true);
		// fixtureDef.restitution=restitution;
		PolygonShape playerBodyShape = new PolygonShape();
		playerBodyShape.setAsBox(.5f, .5f);
		playerFixtureDef = new FixtureDef();
		playerFixtureDef.density=1.0f;
		playerFixtureDef.shape = playerBodyShape;

		this.playerBody.createFixture(playerFixtureDef);
		playerFixtureDef.shape.dispose();
	}
	
	public Player(float x, float y) {
		this.position = new Vector2(x, y);
		this.center = new Vector2();
	}

	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		if (this.state != state) {
			// reset the state time every time state changes
			stateTime = 0.0f;
		}
		this.state = state;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void move(Vector2 velocity) {
		if (this.state == State.ATTACKING) {
			this.velocity.set(0, 0);
		} else {
			if (velocity.isZero(0.01f)) {
				this.velocity.set(0, 0);
				this.state = State.STANDING;
			} else {
				this.velocity.set(velocity.nor());
				this.state = State.WALKING;
				
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
	
}