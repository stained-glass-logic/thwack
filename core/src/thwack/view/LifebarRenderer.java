package thwack.view;


import thwack.model.entity.Lifebar;









import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public class LifebarRenderer implements Disposable {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	
	float lifebarAlpha = 0.5f;
	

	public LifebarRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
	}
	
	public void render(float xLoc, float yLoc, Lifebar lifebar) {	
		
		if (lifebar.getImage() != null) { 
			batch.draw(lifebar.getImage(),xLoc - (lifebar.getWidth() / 2),yLoc, lifebar.getWidth(),lifebar.getHeight());
			
		}
		
		
		
	}
	
	@Override
	public void dispose() {
		
	}

	

}
