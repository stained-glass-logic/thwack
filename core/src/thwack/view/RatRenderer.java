package thwack.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import thwack.Constants;
import thwack.model.Entity.Direction;
import thwack.model.Entity.State;
import thwack.model.Rat;

import java.util.HashMap;
import java.util.Map;

public class RatRenderer implements Disposable {

	private SpriteBatch batch;

	private ShapeRenderer shapeRenderer;

	private TextureAtlas ratAtlas;

	private final Map<Direction, Animation> ratAnim = new HashMap<Direction, Animation>();

	private Boolean ratBool = false;

	public RatRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;

		this.ratAtlas = new TextureAtlas(Gdx.files.internal("Rat-Packed/Rat.atlas"));

		float ratAnimSpeed = 0.075f;
		ratAnim.put(Direction.DOWN, new Animation(ratAnimSpeed, ratAtlas.findRegions("run down/rat run down"), PlayMode.LOOP));
		ratAnim.put(Direction.LEFT, new Animation(ratAnimSpeed, ratAtlas.findRegions("run left/rat run left"), PlayMode.LOOP));
		ratAnim.put(Direction.RIGHT, new Animation(ratAnimSpeed, ratAtlas.findRegions("run right/rat run right"), PlayMode.LOOP));
		ratAnim.put(Direction.UP, new Animation(ratAnimSpeed, ratAtlas.findRegions("run up/rat run up"), PlayMode.LOOP));

	}

    // why is the rat logic not in the Rat object?
	private void ratLogic(Rat rat, float time) {
		if (rat.getStateTime() < time) {
			rat.setState(State.RUNNING);
			if (ratBool == false) {
				rat.velocity.set(MathUtils.random((int) -1, (int) 1), MathUtils.random((int) -1, (int) 1));
				ratBool = true;
			}
		} else if (rat.getStateTime() > time) {
			rat.velocity.set(0, 0);
			rat.setState(State.BORED);
			rat.setStateTime(0);
			ratBool = false;
		}
		rat.move(rat.velocity);
		rat.applyImpulse();
	}

	public void render(Rat rat) {
		ratLogic(rat, 3f);
		rat.increaseStateTime(Gdx.graphics.getDeltaTime());
		Direction dir = rat.getDirection();
		AtlasRegion currentRegion = null;
		Animation animation = null;

		if (rat.isMoving()) {
			switch (rat.getState()) {
				case RUNNING:
					animation = ratAnim.get(rat.getDirection());
					currentRegion = (AtlasRegion) animation.getKeyFrame(rat.getStateTime(), true);
					break;
				case BORED:
					animation = ratAnim.get(rat.getDirection());
					currentRegion = (AtlasRegion) animation.getKeyFrame(rat.getStateTime(), true);
					break;
				default:
					animation = ratAnim.get(rat.getDirection());
					currentRegion = (AtlasRegion) animation.getKeyFrame(rat.getStateTime(), true);
					break;
			}
		} else {
			animation = ratAnim.get(rat.getDirection());
			currentRegion = (AtlasRegion) animation.getKeyFrame(0, true);
		}

		float width = currentRegion.getRegionWidth() / Constants.PIXELS_PER_METER * 2;
		float height = currentRegion.getRegionHeight() / Constants.PIXELS_PER_METER * 2;

		batch.begin();
		batch.draw(currentRegion, rat.getPosition().x, rat.getPosition().y, width, height);
		batch.end();

        rat.archivePosition();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}