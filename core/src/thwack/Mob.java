package thwack;

import java.util.Map;

import thwack.collision.CollisionContext;
import thwack.collision.CollisionVisitor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Mob
  implements Body, CollisionVisitor
{
  private Circle circle;
  
  private boolean facingLeft = true;
  
  private Texture texture;
  
  private Animation flyingAnimation;
  
  private Sprite currentRegion;

  private float speed = 200.0f;
  
  private float stateTime;
  
  public Mob() {
    this.circle = new Circle(50, 50, 32);
    this.texture = new Texture(Gdx.files.internal("spiderwasp1asheet.gif"));
    
    TextureRegion[] regions = new TextureRegion[4];
    
    int imageWidth = 74;
    int imageHeight = 60;
    
    for (int i = 0; i < 4; i++) {
    	regions[i] = new Sprite(texture, i * imageWidth, 0, imageWidth, imageHeight);
    }
    
    flyingAnimation = new Animation(0.06f, regions);
    
    stateTime = 0f;
    
  }

  @Override
  public void render(float deltaTime, Map<String, Object> context)
  {
	  SpriteBatch batch = (SpriteBatch)context.get(ThwackGame.SPRITE_BATCH);
	  
	  stateTime += deltaTime;
	  currentRegion = (Sprite)flyingAnimation.getKeyFrame(stateTime, true);
	  
	  batch.begin();
	  if (facingLeft) {
		  currentRegion.flip(currentRegion.isFlipX(), false);
	  } else {
		  currentRegion.flip(!currentRegion.isFlipX(), false);
	  }
	  batch.draw(currentRegion, circle.x - circle.radius, circle.y - circle.radius);
	  batch.end();
	  
//	  ShapeRenderer renderer = (ShapeRenderer)context.get(ThwackGame.SHAPE_RENDERER);
//	  renderer.begin(ShapeType.Line);
//	  renderer.circle(circle.x, circle.y, circle.radius);
//	  renderer.end();
  }

  @Override
  public void update(float deltaTime, Map<String, Object> context)
  {
		float oldX = circle.x;
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			circle.x -= speed * deltaTime;
			facingLeft = true;
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			circle.x += speed * deltaTime;
			facingLeft = false;
		}
		
		CollisionContext collisionContext = (CollisionContext)context.get(CollisionContext.COLLISION);
		
		Array<CollisionVisitor> objects = collisionContext.getCollisionCandidates();
		
		boolean collidedX = false;
		for (CollisionVisitor obj : objects) {
			if (this.collidesWith(obj)) {
				collidedX = true;
				break;
			}
		}
		
		if (collidedX) {
			circle.x = oldX;
		}
		
		float oldY = circle.y;
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			circle.y += speed * deltaTime;
		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			circle.y -= speed * deltaTime;
		}
		
		boolean collidedY = false;
		for (CollisionVisitor obj : objects) {
			if (this.collidesWith(obj)) {
				collidedY = true;
				break;
			}
		}
		
		if (collidedY) {
			circle.y = oldY;
		}

  }

  @Override
  public boolean collidesWith(CollisionVisitor visitor) {
    return this != visitor && visitor.visit(this.circle);
  }
  
  @Override
  public boolean visit(Circle circle) {
    return Intersector.overlaps(circle, this.circle);
  }
  
  public boolean visit(Rectangle rect) {
    return Intersector.overlaps(this.circle, rect);
  }

}
