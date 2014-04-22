package thwack.view;

import thwack.model.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PlayerRenderer {
	
	private SpriteBatch batch;
	
	private ShapeRenderer shapeRenderer;
	
	public PlayerRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
	}
	
	public void render(Player player) {
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(player.getPosition().x, player.getPosition().y, player.getBounds().width, player.getBounds().height);
		shapeRenderer.end();
	}
}
