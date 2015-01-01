package thwack.view;



import thwack.model.entities.mobs.Aimer;
import thwack.model.entities.player.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class MinimapRenderer implements Disposable {

	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Array<Aimer> mobs;
	Player player;
	
	float minimapAlpha = 0.5f;
	Texture mapTerrainImage;
	
	int MINIMAP_WIDTH_PIXELS = 56;
	int MINIMAP_HEIGHT_PIXELS = 36;
	//these offsets are for walls or something
	int XOFFSET = 1;
	int YOFFSET = 1;
	

	public MinimapRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer,
			Array<Aimer> mobs, thwack.model.entities.player.Player player,
			TiledMap tiledMap) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
		this.mobs = mobs;
		this.player = player;
		updateMapTerrain(tiledMap);
		
	}

	public void render() {
	
		//batch.draw(mapTerrainImage,-20f,20f);
		batch.draw(mapTerrainImage,-20f,70f);
		Texture radarImage = new Texture(getUpdatedRadar(mobs), Format.RGBA8888, false);
		//batch.draw(radarImage,-20f,20f);
		batch.draw(radarImage,-20f,70f);
		
	}
	
	public void updateMapTerrain(TiledMap tiledMap) {
		
		Pixmap terrainPixels = new Pixmap(MINIMAP_WIDTH_PIXELS,MINIMAP_HEIGHT_PIXELS,Format.RGBA8888);
		
		//Dark grey for floor, light grey for walls
		Color floorColor = new Color(0.1f,0.1f,0.1f,minimapAlpha);
		Color wallColor = new Color(0.6f,0.6f,0.6f,minimapAlpha);
		
		terrainPixels.setColor(floorColor);
		
		
		//draw floors
		TiledMapTileLayer floor = (TiledMapTileLayer) tiledMap.getLayers().get("floor");
		for (int height = 0; height < floor.getHeight(); height++) {
			for (int width = 0; width < floor.getWidth(); width++) {
				Cell thisCell = floor.getCell(width,height);
				if (thisCell != null)
				{
					int tileId = thisCell.getTile().getId();
					if (tileId != 0) {
						terrainPixels.drawPixel((width*2),MINIMAP_HEIGHT_PIXELS - (height*2));
						terrainPixels.drawPixel((width*2)+1,MINIMAP_HEIGHT_PIXELS - (height*2));
						terrainPixels.drawPixel((width*2),MINIMAP_HEIGHT_PIXELS - (height*2)+1);
						terrainPixels.drawPixel((width*2)+1,MINIMAP_HEIGHT_PIXELS - (height*2)+1);
					}
				}
				else
				{
				}
			}
		}
		
		terrainPixels.setColor(wallColor);
		
		//draw walls
		TiledMapTileLayer walls = (TiledMapTileLayer) tiledMap.getLayers().get("walls");
		for (int height = 0; height < walls.getHeight(); height++) {
			for (int width = 0; width < walls.getWidth(); width++) {
				Cell thisCell = walls.getCell(width,height);
				if (thisCell != null)
				{
					int tileId = thisCell.getTile().getId();
					if (tileId != 0) {
						terrainPixels.drawPixel((width*2),MINIMAP_HEIGHT_PIXELS - (height*2));
						terrainPixels.drawPixel((width*2)+1,MINIMAP_HEIGHT_PIXELS - (height*2));
						terrainPixels.drawPixel((width*2),MINIMAP_HEIGHT_PIXELS - (height*2)+1);
						terrainPixels.drawPixel((width*2)+1,MINIMAP_HEIGHT_PIXELS - (height*2)+1);
					}
				}
				else
				{
				}
			}
		}
		
		//terrainPixels.drawPixel(0,0);
		//terrainPixels.drawPixel(MINIMAP_WIDTH_PIXELS-1,MINIMAP_HEIGHT_PIXELS-1);
		
		mapTerrainImage = new Texture(terrainPixels,Format.RGBA8888,false);
	}
	
	public Pixmap getUpdatedRadar(Array<Aimer> mobs) {
		
		//there is no particular reason why we offset x and y here.  It just worked.  Otherwise the pixels don't line up.
		//it's possible that I made a rounding error somewhere when converting floats to ints
		
		
		//draw rats on radar
		Pixmap retPixmap = new Pixmap(MINIMAP_WIDTH_PIXELS,MINIMAP_HEIGHT_PIXELS,Format.RGBA8888);
		for (int count = 0; count < mobs.size; count++) {
			if (mobs.get(count).active() == true)
			{
				retPixmap.setColor(Color.GREEN);
				retPixmap.drawPixel( (int)(mobs.get(count).getBody().getPosition().x)+XOFFSET, MINIMAP_HEIGHT_PIXELS - (int)(mobs.get(count).getBody().getPosition().y)+YOFFSET);
			}
		}
		
		//draw player on radar
		retPixmap.setColor(Color.WHITE);
		retPixmap.drawPixel( (int)(player.getBody().getPosition().x)+XOFFSET, MINIMAP_HEIGHT_PIXELS - (int)(player.getBody().getPosition().y)+YOFFSET       );
		
		
		return retPixmap;
		
	}
	
	public void dispose() {
		
	}
}
