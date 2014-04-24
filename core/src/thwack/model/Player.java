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
	
	private final Vector2 center;
	private final Vector2 position;
	private final Rectangle bounds;
	private final float speed = 5.0f;
	private Vector2 velocity = new Vector2(0,0).limit(speed);
	
	public Player() {
		this(0.0f, 0.0f);
	}
	
	public Player(float x, float y) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x, y, 50 / Constants.PIXELS_PER_METER, 50 / Constants.PIXELS_PER_METER);
		this.center = new Vector2();
		this.bounds.getCenter(center);
	}
	
	public void move(Vector2 velocity) {
		if (velocity.isZero(0.01f)) {
			this.velocity.set(0, 0);
		} else {
			this.velocity.set(velocity.nor());
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