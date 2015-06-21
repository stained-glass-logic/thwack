package thwack.model.entity;

import thwack.Global;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Lifebar {

	
	float lifebarWidth=1;						//width of this lifebar
													//will change based on hp
	float lifebarHeight=2;						//height of this lifebar.  Everyone
													//is hard coded at 2.

	Texture lifebarImage;
	Sprite lifebarSprite;
	
	public float scaleFactor=16;
	public float xOffset;							//offset based on mob's size
	public float yOffset =-3;
	public float lifebarAlpha = 0f;
	public float lifebarFadeTimer = 0.0f;

	
	public Lifebar() {
		lifebarImage = null;

	}
	
	public Texture getImage() { return lifebarImage; }
	public Sprite getSprite() { return lifebarSprite; }
	public float getWidth() { return lifebarWidth; }
	public float getHeight() { return lifebarHeight; }
	
	public void updateTime(float deltaTime)
	{
		//Global.DebugOutLine("" + deltaTime);
		if (lifebarFadeTimer < 0)
		{
			lifebarAlpha -= deltaTime;
			if (lifebarAlpha < 0) lifebarAlpha = 0;
		}
		else lifebarFadeTimer -= deltaTime;
	}

	public void makeVisible()
	{
		lifebarAlpha = 1.0f;
		lifebarFadeTimer = 2.0f;
	}
	
	public void updateImage(int curHP, int maxHP) {
		if (maxHP > 32) {
			Global.DebugOutLine("TODO: Handle maxhp > 32 for lifebarS");
		}
		else {
			lifebarWidth = maxHP;
			xOffset =  - ( (lifebarWidth) / scaleFactor);
			Pixmap lifebarPixels = new Pixmap(32,32,Format.RGBA8888);
			
			int starty = 0;
			int startx = 0;
			int endy = (int)lifebarHeight;
			int endx = (int)lifebarWidth;
			
			for (int y = starty; y< endy; y++) {
				for (int x =startx;x<endx;x++) {
					int curx = x - startx;
					if (curx < curHP ) lifebarPixels.setColor(Color.GREEN);
					else lifebarPixels.setColor(Color.BLACK);
					lifebarPixels.drawPixel(x,y);
				}
			}
			lifebarImage = new Texture(lifebarPixels,Format.RGBA8888,false);
			lifebarSprite = new Sprite(lifebarImage);
			lifebarSprite.setAlpha(0.1f);
			
		}
	}
	
	

	
}
