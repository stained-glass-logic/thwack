package thwack.model;

import java.util.Map;

import thwack.Constants;
import thwack.collision.CollisionContext;
import thwack.collision.CollisionVisitor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player implements Updateable, CollisionVisitor {
	
	private final Vector2 position;
	private final Rectangle bounds;
	private float speed = 5.0f;
	
	public Player() {
		this(0.0f, 0.0f);
	}
	
	public Player(float x, float y) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x, y, 50 / Constants.PIXELS_PER_METER, 50 / Constants.PIXELS_PER_METER);
	}
	
	@Override
	public void update(float deltaTime, Map<String, Object> context) {
		
		float oldX = bounds.x;
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			bounds.x -= speed * deltaTime;
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			bounds.x += speed * deltaTime;
		}
		
		CollisionContext collisionContext = (CollisionContext)context.get(CollisionContext.COLLISION);
		
		Array<CollisionVisitor> objects = collisionContext.getCollisionCandidates();
		
		boolean collidedX = false;
		for (CollisionVisitor obj : objects) {
			if (this.collidesWith(obj)) {
				collidedX = true;
				break;
			}
		}
		
		if (collidedX) {
			bounds.x = oldX;
		}
		
		float oldY = bounds.y;
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			bounds.y += speed * deltaTime;
		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			bounds.y -= speed * deltaTime;
		}
		
		boolean collidedY = false;
		for (CollisionVisitor obj : objects) {
			if (this.collidesWith(obj)) {
				collidedY = true;
				break;
			}
		}
		
		if (collidedY) {
			bounds.y = oldY;
		}
	}
	
	public Vector2 getPosition() {
		return bounds.getPosition(position);
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
	
	@Override
	public boolean visit(Circle circle) {
		return Intersector.overlaps(circle, this.bounds);
	}
	
	public boolean visit(Rectangle rect) {
		return Intersector.overlaps(this.bounds, rect);
	}
	
}