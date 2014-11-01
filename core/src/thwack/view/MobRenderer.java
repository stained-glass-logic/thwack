package thwack.view;

import thwack.model.Mob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Disposable;

public class MobRenderer implements Disposable {

	private SpriteBatch batch;
	
	private ShapeRenderer shapeRenderer;

	private Texture texture;

	private Animation flyingAnimation;

	private static final int IMAGE_WIDTH = 74;
	private static final int IMAGE_HEIGHT = 60;

	public MobRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;

		TextureRegion[] regions = new TextureRegion[4];

		for (int i = 0; i < 4; i++) {
			regions[i] = new TextureRegion(texture, i * IMAGE_WIDTH, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		}

		flyingAnimation = new Animation(0.08f, regions);
	}

	public void render(Mob mob) {
		mob.increaseStateTime(Gdx.graphics.getDeltaTime());
		TextureRegion currentRegion = flyingAnimation.getKeyFrame(mob.getStateTime(), true);

		batch.begin();
		if (mob.isFacingLeft()) {
			currentRegion.flip(currentRegion.isFlipX(), false);
		} else {
			currentRegion.flip(!currentRegion.isFlipX(), false);
		}

		batch.draw(currentRegion, mob.getPosition().x, mob.getPosition().y, mob.getBounds().width, mob.getBounds().height);
		batch.end();

		if (shapeRenderer != null) {
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.rect(mob.getPosition().x, mob.getPosition().y, mob.getBounds().width, mob.getBounds().height);
			shapeRenderer.end();
		}
	}

	@Override
	public void dispose() {
		texture.dispose();
	}

}
