package thwack.model;

import java.util.Map;

import thwack.Constants;
import thwack.collision.CollisionContext;
import thwack.collision.CollisionVisitor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Mob implements Updateable, CollisionVisitor {

	private Vector2 position;
	
	private Rectangle bounds;

	private boolean facingLeft = true;

	private float speed = 5.0f;
	
	private float stateTime = MathUtils.random(10.0f);

	public Mob() {
		this(0f, 0f);
	}
	
	public Mob(float x, float y) {
		this.position = new Vector2(x, y);
		bounds = new Rectangle(x, y, 74 / Constants.PIXELS_PER_METER, 60 / Constants.PIXELS_PER_METER);
	}
	
	@Override
	public void update(float deltaTime, Map<String, Object> context) {
		float oldX = bounds.x;

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			bounds.x -= speed * deltaTime;
			facingLeft = true;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			bounds.x += speed * deltaTime;
			facingLeft = false;
		}

		CollisionContext collisionContext = (CollisionContext) context.get(CollisionContext.COLLISION);

		Array<CollisionVisitor> objects = collisionContext
				.getCollisionCandidates();

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
	
	public boolean isFacingLeft() {
		return facingLeft;
	}
	
	public void setPosition(float x, float y) {
		this.bounds.setPosition(x, y);
	}
	
	public Vector2 getPosition() {
		return bounds.getPosition(position);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public float getStateTime() {
		return stateTime;
	}
	
	public void increaseStateTime(float delta) {
		this.stateTime += delta;
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
