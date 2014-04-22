package thwack.model;

import thwack.collision.CollisionVisitor;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block implements CollisionVisitor {
	
	private final Vector2 position;
	private final Rectangle bounds;
	
	public Block() {
		this(0.0f, 0.0f, 1.0f, 1.0f);
	}
	
	public Block(float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x, y, width, height);
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

	@Override
	public boolean collidesWith(CollisionVisitor visitor) {
		return this != visitor && visitor.visit(this.bounds);
	}
	
	@Override
	public boolean visit(Circle circle) {
		return Intersector.overlaps(circle, bounds);
	}
	
	@Override
	public boolean visit(Rectangle rect) {
		return Intersector.overlaps(this.bounds, rect);
	}
}
