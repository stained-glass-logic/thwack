package thwack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import thwack.screen.GameScreen;

public class ThwackGame extends Game {
	private TiledMap tiledMap;

	@Override
	public void create() {
		tiledMap = new TmxMapLoader().load("DemoMap.tmx");

		setScreen(new GameScreen(tiledMap));
	}

}
