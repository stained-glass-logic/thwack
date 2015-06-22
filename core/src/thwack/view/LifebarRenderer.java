package thwack.view;


import thwack.model.entity.Lifebar;











import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public class LifebarRenderer implements Disposable {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	
	public LifebarRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
	}
	
	public void render(float xLoc, float yLoc, Lifebar lifebar) {	
		
		
		if (lifebar.getImage() != null) { 
			
			//batch.draw(lifebar.getImage(),xLoc  + lifebar.xOffset,yLoc + lifebar.yOffset, lifebar.getWidth(),lifebar.getHeight());
			//lifebar.getSprite().setAlpha(lifebarAlpha);
			Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, lifebar.lifebarAlpha); 
			batch.draw(lifebar.getSprite(),xLoc  + lifebar.xOffset,yLoc + lifebar.yOffset, lifebar.getWidth(),lifebar.getHeight());
            batch.setColor(c.r, c.g, c.b, 1f);
			
		}
		
		
		
	}
	
	@Override
	public void dispose() {
		
	}

	

}
