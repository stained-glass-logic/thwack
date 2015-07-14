package thwack.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class UiRenderer implements Disposable {
	
	Texture lifebarLeft;
	Texture lifebarMid;
	Texture lifebarRight;
	Texture lifebarGem;
	
	Texture weaponIconBorder;
	Texture weaponIconSword;
	Texture weaponIconHammer;
	
	public UiRenderer()
	{
		lifebarLeft = new Texture(Gdx.files.internal("Health_Bar_01.png"));
		lifebarMid = new Texture(Gdx.files.internal("Health_Bar_02.png"));
		lifebarRight = new Texture(Gdx.files.internal("Health_Bar_03.png"));
		lifebarGem = new Texture(Gdx.files.internal("Health_Bar_04.png"));
		
		weaponIconBorder = new Texture(Gdx.files.internal("Weapon_Border_01.png"));
		weaponIconHammer = new Texture(Gdx.files.internal("Weapon_Icon_Hammer_01.png"));
		weaponIconSword = new Texture(Gdx.files.internal("Weapon_Icon_Sword_01.png"));
		
		
		
	}
	
	public void render(SpriteBatch batch)
	{
		float lifebarX = -160f;
		float lifebarY = 100f;
		float lifebarSizePx = 25f;	//half the size of the sprite because scaling
		
		batch.draw(lifebarLeft,lifebarX,lifebarY,lifebarSizePx,lifebarSizePx);
		batch.draw(lifebarMid,lifebarX+18,lifebarY, lifebarSizePx,lifebarSizePx);
		batch.draw(lifebarRight,lifebarX+31,lifebarY,lifebarSizePx,lifebarSizePx);
		
		batch.draw(lifebarGem,lifebarX+6,lifebarY,lifebarSizePx,lifebarSizePx);
		batch.draw(lifebarGem,lifebarX+12,lifebarY, lifebarSizePx,lifebarSizePx);
		batch.draw(lifebarGem,lifebarX+18,lifebarY,lifebarSizePx,lifebarSizePx);
		batch.draw(lifebarGem,lifebarX+24,lifebarY,lifebarSizePx,lifebarSizePx);
		batch.draw(lifebarGem,lifebarX+30,lifebarY,lifebarSizePx,lifebarSizePx);
		
		float weaponsX = 80f;
		float weaponsY = 93f;
		float weaponsSizePx = 32f;	//half the size of the sprite because scaling
		
		batch.draw(weaponIconBorder,weaponsX,weaponsY,weaponsSizePx,weaponsSizePx);
		batch.draw(weaponIconBorder,weaponsX+24,weaponsY,weaponsSizePx,weaponsSizePx);
		batch.draw(weaponIconSword,weaponsX,weaponsY,weaponsSizePx,weaponsSizePx);
		batch.draw(weaponIconHammer,weaponsX+24,weaponsY,weaponsSizePx,weaponsSizePx);
		
	}
	
	public void dispose()
	{
		
	}

}
