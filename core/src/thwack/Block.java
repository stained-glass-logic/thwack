package thwack;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Block {
	
	final Rectangle rectangle;
	private Color color = Color.GREEN;
	
	public Block() {
		this(100, 100, 64, 64);
	}
	
	public Block(float x, float y, float width, float height) {
		this.rectangle = new Rectangle(x, y, width, height);
	}
	
	public void render(ShapeRenderer renderer) {
		renderer.begin(ShapeType.Filled);
		renderer.setColor(color);
		renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		renderer.end();
	}

}
