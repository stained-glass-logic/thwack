package thwack.view;

import java.util.HashMap;
import java.util.Map;

import thwack.Constants;
import thwack.model.Player;
import thwack.model.Player.Direction;
import thwack.model.Player.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class PlayerRenderer implements Disposable {
	
	private SpriteBatch batch;
	
	private ShapeRenderer shapeRenderer;
	
	private TextureAtlas heroAtlas;
	
	private final Map<Direction, Animation> walking = new HashMap<Direction, Animation>();
	private final Map<Direction, Animation> attack = new HashMap<Direction, Animation>();
	private final Map<Direction, Animation> attackSword = new HashMap<Direction, Animation>();
	
	public PlayerRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
		
		this.heroAtlas = new TextureAtlas(Gdx.files.internal("Hero-packed/Hero.atlas"));
		
		float walkingSpeed = 0.075f;
		walking.put(Direction.DOWN, new Animation(walkingSpeed, heroAtlas.findRegions("Running/Down/running_down"), PlayMode.LOOP));
		
		Array<AtlasRegion> runningSidewaysRegions = this.heroAtlas.findRegions("Running/Side/running_side");
		walking.put(Direction.LEFT, new Animation(walkingSpeed, runningSidewaysRegions, PlayMode.LOOP));
		walking.put(Direction.RIGHT, new Animation(walkingSpeed, runningSidewaysRegions, PlayMode.LOOP));
		walking.put(Direction.UP, new Animation(walkingSpeed, heroAtlas.findRegions("Running/Up/running_up"), PlayMode.LOOP));
		
		float attackSpeed = 0.100f;
		attack.put(Direction.DOWN, new Animation(attackSpeed, heroAtlas.findRegions("Attack/Down/Strike/down_strike")));
		
		Array<AtlasRegion> rightStrikeRegions = heroAtlas.findRegions("Attack/Right/Strike/right_strike");
		attack.put(Direction.RIGHT, new Animation(attackSpeed, rightStrikeRegions));
		attack.put(Direction.LEFT, new Animation(attackSpeed, rightStrikeRegions));
		
		attackSword.put(Direction.DOWN, new Animation(attackSpeed, heroAtlas.findRegions("Attack/Down/Strike/Sword/down_strike_sword")));

		Array<AtlasRegion> rightStrikeSwordRegions = heroAtlas.findRegions("Attack/Right/Strike/Sword/right_strike_sword");
		attackSword.put(Direction.RIGHT, new Animation(attackSpeed, rightStrikeSwordRegions));
		attackSword.put(Direction.LEFT, new Animation(attackSpeed, rightStrikeSwordRegions));
	}
	
	public void render(Player player) {
		
		player.increaseStateTime(Gdx.graphics.getDeltaTime());

		AtlasRegion currentRegion = null;
		AtlasRegion weaponRegion = null;
		Animation animation = null;
		Animation weaponAnimation = null;
		
		switch (player.getState()) {
		case ATTACKING:
//			Direction dir = player.getDirection();
			Direction dir = Direction.DOWN;
			
			animation = attack.get(dir);
			weaponAnimation = attackSword.get(dir);
			
			if (animation.isAnimationFinished(player.getStateTime())) {
				player.setState(State.STANDING);
				animation = walking.get(player.getDirection());
				currentRegion = (AtlasRegion)animation.getKeyFrames()[0];
			} else {
				currentRegion = (AtlasRegion)animation.getKeyFrame(player.getStateTime(), false);
				weaponRegion = (AtlasRegion)weaponAnimation.getKeyFrame(player.getStateTime(), false);
			}
			break;
		case STANDING:
			animation = walking.get(player.getDirection());
			currentRegion = (AtlasRegion)animation.getKeyFrames()[0];
			break;
		case WALKING:
			animation = walking.get(player.getDirection());
			currentRegion = (AtlasRegion)animation.getKeyFrame(player.getStateTime(), true);
			break;
		}
	
		if (player.getDirection() == Direction.LEFT) {
			currentRegion.flip(currentRegion.isFlipX(), false);
			if (weaponRegion != null) {
				weaponRegion.flip(weaponRegion.isFlipX(), false);
			}
		} else if (player.getDirection() == Direction.RIGHT) {
			currentRegion.flip(!currentRegion.isFlipX(), false);
			if (weaponRegion != null) {
				weaponRegion.flip(!weaponRegion.isFlipX(), false);
			}
		}
		
		player.getBounds().setWidth(currentRegion.originalWidth / Constants.PIXELS_PER_METER);
		player.getBounds().setHeight(currentRegion.originalHeight / Constants.PIXELS_PER_METER);
		
		float width = currentRegion.getRegionWidth() / Constants.PIXELS_PER_METER;
		float height = currentRegion.getRegionHeight() / Constants.PIXELS_PER_METER;
		
		float weaponWidth = 0.0f;
		float weaponHeight = 0.0f;
		float weaponX = 0.0f;
		float weaponY = 0.0f;
		if (weaponRegion != null) {
			weaponX = player.getPosition().x - (currentRegion.offsetX - weaponRegion.offsetX) / Constants.PIXELS_PER_METER; 
			weaponY = player.getPosition().y - (currentRegion.offsetY - weaponRegion.offsetY) / Constants.PIXELS_PER_METER; 
			weaponWidth = weaponRegion.getRegionWidth() / Constants.PIXELS_PER_METER;
			weaponHeight = weaponRegion.getRegionHeight() / Constants.PIXELS_PER_METER;
		}
		
		batch.begin();
		batch.draw(currentRegion, player.getPosition().x, player.getPosition().y, width, height);
			
		if (weaponRegion != null) {
			batch.draw(weaponRegion, weaponX, weaponY, weaponWidth, weaponHeight);
		}
		
		batch.end();
		
//		shapeRenderer.begin(ShapeType.Line);
//		shapeRenderer.setColor(Color.WHITE);
//		shapeRenderer.rect(player.getPosition().x, player.getPosition().y, width, height);
//		shapeRenderer.end();
		
	}
	
	@Override
	public void dispose() {
		heroAtlas.dispose();
	}
}
