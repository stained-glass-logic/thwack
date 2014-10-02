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
		
		directions.put(Direction.DOWN, new AnimationDirection("Hero/Running/Forward/forward.png"));
		directions.put(Direction.UP, new AnimationDirection("Hero/Running/Forward/forward.png"));
		directions.put(Direction.LEFT, new AnimationDirection("Hero/Running/Side/side.png", true, 6));
		directions.put(Direction.RIGHT, new AnimationDirection("Hero/Running/Side/side.png", false, 6));
	}
	
	public void render(Player player) {
		
		player.increaseStateTime(Gdx.graphics.getDeltaTime());
		
		AnimationDirection direction = directions.get(player.getDirection());
		TextureRegion currentRegion = direction.getFrame(player.getState(), player.getStateTime());
		
		batch.begin();
		batch.draw(currentRegion, player.getPosition().x, player.getPosition().y, player.getBounds().width, player.getBounds().height);
		batch.end();
		
//		shapeRenderer.begin(ShapeType.Line);
//		shapeRenderer.setColor(Color.WHITE);
//		shapeRenderer.rect(player.getPosition().x, player.getPosition().y, player.getBounds().width, player.getBounds().height);
//		shapeRenderer.end();
		
	}
	
	@Override
	public void dispose() {
		for (AnimationDirection animDir: directions.values()) {
			animDir.dispose();
		}
		directions.clear();
	}
	
	private static class AnimationDirection implements Disposable {

		private final Texture walking;
		private final Animation walkingAnimation;
		private final TextureRegion standing;
		private final boolean invert;
		
		public AnimationDirection(String image) {
			this(image, false);
		}
		
		public AnimationDirection(String image, boolean invert) {
			this(image, false, 0);
		}
		
		public AnimationDirection(String image, boolean invert, int standingFrame) {
			this.invert = invert;
			this.walking = new Texture(Gdx.files.internal(image));
			
			TextureRegion[] regions = new TextureRegion[8];
			
			for (int i = 0; i < 8; i++) {
				regions[i] = new TextureRegion(walking, i * 22, 0, 22, 45);
			}
			
			walkingAnimation = new Animation(0.150f, regions);
			walkingAnimation.setPlayMode(PlayMode.LOOP);
			
			standing = regions[standingFrame];
		}
		
		public TextureRegion getFrame(State playerState, float playerStateTime) {
			TextureRegion keyFrame = null;
			if (playerState == State.WALKING) {
				keyFrame = walkingAnimation.getKeyFrame(playerStateTime, true);
			} else if (playerState == State.STANDING) {
				keyFrame = standing;
			}
			if (invert) {
				keyFrame.flip(keyFrame.isFlipX(), false);
			} else {
				keyFrame.flip(!keyFrame.isFlipX(), false);
			}
			return keyFrame;
		}
		
		@Override
		public void dispose() {
			walking.dispose();
		}
		
	}

}
