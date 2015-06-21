package thwack.view;

import java.util.HashMap;
import java.util.Map;

import thwack.Constants;
import thwack.model.entities.mobs.Mob.State;
import thwack.model.entities.mobs.Rat;
import thwack.model.entity.Damageable.DamageState;
import thwack.model.entity.Movable.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;

public class RatRenderer implements Disposable {

	private SpriteBatch batch;

	private TextureAtlas ratAtlas;

	private final Map<Direction, Animation> ratWalkAnim = new HashMap<Direction, Animation>();
	private final Map<Direction, Animation> ratBoredAnim1 = new HashMap<Direction, Animation>();
	private final Map<Direction, Animation> ratBoredAnim2 = new HashMap<Direction, Animation>();
	
	public RatRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		//this.shapeRenderer = shapeRenderer;

		this.ratAtlas = new TextureAtlas(Gdx.files.internal("Rat-Packed/Rat.atlas"));

		float ratAnimSpeed = 0.075f;
		ratWalkAnim.put(Direction.DOWN, new Animation(ratAnimSpeed, ratAtlas.findRegions("Run Down/Rat Run Down"), PlayMode.LOOP));
		ratWalkAnim.put(Direction.LEFT, new Animation(ratAnimSpeed, ratAtlas.findRegions("Run Left/Rat Run Side"), PlayMode.LOOP));
		ratWalkAnim.put(Direction.RIGHT, new Animation(ratAnimSpeed, ratAtlas.findRegions("Run Right/Rat Run Side"), PlayMode.LOOP));
		ratWalkAnim.put(Direction.UP, new Animation(ratAnimSpeed, ratAtlas.findRegions("Run Up/Rat Run Up"), PlayMode.LOOP));
		ratBoredAnim1.put(Direction.DOWN, new Animation(ratAnimSpeed, ratAtlas.findRegions("Bored Down/Clean/Rat Clean Down"), PlayMode.LOOP));
		ratBoredAnim1.put(Direction.LEFT, new Animation(ratAnimSpeed, ratAtlas.findRegions("Bored Side/Clean/Rat Clean Side"), PlayMode.LOOP));
		ratBoredAnim1.put(Direction.RIGHT, new Animation(ratAnimSpeed, ratAtlas.findRegions("Bored Side/Clean/Rat Clean Side"), PlayMode.LOOP));
		ratBoredAnim1.put(Direction.UP, new Animation(ratAnimSpeed, ratAtlas.findRegions("Bored Up/Clean/Rat Clean Up"), PlayMode.LOOP));

		//i tweaked these to get the look times feeling right --radish
		ratBoredAnim2.put(Direction.DOWN, new Animation(.75f, ratAtlas.findRegions("Bored Down/Look/Rat Look Down"), PlayMode.LOOP));
		ratBoredAnim2.put(Direction.LEFT, new Animation(.75f, ratAtlas.findRegions("Bored Side/Look/Rat Look Side"), PlayMode.LOOP));
		ratBoredAnim2.put(Direction.RIGHT, new Animation(.75f, ratAtlas.findRegions("Bored Side/Look/Rat Look Side"), PlayMode.LOOP));
		ratBoredAnim2.put(Direction.UP, new Animation(.75f, ratAtlas.findRegions("Bored Up/Look/Rat Look Up"), PlayMode.LOOP));
}

	public void render(Rat rat) {
		Vector2 position = new Vector2();
		
		if(rat.state == State.RUNNING){
			if(rat.direction == Direction.UP){
				position.set(rat.getBody().getPosition().x - .5f, rat.getBody().getPosition().y - .5f);
			} else if (rat.direction == Direction.DOWN){
				position.set(rat.getBody().getPosition().x - .5f, rat.getBody().getPosition().y - .5f);
			} else if (rat.direction == Direction.RIGHT){
				position.set(rat.getBody().getPosition().x -1.35f, rat.getBody().getPosition().y - .5f);
			} else if (rat.direction == Direction.LEFT){
				position.set(rat.getBody().getPosition().x -.75f, rat.getBody().getPosition().y - .5f);
			}
			}
		if(rat.state == State.BORED || rat.state == State.BORED2){
			if(rat.direction == Direction.UP){
				position.set(rat.getBody().getPosition().x - .5f, rat.getBody().getPosition().y - .5f);
			} else if (rat.direction == Direction.DOWN){
				position.set(rat.getBody().getPosition().x - .5f, rat.getBody().getPosition().y - .5f);
			} else if (rat.direction == Direction.RIGHT){
				position.set(rat.getBody().getPosition().x -1.35f, rat.getBody().getPosition().y - .5f);
			} else if (rat.direction == Direction.LEFT){
				position.set(rat.getBody().getPosition().x -.5f, rat.getBody().getPosition().y - .5f);
			}
			}
		Direction dir = rat.direction;
		AtlasRegion currentRegion;
		Animation animation;
		TextureRegion image2 = new TextureRegion(new Texture(Gdx.files.internal("Hit_1.png")));
		Image image = new Image(image2);
		image.setScaling(Scaling.fill);
		
		switch (rat.state) {
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
					animation = ratBoredAnim1.get(dir);
					currentRegion = (AtlasRegion) animation.getKeyFrame(rat.getStateTime(), true);
					break;
					
			
		}
		float width = currentRegion.getRegionWidth() / Constants.PIXELS_PER_METER * 2;
		float height = currentRegion.getRegionHeight() / Constants.PIXELS_PER_METER * 2;
		
		TextureRegion region = (currentRegion);
		//next 2 lines draw the shadow
		batch.setColor( 0, 0, 0,  0.6f);
		
		batch.draw(region,  rat.getBody().getPosition().x - width/2, rat.getBody().getPosition().y - height/2 , (float) (width * 1.5) , height / 4);
		
		batch.setColor(Color.WHITE);
		
		batch.draw(currentRegion, rat.getBody().getPosition().x - width/2, rat.getBody().getPosition().y - height/2, width, height);
		
		if (rat.damageState == DamageState.PHYSICAL) {
			if(rat.getDamageStateTime() < .1f){
			
			image.setPosition(rat.getLastContactX() - .5f, rat.getLastContactY() - .5f);
			image.setSize(image2.getTexture().getWidth() / 32, image2.getTexture().getHeight() / 32);

			image.draw(batch, 1f);
			batch.setColor(Color.RED);
			batch.draw(currentRegion, rat.getBody().getPosition().x - width/2, rat.getBody().getPosition().y - height/2, width, height);
			image.draw(batch, 1f);}
			else if(rat.getDamageStateTime() <= .1f){
			
			image2.getTexture().dispose();
			batch.setColor(Color.WHITE);
			rat.damageState = DamageState.OK;
			rat.setDamageStateTime(0);
			}

		}
		
		batch.setColor(Color.WHITE);

        //rat.archivePosition();
	}

	@Override
	public void dispose() {
		ratAtlas.dispose();
	}

}