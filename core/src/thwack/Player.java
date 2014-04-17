package thwack;

import java.util.Map;

import thwack.collision.CollisionContext;
import thwack.collision.CollisionVisitor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

// player character stuff
public class Player implements Body, CollisionVisitor {
	
	private final Circle circle;
	private float speed = 200;
	
	public Player() {
		this(50, 50, 32);
	}
	
	public Player(float x, float y, float radius) {
		this.circle = new Circle(x, y, radius);
	}
	
	@Override
	public void update(float deltaTime, Map<String, Object> context) {
		
		float oldX = circle.x;
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			circle.x -= speed * deltaTime;
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			circle.x += speed * deltaTime;
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
			circle.x = oldX;
		}
		
		float oldY = circle.y;
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			circle.y += speed * deltaTime;
		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			circle.y -= speed * deltaTime;
		}
		
		boolean collidedY = false;
		for (CollisionVisitor obj : objects) {
			if (this.collidesWith(obj)) {
				collidedY = true;
				break;
			}
		}
		
		if (collidedY) {
			circle.y = oldY;
		}
	}
	
	@Override
	public void render(float deltaTime, Map<String, Object> context) {
		ShapeRenderer renderer = (ShapeRenderer)context.get(ThwackGame.SHAPE_RENDERER);
		
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.WHITE);
		renderer.circle(circle.x, circle.y, circle.radius);
		renderer.end();
	}
	
	@Override
	public boolean collidesWith(CollisionVisitor visitor) {
		return this != visitor && visitor.visit(this.circle);
	}
	
	@Override
	public boolean visit(Circle circle) {
		return Intersector.overlaps(circle, this.circle);
	}
	
	public boolean visit(Rectangle rect) {
		return Intersector.overlaps(this.circle, rect);
	}
	
}