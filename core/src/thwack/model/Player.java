package thwack.model;

import java.util.Map;

import thwack.Constants;
import thwack.collision.CollisionContext;
import thwack.collision.CollisionVisitor;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player implements Updateable, CollisionVisitor {
	
	public static enum State {
		STANDING,
		WALKING,
		ATTACKING
	}
	
	public static enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	private Direction direction = Direction.DOWN;
	private State state = State.STANDING;
	
	private final Vector2 center;
	private final Vector2 position;
	private final Rectangle bounds;
	private final float speed = 7.0f;
	private Vector2 velocity = new Vector2(0,0).limit(speed);
	
	private float stateTime = 0.0f;
	
	public Player() {
		this(0.0f, 0.0f);
	}
	
	public Player(float x, float y) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x, y, 22, 45);
		this.center = new Vector2();
		this.bounds.getCenter(center);
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
	
	public float getStateTime() {
		return stateTime;
	}
	
	public void increaseStateTime(float delta) {
		this.stateTime += delta;
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
	
	@Override
	public void update(float deltaTime, Map<String, Object> context) {
		
		Vector2 oldPosition = position.cpy();

		bounds.setPosition(oldPosition.mulAdd(velocity, speed * deltaTime));
		
		CollisionContext collisionContext = (CollisionContext)context.get(CollisionContext.COLLISION);
		
		Array<CollisionVisitor> objects = collisionContext.getCollisionCandidates();
		
		boolean collided = false;
		for (CollisionVisitor obj : objects) {
			if (this.collidesWith(obj)) {
				collided = true;
				break;
			}
		}
		
		if (collided) {
			bounds.setPosition(position);
		}
	}
	
	public float getPositionX() {
	return bounds.getX();
	}
	
	public float getPositionY() {
	return bounds.getY();
	}
	
	public Vector2 getPosition() {
		return bounds.getPosition(position);
	}
	
	public Vector2 getCenter() {
		return bounds.getCenter(center);
	}
	
	public void setPosition(float x, float y) {
		this.bounds.setPosition(x, y);
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}

	@Override
	public boolean collidesWith(CollisionVisitor visitor) {
		return this != visitor && visitor.visit(this.bounds);
	}
	
	public boolean visit(Rectangle rect) {
		return Intersector.overlaps(this.bounds, rect);
	}
	
}