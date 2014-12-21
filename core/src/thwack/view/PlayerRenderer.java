package thwack.view;

import java.util.HashMap;
import java.util.Map;

import thwack.Constants;
import thwack.model.entities.player.Player;
import thwack.model.entities.player.Player.State;
import thwack.model.entity.Movable.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class PlayerRenderer implements Disposable {

	private SpriteBatch batch;

	private TextureAtlas heroAtlas;

	private final Map<Direction, Animation> walking = new HashMap<Direction, Animation>();
	private final Map<Direction, Animation> attack = new HashMap<Direction, Animation>();
	private final Map<Direction, Animation> attackSword = new HashMap<Direction, Animation>();

	public PlayerRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		//this.shapeRenderer = shapeRenderer;

		this.heroAtlas = new TextureAtlas(Gdx.files.internal("Hero-packed/Hero.atlas"));

		float walkingSpeed = 0.075f;
		walking.put(Direction.DOWN, new Animation(walkingSpeed, heroAtlas.findRegions("Running/Down/running_down"), PlayMode.LOOP));

		Array<AtlasRegion> runningSidewaysRegions = this.heroAtlas.findRegions("Running/Side/running_side");
		walking.put(Direction.LEFT, new Animation(walkingSpeed, runningSidewaysRegions, PlayMode.LOOP));
		walking.put(Direction.RIGHT, new Animation(walkingSpeed, runningSidewaysRegions, PlayMode.LOOP));
		walking.put(Direction.UP, new Animation(walkingSpeed, heroAtlas.findRegions("Running/Up/running_up"), PlayMode.LOOP));

		float attackSpeed = 0.04f;
		attack.put(Direction.DOWN, new Animation(attackSpeed, heroAtlas.findRegions("Attack/Down/attack_down")));
		attack.put(Direction.LEFT, new Animation(attackSpeed, heroAtlas.findRegions("Attack/Left/attack_left")));
		attack.put(Direction.RIGHT, new Animation(attackSpeed, heroAtlas.findRegions("Attack/Right/attack_right")));
		attack.put(Direction.UP, new Animation(attackSpeed, heroAtlas.findRegions("Attack/Up/attack_up")));

		attackSword.put(Direction.DOWN, new Animation(attackSpeed, heroAtlas.findRegions("Attack/Down/Sword/attack_down_sword")));
		attackSword.put(Direction.LEFT, new Animation(attackSpeed, heroAtlas.findRegions("Attack/Left/Sword/attack_left_sword")));
		attackSword.put(Direction.RIGHT, new Animation(attackSpeed, heroAtlas.findRegions("Attack/Right/Sword/attack_right_sword")));
		attackSword.put(Direction.UP, new Animation(attackSpeed, heroAtlas.findRegions("Attack/Up/Sword/attack_up_sword")));
	}

	public void render(Player player) {
		//player.setPosition(player.getBody().getPosition().x -.65f, player.getBody().getPosition().y);

		player.increaseStateTime(Gdx.graphics.getDeltaTime());

		AtlasRegion currentRegion = null;
		AtlasRegion weaponRegion = null;
		Animation animation = null;
		Animation weaponAnimation = null;

		switch (player.state) {
			case ATTACKING:
				Direction dir = player.direction;

				animation = attack.get(dir);
				weaponAnimation = attackSword.get(dir);

				if (animation.isAnimationFinished(player.getStateTime())) {
					//System.out.println("this should be working");
					player.setState(State.STANDING);
					animation = walking.get(player.direction);
					currentRegion = (AtlasRegion)animation.getKeyFrames()[0];
				} else {
					//System.out.println("why isn't this working");
					currentRegion = (AtlasRegion)animation.getKeyFrame(player.getStateTime(), false);
					weaponRegion = (AtlasRegion)weaponAnimation.getKeyFrame(player.getStateTime(), false);
				}
				break;
			case STANDING:
				animation = walking.get(player.direction);
				currentRegion = (AtlasRegion)animation.getKeyFrames()[0];
				break;
			case WALKING:
				animation = walking.get(player.direction);
				currentRegion = (AtlasRegion)animation.getKeyFrame(player.getStateTime(), true);
				if (player.direction == Direction.LEFT) {
				  currentRegion.flip(currentRegion.isFlipX(), false);
				} else if (player.direction == Direction.RIGHT) {
				  currentRegion.flip(!currentRegion.isFlipX(), false);
				}
				break;
			case BORED:
				break;
			case RUNNING:
				break;
			default:
				break;
		}

		float width = currentRegion.getRegionWidth() / Constants.PIXELS_PER_METER * 2;
		float height = currentRegion.getRegionHeight() / Constants.PIXELS_PER_METER * 2;

		float weaponWidth = 0.0f;
		float weaponHeight = 0.0f;
		float weaponX = 0.0f;
		float weaponY = 0.0f;
		if (weaponRegion != null) {
			weaponX = player.getBody().getPosition().x - (currentRegion.offsetX - weaponRegion.offsetX) / Constants.PIXELS_PER_METER * 2;
			weaponY = player.getBody().getPosition().y - ((currentRegion.offsetY - weaponRegion.offsetY) / Constants.PIXELS_PER_METER * 2);
			weaponWidth = weaponRegion.getRegionWidth() / Constants.PIXELS_PER_METER * 2;
			weaponHeight = weaponRegion.getRegionHeight() / Constants.PIXELS_PER_METER * 2;
		}

		TextureRegion region = (currentRegion);
		//next 2 lines draw the shadow
		batch.setColor((int) 0, (int) 0, (int) 0, (float) 0.6);
		batch.draw(region, player.getBody().getPosition().x - width/2, player.getBody().getPosition().y - height/4, (float) (width * 1.5) , height / 4); 
		
		batch.setColor(Color.WHITE);
		batch.draw(currentRegion, player.getBody().getPosition().x - width/2, player.getBody().getPosition().y - height/4, width, height);
		if (weaponRegion != null) {
			batch.draw(weaponRegion, weaponX, weaponY - .5f, weaponWidth, weaponHeight);
		}

	}

	@Override
	public void dispose() {
		heroAtlas.dispose();
	}

}
