package thwack.view;

import thwack.model.Player;
import thwack.model.Player.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PlayerRenderer {
	
	private SpriteBatch batch;
	
	private ShapeRenderer shapeRenderer;
	
	private Texture walkingDown;
	
	private Animation walkingDownAnimation;
	
	private TextureRegion standingDown;
	
	public PlayerRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
		this.walkingDown = new Texture(Gdx.files.internal("walking-downard_35x45.gif"));
		
		TextureRegion[] walkingDownRegions = new TextureRegion[4];
		
		for (int i = 0; i < 3; i++) {
			walkingDownRegions[i] = new TextureRegion(walkingDown, i * 35, 0, 35, 45);
		}
		
		walkingDownRegions[3] = walkingDownRegions[1];
		
		walkingDownAnimation = new Animation(0.150f, walkingDownRegions);
		
		standingDown = walkingDownRegions[1];
	}
	
	public void render(Player player) {
		
		player.increaseStateTime(Gdx.graphics.getDeltaTime());
		
		
		TextureRegion currentRegion = null;
		if (player.getState() == State.WALKING) {
			currentRegion = walkingDownAnimation.getKeyFrame(player.getStateTime(), true);
		} else if (player.getState() == State.STANDING) {
			currentRegion = standingDown;
		}
		
		batch.begin();
		batch.draw(currentRegion, player.getPosition().x, player.getPosition().y, player.getBounds().width, player.getBounds().height);
		batch.end();
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(player.getPosition().x, player.getPosition().y, player.getBounds().width, player.getBounds().height);
		shapeRenderer.end();
		
	}
}
