package thwack;

import java.util.HashMap;
import java.util.Map;

import thwack.controller.PlayerController;
import thwack.model.Mob;
import thwack.model.Player;
import thwack.model.Rat;
import thwack.model.Updateable;
import thwack.view.BlockRenderer;
import thwack.view.MobRenderer;
import thwack.view.PlayerRenderer;
import thwack.view.RatRenderer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

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

	private MobRenderer mobRenderer;

	private Array<Mob> mobs = new Array<Mob>();

	private BlockRenderer blockRenderer;


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

		//the walls
		{

			float ww = (width);
			float hh = (height);

			bodyWestDef.type = BodyType.StaticBody;
			bodyWestDef.position.set(2f, 20f);
			bodyWest = world.createBody(bodyWestDef);
			PolygonShape bodyShapeWest = new PolygonShape();
			bodyShapeWest.setAsBox(ww, hh);
			fixtureDefWest = new FixtureDef();

			fixtureDefWest.shape = bodyShapeWest;
			bodyWest.createFixture(fixtureDefWest);
			bodyShapeWest.dispose();
			

			bodyEastDef.type = BodyType.StaticBody;
			bodyEastDef.position.set(54f, 20f);
			bodyEast = world.createBody(bodyEastDef);
			PolygonShape bodyShapeEast = new PolygonShape();
			bodyShapeEast.setAsBox(ww, hh);
			fixtureDefEast = new FixtureDef();
			fixtureDefEast.shape = bodyShapeEast;
			bodyEast.createFixture(fixtureDefEast);
			bodyShapeEast.dispose();
			
			bodySouthDef.type = BodyType.StaticBody;
			bodySouthDef.position.set(28f, 4f);
			bodySouth = world.createBody(bodySouthDef);
			PolygonShape bodyShapeSouth = new PolygonShape();
			bodyShapeSouth.setAsBox(mapWidth, ww);
			fixtureDefSouth = new FixtureDef();
			fixtureDefSouth.shape = bodyShapeSouth;
			bodySouth.createFixture(fixtureDefSouth);
			bodyShapeSouth.dispose();

			bodyNorthDef.type = BodyType.StaticBody;
			bodyNorthDef.position.set(28f, 36f);
			bodyNorth = world.createBody(bodyNorthDef);
			PolygonShape bodyShapeNorth = new PolygonShape();
			bodyShapeNorth.setAsBox(mapWidth, ww);
			fixtureDefNorth = new FixtureDef();
			fixtureDefNorth.shape = bodyShapeNorth;
			bodyNorth.createFixture(fixtureDefNorth);
			bodyShapeNorth.dispose();

		}
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

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.1f, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.setView(camera);
		camera.position.set(player.playerBody.getPosition().x,
				player.playerBody.getPosition().y - (11 / 32), 0);
		player.setPosition(player.playerBody.getPosition().x -.65f, player.playerBody.getPosition().y);
		camera.update();
		tiledMapRenderer.render();
		playerRenderer.render(player);
		float deltaTime = Gdx.graphics.getDeltaTime();

		for (Updateable updateable : updateables) {
			updateable.update(deltaTime, context);
		}

		for (Mob mob : mobs) {
			mobRenderer.render(mob);
		}

		debugRenderer.render(world, camera.combined);
		world.step(1 / 60f, 6, 2);
	}


	// @Override
	/*
	 * public void resize(int width, int height) { Vector3 oldpos = new
	 * Vector3(camera.position); camera.setToOrtho(false,
	 * width/Constants.PIXELS_PER_METER, height/Constants.PIXELS_PER_METER);
	 * camera.translate(oldpos.x - camera.position.x, oldpos.y -
	 * camera.position.y); }
	 */

}
