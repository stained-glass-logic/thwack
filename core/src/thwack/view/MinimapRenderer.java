package thwack.view;



import thwack.Dungeon;
import thwack.model.entities.mobs.MobGroup;
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
import com.badlogic.gdx.utils.Disposable;

public class MinimapRenderer implements Disposable {

	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	//MobGroup mobs;
	Player player;
	
	float minimapAlpha = 0.5f;
	Texture mapTerrainImage;
	Pixmap terrainPixels;
	Pixmap updatedRadar;
	Texture radarImage;
	
	int MINIMAP_WIDTH_PIXELS = 64;
	int MINIMAP_HEIGHT_PIXELS = 64;
	//these offsets are for walls or something
	int XOFFSET = 0;
	int YOFFSET = 1;
	

	public MinimapRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer,
			MobGroup mobs, thwack.model.entities.player.Player player,
			TiledMap tiledMap) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
		//this.mobs = mobs;
		this.player = player;
		updateMapTerrain(tiledMap);
		
	}

	public void render(Dungeon thisDungeon) {
	
		MobGroup mobs = thisDungeon.getCurrentStage().getMobs();
		batch.draw(mapTerrainImage,-20f,70f);
		if (updatedRadar != null) updatedRadar.dispose();
		updatedRadar = getUpdatedRadar(mobs);
		if (radarImage != null) radarImage.dispose();
		radarImage = new Texture(updatedRadar, Format.RGBA8888, false);
		batch.draw(radarImage,-20f,70f);
		
	}
	
	public void updateMapTerrain(TiledMap tiledMap) {
		
		if (terrainPixels != null) terrainPixels.dispose();
		terrainPixels = new Pixmap(MINIMAP_WIDTH_PIXELS,MINIMAP_HEIGHT_PIXELS,Format.RGBA8888);
		
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
		
		if (mapTerrainImage != null) mapTerrainImage.dispose();
		mapTerrainImage = new Texture(terrainPixels,Format.RGBA8888,false);
		
	}
	
	public Pixmap getUpdatedRadar(MobGroup mobs) {
		
		//there is no particular reason why we offset x and y here.  It just worked.  Otherwise the pixels don't line up.
		//it's possible that I made a rounding error somewhere when converting floats to ints
		
		
		//draw rats on radar
		//Global.DebugOutLine("On minimap, ,drawing " + mobs.getRatCount() + " rats and " + mobs.getAimerCount() + "aimers.");
		Pixmap retPixmap = new Pixmap(MINIMAP_WIDTH_PIXELS,MINIMAP_HEIGHT_PIXELS,Format.RGBA8888);
		
		
		for (int count = 0; count < mobs.getRatCount(); count++) {
			if (mobs.getRatByIndex(count) != null && mobs.getRatByIndex(count).active() == true)
			{
				retPixmap.setColor(Color.GREEN);
				retPixmap.drawPixel( (int)(mobs.getRatByIndex(count).getBody().getPosition().x)+XOFFSET, MINIMAP_HEIGHT_PIXELS - (int)(mobs.getRatByIndex(count).getBody().getPosition().y)+YOFFSET);
			}
		}
		//draw aimers on radar
		for (int count = 0; count < mobs.getAimerCount(); count++) {
			if (mobs.getAimerByIndex(count) != null && mobs.getAimerByIndex(count).active() == true)
			{
				retPixmap.setColor(Color.OLIVE);
				retPixmap.drawPixel( (int)(mobs.getAimerByIndex(count).getBody().getPosition().x)+XOFFSET, MINIMAP_HEIGHT_PIXELS - (int)(mobs.getAimerByIndex(count).getBody().getPosition().y)+YOFFSET);
			}
		}
		//draw sawrats on radar
		for (int count = 0; count < mobs.getSawRatCount(); count++) {
			if (mobs.getSawRatByIndex(count) != null && mobs.getSawRatByIndex(count).active() == true)
			{
				retPixmap.setColor(Color.BLUE);
				retPixmap.drawPixel( (int)(mobs.getSawRatByIndex(count).getBody().getPosition().x)+XOFFSET, MINIMAP_HEIGHT_PIXELS - (int)(mobs.getSawRatByIndex(count).getBody().getPosition().y) + YOFFSET);
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
