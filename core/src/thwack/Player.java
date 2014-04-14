package thwack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;

// player character stuff
public class Player {
	
	final Circle circle;
	private float speed = 100;
	
	
	public Player() {
		this(50, 50, 32);
	}
	
	public Player(float x, float y, float radius) {
		this.circle = new Circle(x, y, radius);
	}
	
	public void update(float deltaTime, Array<Block> objects) {

		float oldX = circle.x;
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			circle.x -= speed * deltaTime;
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			circle.x += speed * deltaTime;
		}
		
		boolean collidedX = false;
		for (Block obj : objects) {
			if (Intersector.overlaps(this.circle, obj.rectangle)) {
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
		for (Block obj : objects) {
			if (Intersector.overlaps(circle, obj.rectangle)) {
				collidedY = true;
				break;
			}
		}
		
		if (collidedY) {
			circle.y = oldY;
		}
	}
	
	public void render(ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.circle(circle.x, circle.y, circle.radius);
		shapeRenderer.end();
	}
	
	
}