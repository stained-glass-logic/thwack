package thwack.view;

import java.util.HashMap;
import java.util.Map;

import thwack.Constants;
import thwack.model.Mob.Direction;
import thwack.model.Mob.State;
import thwack.model.Rat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class RatRenderer implements Disposable {
	
	private SpriteBatch batch;
	
	private ShapeRenderer shapeRenderer;
	
	private TextureAtlas ratAtlas;
	
	private final Map<Direction, Animation> running = new HashMap<Direction, Animation>();


	public RatRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
		
		this.ratAtlas = new TextureAtlas(Gdx.files.internal("Rat-packed/Rat.atlas"));
		
		float runningSpeed = 0.075f;
		running.put(Direction.DOWN, new Animation(runningSpeed, ratAtlas.findRegions("Rat/run down"), PlayMode.LOOP));
		running.put(Direction.LEFT, new Animation(runningSpeed, ratAtlas.findRegions("Rat/run left"), PlayMode.LOOP));
		running.put(Direction.RIGHT, new Animation(runningSpeed, ratAtlas.findRegions("Rat/run right"), PlayMode.LOOP));
		running.put(Direction.UP, new Animation(runningSpeed, ratAtlas.findRegions("Rat/running up"), PlayMode.LOOP));
		
	}

	public void render(Rat rat) {
		
		rat.increaseStateTime(Gdx.graphics.getDeltaTime());

		AtlasRegion currentRegion = null;
		Animation animation = null;

		switch (rat.getState()) {
		
		case RUNNING:
			animation = running.get(rat.getDirection());
			currentRegion = (AtlasRegion)animation.getKeyFrame(rat.getStateTime(), true);
	    if (rat.getDirection() == Direction.LEFT) {
	      currentRegion.flip(currentRegion.isFlipX(), false);
	    } else if (rat.getDirection() == Direction.RIGHT) {
	      currentRegion.flip(!currentRegion.isFlipX(), false);
	    }
			break;
		case BORED:
			animation = running.get(rat.getDirection());
			currentRegion = (AtlasRegion)animation.getKeyFrame(rat.getStateTime(), true);
			break;
		case STANDING:
			animation = running.get(rat.getDirection());
			currentRegion = (AtlasRegion)animation.getKeyFrame(rat.getStateTime(), true);
			break;
		default:
			animation = running.get(rat.getDirection());
			currentRegion = (AtlasRegion)animation.getKeyFrame(rat.getStateTime(), true);
			break;
		}
		
		float width = currentRegion.getRegionWidth() / Constants.PIXELS_PER_METER * 2;
		float height = currentRegion.getRegionHeight() / Constants.PIXELS_PER_METER * 2;

		batch.begin();
		batch.draw(currentRegion, rat.getPosition().x, rat.getPosition().y, width, height);
		batch.end();
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	}