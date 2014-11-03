package thwack;

import java.util.HashMap;
import java.util.Map;

import thwack.collision.CollisionContext;
import thwack.controller.PlayerController;
import thwack.model.Block;
import thwack.model.Mob;
import thwack.model.Player;
import thwack.model.Updateable;
import thwack.view.BlockRenderer;
import thwack.view.MobRenderer;
import thwack.view.PlayerRenderer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class ThwackGame extends ApplicationAdapter {
	
    Texture img;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
	
	private OrthographicCamera camera;
	
	private Map<String, Object> context = new HashMap<String, Object>();
	
	public static final String SPRITE_BATCH = "SPRITE_BATCH";
	private SpriteBatch batch;
	
	public static final String BITMAP_FONT = "BITMAP_FONT";
	private BitmapFont font;
	
	public static final String SHAPE_RENDERER = "SHAPE_RENDERER";
	private ShapeRenderer shapeRenderer;
	
	private CollisionContext collisionContext;
	
	private Array<Updateable> updateables = new Array<Updateable>();
	
	private Array<Disposable> disposables = new Array<Disposable>();
	
	private Player player;
	
	private PlayerController playerController;
	
	private PlayerRenderer playerRenderer;
	
	private MobRenderer mobRenderer;
	
	private Array<Mob> mobs = new Array<Mob>();
	
	private BlockRenderer blockRenderer;
	
	private Array<Block> blocks = new Array<Block>();
	
	@Override
	public void create () {
        float w = Gdx.graphics.getWidth() / 32;
        float h = Gdx.graphics.getHeight() / 32;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		

	    
		batch = new SpriteBatch();
		
		context.put(SPRITE_BATCH, batch);
		disposables.add(batch);

		tiledMap = new TmxMapLoader().load("DemoMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/16f, batch);

	    int mapWidth = tiledMap.getProperties().get("width",Integer.class);
	    int mapHeight = tiledMap.getProperties().get("height",Integer.class);

		font = new BitmapFont();
		context.put(BITMAP_FONT, font);
		disposables.add(font);
		
		shapeRenderer = new ShapeRenderer();
		context.put(SHAPE_RENDERER, shapeRenderer);
		disposables.add(shapeRenderer);

		collisionContext = new CollisionContext();
		context.put(CollisionContext.COLLISION, collisionContext);
	
		playerRenderer = new PlayerRenderer(batch, shapeRenderer);
		
		blockRenderer = new BlockRenderer(shapeRenderer);
		
//		mobRenderer = new MobRenderer(batch, shapeRenderer);

		player = new Player();
		updateables.add(player);
		collisionContext.add(player);
		
		playerController = new PlayerController(camera);
		playerController.setPlayer(player);
		
		updateables.add(playerController);
		Gdx.input.setInputProcessor(playerController);
		System.out.println((Gdx.graphics.getDeltaTime()));
		player.setPosition(20, 20);
		
		
//		for (int i = 0; i < 10; i++) {
//			Mob b = new Mob();
//			
//			do {
//				b.setPosition(MathUtils.random(20.0f) - 10.0f, MathUtils.random(20.0f) - 10.0f);
//			} while (b.collidesWith(player));
//			
//			mobs.add(b);
//			collisionContext.add(b);
//		}
//		
//		for (int i = 0; i < 10; i++) {
//			Block b = new Block();
//			
//			do {
//				b.setPosition(MathUtils.random(20.0f) - 10.0f,  MathUtils.random(20.0f) - 10.0f);
//			} while (b.collidesWith(player));
//			
//			blocks.add(b);
//			collisionContext.add(b);
//		}
		
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.1f, 1);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.setView(camera);
		camera.position.set(player.getPositionX() + (42 / 32), player.getPositionY() - (11 / 32), 0);
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
		
		for (Block b : blocks) {
			blockRenderer.render(b);
		}
	}
	
	@Override
	public void dispose() {
		for (Disposable disposable : disposables) {
			disposable.dispose();
		}
	}
	
	//@Override
	/*public void resize(int width, int height) {
		Vector3 oldpos = new Vector3(camera.position);
		camera.setToOrtho(false, width/Constants.PIXELS_PER_METER, height/Constants.PIXELS_PER_METER);
		camera.translate(oldpos.x - camera.position.x, oldpos.y - camera.position.y);
	}*/

}
