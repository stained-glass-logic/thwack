package thwack.model;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Box2DCell extends Entity {
	public Box2DCell (Cell cell, float x, float y) {
		this.cell = cell;
		this.x = x;
		this.y = y;
	}

	public Box2DCell() {
		// TODO Auto-generated constructor stub
	}

	public Cell cell;
	public float x, y;
}