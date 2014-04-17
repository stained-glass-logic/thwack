package thwack;

import java.util.Map;

import thwack.collision.CollisionVisitor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Mob
  implements Body, CollisionVisitor
{
  private final Circle circle;
  
  private Texture texture;

  public Mob() {
    this.circle = new Circle(50, 50, 32);
    this.texture = new Texture(Gdx.files.internal("spiderwasp1asheet.gif"));
  }

  @Override
  public void render(Map<String, Object> context)
  {
	  SpriteBatch batch = (SpriteBatch)context.get(ThwackGame.SPRITE_BATCH);
	  batch.draw(texture, circle.x, circle.y);
  }

  @Override
  public void update(float deltaTime, Map<String, Object> context)
  {
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
