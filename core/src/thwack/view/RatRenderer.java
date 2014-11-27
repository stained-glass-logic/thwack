package thwack.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;

import thwack.Constants;
import thwack.model.Entity.DamageState;
import thwack.model.Entity.Direction;
import thwack.model.Entity.State;
import thwack.model.Rat;

import java.util.HashMap;
import java.util.Map;

public class RatRenderer implements Disposable {

	private SpriteBatch batch;

	private ShapeRenderer shapeRenderer;

	private TextureAtlas ratAtlas;

	private final Map<Direction, Animation> ratWalkAnim = new HashMap<Direction, Animation>();
	private final Map<Direction, Animation> ratBoredAnim1 = new HashMap<Direction, Animation>();
	private final Map<Direction, Animation> ratBoredAnim2 = new HashMap<Direction, Animation>();
	
	public RatRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;

		this.ratAtlas = new TextureAtlas(Gdx.files.internal("Rat-Packed/Rat.atlas"));

		float ratAnimSpeed = 0.075f;
		ratWalkAnim.put(Direction.DOWN, new Animation(ratAnimSpeed, ratAtlas.findRegions("Run Down/Rat Run Down"), PlayMode.LOOP));
		ratWalkAnim.put(Direction.LEFT, new Animation(ratAnimSpeed, ratAtlas.findRegions("Run Left/Rat Run Side"), PlayMode.LOOP));
		ratWalkAnim.put(Direction.RIGHT, new Animation(ratAnimSpeed, ratAtlas.findRegions("Run Right/Rat Run Side"), PlayMode.LOOP));
		ratWalkAnim.put(Direction.UP, new Animation(ratAnimSpeed, ratAtlas.findRegions("Run Up/Rat Run Up"), PlayMode.LOOP));
		ratBoredAnim1.put(Direction.DOWN, new Animation(ratAnimSpeed, ratAtlas.findRegions("Bored Down/Clean/Rat Clean Down"), PlayMode.LOOP));
		System.out.println("Adding rat animation: " + ratAtlas.findRegions("Bored Down/Clean/Rat Clean Down"));
		ratBoredAnim1.put(Direction.LEFT, new Animation(ratAnimSpeed, ratAtlas.findRegions("Bored Side/Clean/Rat Clean Side"), PlayMode.LOOP));
		System.out.println("Adding rat animation: " + ratAtlas.findRegions("Bored Side/Clean/Rat Clean Side"));
		ratBoredAnim1.put(Direction.RIGHT, new Animation(ratAnimSpeed, ratAtlas.findRegions("Bored Side/Clean/Rat Clean Side"), PlayMode.LOOP));
		System.out.println("Adding rat animation: " + ratAtlas.findRegions("Bored Side/Clean/Rat Clean Side"));
		ratBoredAnim1.put(Direction.UP, new Animation(ratAnimSpeed, ratAtlas.findRegions("Bored Up/Clean/Rat Clean Up"), PlayMode.LOOP));
		System.out.println("Adding rat animation: " + ratAtlas.findRegions("Bored Up/Clean/Rat Clean Up"));

		//i tweaked these to get the look times feeling right --radish
		ratBoredAnim2.put(Direction.DOWN, new Animation(.75f, ratAtlas.findRegions("Bored Down/Look/Rat Look Down"), PlayMode.LOOP));
		ratBoredAnim2.put(Direction.LEFT, new Animation(.75f, ratAtlas.findRegions("Bored Side/Look/Rat Look Side"), PlayMode.LOOP));
		ratBoredAnim2.put(Direction.RIGHT, new Animation(.75f, ratAtlas.findRegions("Bored Side/Look/Rat Look Side"), PlayMode.LOOP));
		ratBoredAnim2.put(Direction.UP, new Animation(.75f, ratAtlas.findRegions("Bored Up/Look/Rat Look Up"), PlayMode.LOOP));
}

	public void render(Rat rat) {
		if(rat.getDirection() == Direction.UP){
			rat.setPosition(rat.ratBody.getPosition().x - .5f, rat.ratBody.getPosition().y - .5f);
		} else if (rat.getDirection() == Direction.DOWN){
			rat.setPosition(rat.ratBody.getPosition().x - .5f, rat.ratBody.getPosition().y - .5f);
		} else if (rat.getDirection() == Direction.RIGHT){
			rat.setPosition(rat.ratBody.getPosition().x -1.35f, rat.ratBody.getPosition().y - .5f);
		} else if (rat.getDirection() == Direction.LEFT){
			rat.setPosition(rat.ratBody.getPosition().x -.75f, rat.ratBody.getPosition().y - .5f);
		}

		Direction dir = rat.getDirection();
		AtlasRegion currentRegion;
		Animation animation;

		switch (rat.getState()) {
				case RUNNING:
					animation = ratWalkAnim.get(dir);
					currentRegion = (AtlasRegion) animation.getKeyFrame(rat.getStateTime(), true);
					break;
				case BORED:
					animation = ratBoredAnim1.get(dir);
					currentRegion = (AtlasRegion) animation.getKeyFrame(rat.getStateTime(), true);
					break;
				case BORED2:
					animation = ratBoredAnim2.get(dir);
					currentRegion = (AtlasRegion) animation.getKeyFrame(rat.getStateTime(), true);
					break;
				default:
					animation = ratWalkAnim.get(rat.getDirection());
					currentRegion = (AtlasRegion) animation.getKeyFrame(0, true);
					break;
					
			
		}

		if (rat.getDamageState() == DamageState.PHYSICAL) {
			if(rat.getDamageStateTime() < .1f){
			batch.setColor(Color.RED);}
			else if(rat.getDamageStateTime() <= .1f){
			batch.setColor(Color.WHITE);
			rat.setDamageState(DamageState.OK);
			rat.setDamageStateTime(0);}
		}
		
		float width = currentRegion.getRegionWidth() / Constants.PIXELS_PER_METER * 2;
		float height = currentRegion.getRegionHeight() / Constants.PIXELS_PER_METER * 2;

		if (rat.getState() == State.TAKINGDAMAGE) {
			batch.setColor(Color.RED);
		}
		
		batch.draw(currentRegion, rat.getPosition().x, rat.getPosition().y, width, height);
		batch.setColor(Color.WHITE);
		
<<<<<<< HEAD
		batch.setColor(Color.WHITE);
		
=======
>>>>>>> origin/master
        rat.archivePosition();
	}

	@Override
	public void dispose() {
		ratAtlas.dispose();
	}

}