package thwack.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class Lifebar {

	
	float lifebarWidth=1;						//width of this lifebar
													//will change based on hp
	float lifebarHeight=2;						//height of this lifebar.  Everyone
													//is hard coded at 2.

	Texture lifebarImage;

	
	public Lifebar() {
		lifebarImage = null;

	}
	
	public Texture getImage() { return lifebarImage; }
	public float getWidth() { return lifebarWidth; }
	public float getHeight() { return lifebarHeight; }
	
	public void update(int curHP, int maxHP) {
		if (maxHP > 32) {
			System.out.println("TODO: Handle maxhp > 32 for lifebarS");
		}
		else {
			lifebarWidth = maxHP;
			Pixmap lifebarPixels = new Pixmap(32,32,Format.RGBA8888);
			
			int starty = lifebarPixels.getHeight() - (int)lifebarHeight -15;
			int endy = starty + (int)lifebarHeight;
			int startx = lifebarPixels.getWidth()/2 - (int)lifebarWidth;
			int endx = startx + (int)lifebarWidth;
			
			for (int y = starty; y< endy; y++) {
				for (int x =startx;x<endx;x++) {
					int curx = x - startx;
					if (curx < curHP ) lifebarPixels.setColor(Color.GREEN);
					else lifebarPixels.setColor(Color.BLACK);
					lifebarPixels.drawPixel(x,y);
				}
			}
			lifebarImage = new Texture(lifebarPixels,Format.RGBA8888,false);
			
		}
	}
	
	

	
}
