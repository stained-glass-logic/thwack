package thwack.view;

import java.util.HashMap;
import java.util.Map;

import thwack.model.Player;
import thwack.model.Player.Direction;
import thwack.model.Player.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Disposable;

public class PlayerRenderer implements Disposable {
	
	private SpriteBatch batch;
	
	private ShapeRenderer shapeRenderer;
	
	private final Map<Direction, AnimationDirection> directions = new HashMap<Direction, AnimationDirection>();
	
	public PlayerRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
		
		directions.put(Direction.DOWN, new AnimationDirection("walking-downard_35x45.gif"));
		directions.put(Direction.UP, new AnimationDirection("walking-upward_35x46.gif"));
		
	}
	
	public void render(Player player) {
		
		player.increaseStateTime(Gdx.graphics.getDeltaTime());
		
		AnimationDirection direction = directions.get(player.getDirection());
		TextureRegion currentRegion = direction.getFrame(player.getState(), player.getStateTime());
		
		batch.begin();
		batch.draw(currentRegion, player.getPosition().x, player.getPosition().y, player.getBounds().width, player.getBounds().height);
		batch.end();
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(player.getPosition().x, player.getPosition().y, player.getBounds().width, player.getBounds().height);
		shapeRenderer.end();
		
	}
	
	@Override
	public void dispose() {
		for (AnimationDirection animDir: directions.values()) {
			animDir.dispose();
		}
		directions.clear();
	}
	
	private static class AnimationDirection implements Disposable {

		private Texture walking;
		private Animation walkingAnimation;
		private TextureRegion standing;
		
		public AnimationDirection(String image) {

			this.walking = new Texture(Gdx.files.internal(image));
			
			TextureRegion[] walkingDownRegions = new TextureRegion[3];
			
			for (int i = 0; i < 3; i++) {
				walkingDownRegions[i] = new TextureRegion(walking, i * 35, 0, 35, 45);
			}
			
			walkingAnimation = new Animation(0.150f, walkingDownRegions);
			walkingAnimation.setPlayMode(PlayMode.LOOP_PINGPONG);
			
			standing = walkingDownRegions[1];
		}
		
		public TextureRegion getFrame(State playerState, float playerStateTime) {
			if (playerState == State.WALKING) {
				return walkingAnimation.getKeyFrame(playerStateTime, true);
			} else if (playerState == State.STANDING) {
				return standing;
			}
			return null;
		}
		
		@Override
		public void dispose() {
			walking.dispose();
		}
		
	}

}
