package thwack.view;

import thwack.model.Block;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class BlockRenderer {

	private ShapeRenderer shapeRenderer;
	
	private Color color = Color.GREEN;
	
	public BlockRenderer(ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}
	
	public void render(Block block) {
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(block.getPosition().x, block.getPosition().y, block.getBounds().width, block.getBounds().height);
		shapeRenderer.end();
	}

}
