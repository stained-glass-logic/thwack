package thwack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

import thwack.controller.MyContactListener;
import thwack.controller.PlayerController;
import thwack.model.Entity.Direction;
import thwack.model.*;
import thwack.view.PlayerRenderer;
import thwack.view.RatRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThwackGame extends ApplicationAdapter {
	Texture img;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	private Box2DDebugRenderer debugRenderer;
	private World world = new World(new Vector2(0, 0), false);
	private OrthographicCamera camera;

	private Map<String, Object> context = new HashMap<String, Object>();

	public static final String SPRITE_BATCH = "SPRITE_BATCH";
	private SpriteBatch batch;

	public static final String BITMAP_FONT = "BITMAP_FONT";
	private BitmapFont font;

	public static final String SHAPE_RENDERER = "SHAPE_RENDERER";
	private ShapeRenderer shapeRenderer;

	private Array<Updateable> updateables = new Array<Updateable>();

	private Player player;

	private PlayerController playerController;

	private PlayerRenderer playerRenderer;

	private Array<Mob> mobs = new Array<Mob>();

	// my attempt to add a bodydef
	private BodyDef bodyWestDef = new BodyDef();
	private BodyDef bodyEastDef = new BodyDef();
	private BodyDef bodyNorthDef = new BodyDef();
	private BodyDef bodySouthDef = new BodyDef();
	private BodyDef playerBodyDef = new BodyDef();
	private Body bodyWest;
	private Body bodyEast;
	private Body bodyNorth;
	private Body bodySouth;
	private Body playerBody;
	private int width = 4 / 2;
	private int height = 29 / 2;
	private TextureRegion region;
	private FixtureDef fixtureDefWest, fixtureDefEast, fixtureDefNorth, fixtureDefSouth;
	private FixtureDef playerDef;

	private Rat rat;
	private RatRenderer ratRenderer;
	
	@Override
	public void create() {

		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();

		world.setContactListener(new MyContactListener());

		float w = Gdx.graphics.getWidth() / 32;
		float h = Gdx.graphics.getHeight() / 32;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);

		// new code for collision goes here. to be replaced by a handler class
		// later

		batch = new SpriteBatch();

		tiledMap = new TmxMapLoader().load("DemoMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 16f,
				batch);

		int mapWidth = tiledMap.getProperties().get("width", Integer.class);
		int mapHeight = tiledMap.getProperties().get("height", Integer.class);

		font = new BitmapFont();
		context.put(BITMAP_FONT, font);

		shapeRenderer = new ShapeRenderer();

		playerRenderer = new PlayerRenderer(batch, shapeRenderer);

		/*//the walls
		{

			float ww = (width);
			float hh = (height);

			bodyWestDef.type = BodyType.StaticBody;
			bodyWestDef.position.set(2f, 20f);
			bodyWest = world.createBody(bodyWestDef);
			bodyWest.setUserData(wall);
			PolygonShape bodyShapeWest = new PolygonShape();
			bodyShapeWest.setAsBox(ww, hh);
			fixtureDefWest = new FixtureDef();

			fixtureDefWest.shape = bodyShapeWest;
			bodyWest.createFixture(fixtureDefWest);
			bodyShapeWest.dispose();


			bodyEastDef.type = BodyType.StaticBody;
			bodyEastDef.position.set(54f, 20f);
			bodyEast = world.createBody(bodyEastDef);
			bodyEast.setUserData(wall);
			PolygonShape bodyShapeEast = new PolygonShape();
			bodyShapeEast.setAsBox(ww, hh);
			fixtureDefEast = new FixtureDef();
			fixtureDefEast.shape = bodyShapeEast;
			bodyEast.createFixture(fixtureDefEast);
			bodyShapeEast.dispose();

			bodySouthDef.type = BodyType.StaticBody;
			bodySouthDef.position.set(28f, 4f);
			bodySouth = world.createBody(bodySouthDef);
			bodySouth.setUserData(wall);
			PolygonShape bodyShapeSouth = new PolygonShape();
			bodyShapeSouth.setAsBox(mapWidth, ww);
			fixtureDefSouth = new FixtureDef();
			fixtureDefSouth.shape = bodyShapeSouth;
			bodySouth.createFixture(fixtureDefSouth);
			bodyShapeSouth.dispose();

			bodyNorthDef.type = BodyType.StaticBody;
			bodyNorthDef.position.set(28f, 36f);
			bodyNorth = world.createBody(bodyNorthDef);
			bodyNorth.setUserData(wall);
			PolygonShape bodyShapeNorth = new PolygonShape();
			bodyShapeNorth.setAsBox(mapWidth, ww);
			fixtureDefNorth = new FixtureDef();
			fixtureDefNorth.shape = bodyShapeNorth;
			bodyNorth.createFixture(fixtureDefNorth);
			bodyShapeNorth.dispose();

		}*/
		
		
		
		
		
		player = new Player(world);

		Vector2 ratPos = new Vector2(6f, 20f);
		Vector2 ratSize = new Vector2(.5f, .5f);
		rat = new Rat(world, ratPos, ratSize);

		// and the player object code
		batch = new SpriteBatch();

		context.put(SPRITE_BATCH, batch);

		tiledMap = new TmxMapLoader().load("DemoMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 16f,
				batch);

		font = new BitmapFont();
		context.put(BITMAP_FONT, font);

		shapeRenderer = new ShapeRenderer();
		context.put(SHAPE_RENDERER, shapeRenderer);

		playerRenderer = new PlayerRenderer(batch, shapeRenderer);
		ratRenderer = new RatRenderer(batch, shapeRenderer);

		updateables.add(player);
		playerController = new PlayerController(camera);
		playerController.setPlayer(player);

		updateables.add(playerController);
		Gdx.input.setInputProcessor(playerController);
		// System.out.println((Gdx.graphics.getDeltaTime()));
		player.setPosition(player.playerBody.getPosition().x - .65f, player.playerBody.getPosition().y - .2f);
		rat.setPosition(rat.ratBody.getPosition().x, rat.ratBody.getPosition().y);
		addBox2D();
	}

	private void addBox2D () {
		float tileSize = 0;
		ArrayList<Box2DCell> cells = new ArrayList<Box2DCell>();
		MapLayer mapLayer = tiledMap.getLayers().get("walls");
		TiledMapTileLayer layer = (TiledMapTileLayer)mapLayer;
		tileSize = layer.getTileWidth();
		for (int y = 0; y < layer.getHeight(); y++) {
			for (int x = 0; x < layer.getWidth(); x++) {
				Cell cell = layer.getCell(x, y);
				if (cell == null) {
					continue;
				}
				if (cell.getTile() == null) {
					continue;
				}

				cells.add(new Box2DCell(cell, x, y));
			}
		}

		// X ___
		// l J
		// ----
		int i = cells.size() - 1;
		ArrayList<Box2DCell> yCells = new ArrayList<Box2DCell>();
		while (true) {
			Box2DCell c = cells.get(0);
			float x = c.x;
			while (true) {
				Box2DCell c2 = findCellToRight(cells, c, x);
				if (c2 != null) {
					cells.remove(c2);
					x++;
					i--;
				} else {
					break;
				}
			}
			float x2 = x - c.x + 1;
			if (x2 - 1 >= 1) {
				BodyDef bodyDef = new BodyDef();
				bodyDef.position.x = Pixels.toMeters(c.x * tileSize + x2 * tileSize / 2);
				bodyDef.position.y = Pixels.toMeters(c.y * tileSize + tileSize / 2);
				bodyDef.type = BodyType.StaticBody;
				Body body = world.createBody(bodyDef);
				body.setUserData(c);
				PolygonShape shape = new PolygonShape();
				shape.setAsBox(Pixels.toMeters(x2 * tileSize / 2), Pixels.toMeters(tileSize / 2));
				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape = shape;
				fixtureDef.friction = 0;
				Fixture f = body.createFixture(fixtureDef);
				f.setUserData("tile");
			}
			if (x2 - 1 == 0) {
				yCells.add(c);
			}
			cells.remove(c);
			if (cells.size() <= 0) {
				break;
			}
		}

		// Y
		// _
		// l l
		// l l
		// L _J
		if (yCells.size() != 0) {
			while (true) {
				Box2DCell c = yCells.get(0);
				float y = c.y;
				while (true) {
					Box2DCell c2 = findCellToDown(yCells, c, y);
					if (c2 != null) {
						yCells.remove(c2);
						y++;
					} else {
						break;
					}
				}
				float y2 = y - c.y + 1;
				if (y2 == 0) {
					y2 = 1;
				}
				BodyDef bodyDef = new BodyDef();
				bodyDef.position.x = Pixels.toMeters(c.x * tileSize + tileSize / 2);
				bodyDef.position.y = Pixels.toMeters(c.y * tileSize + y2 * tileSize / 2);
				bodyDef.type = BodyType.StaticBody;
				Body body = world.createBody(bodyDef);
				body.setUserData(c);
				PolygonShape shape = new PolygonShape();
				shape.setAsBox(Pixels.toMeters(tileSize / 2), Pixels.toMeters(y2 * tileSize / 2));
				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape = shape;
				fixtureDef.friction = 0;
				Fixture f = body.createFixture(fixtureDef);
				f.setUserData("tile");
				yCells.remove(c);
				if (yCells.size() <= 0) {
					break;
				}
			}
		}
	}
	
	public static final class Pixels {

		public static final float toMeters (float pixels) {
			return pixels / 16;
		}

	}

	
	private Box2DCell findCellToRight (ArrayList<Box2DCell> cells, Box2DCell c, float x) {
		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i) == c) {
				continue;
			}
			if (cells.get(i).y == c.y && cells.get(i).x == x + 1) {
				Box2DCell c2 = cells.get(i);
				cells.remove(i);
				return c2;
			}
		}
		return null;
	}

	private Box2DCell findCellToDown (ArrayList<Box2DCell> cells, Box2DCell c, float y) {
		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i) == c) {
				continue;
			}
			if (cells.get(i).y == y + 1 && cells.get(i).x == c.x) {
				Box2DCell c2 = cells.get(i);
				cells.remove(i);
				return c2;
			}
		}
		return null;
	}
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.1f, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.setView(camera);
		camera.position.set(player.playerBody.getPosition().x,
				player.playerBody.getPosition().y - (11 / 32), 0);
		player.setPosition(player.playerBody.getPosition().x -.65f, player.playerBody.getPosition().y);
		if(rat.getDirection() == Direction.UP){
			rat.setPosition(rat.ratBody.getPosition().x - .5f, rat.ratBody.getPosition().y - .5f);
		} else if (rat.getDirection() == Direction.DOWN){
			rat.setPosition(rat.ratBody.getPosition().x - .5f, rat.ratBody.getPosition().y - .5f);
		} else if (rat.getDirection() == Direction.RIGHT){
			rat.setPosition(rat.ratBody.getPosition().x -1.35f, rat.ratBody.getPosition().y - .5f);
		} else if (rat.getDirection() == Direction.LEFT){
			rat.setPosition(rat.ratBody.getPosition().x -.75f, rat.ratBody.getPosition().y - .5f);
		}
		camera.update();
		tiledMapRenderer.render();
		playerRenderer.render(player);

		ratRenderer.render(rat);
		float deltaTime = Gdx.graphics.getDeltaTime();

		for (Updateable updateable : updateables) {
			updateable.update(deltaTime, context);
		}

		debugRenderer.render(world, camera.combined);
		world.step(1 / 60f, 6, 2);
	}

}
