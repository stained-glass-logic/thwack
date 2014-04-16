package thwack;

import java.util.HashMap;
import java.util.Map;

import collision.CollisionContext;
import collision.CollisionVisitor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class ThwackGame extends ApplicationAdapter {
	
	private OrthographicCamera camera;
	
	private Map<String, Object> context = new HashMap<String, Object>();
	
	public static final String SPRITE_BATCH = "SPRITE_BATCH";
	private SpriteBatch batch;
	
	public static final String BITMAP_FONT = "BITMAP_FONT";
	private BitmapFont font;
	
	public static final String SHAPE_RENDERER = "SHAPE_RENDERER";
	private ShapeRenderer shapeRenderer;
	
	private CollisionContext collisionContext;
	
	private Array<Renderable> renderables = new Array<Renderable>();
	
	private Array<Updateable> updateables = new Array<Updateable>();
	
	private Array<Disposable> disposables = new Array<Disposable>();
	
	@Override
	public void create () {
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		
		batch = new SpriteBatch();
		context.put(SPRITE_BATCH, batch);
		disposables.add(batch);

		font = new BitmapFont();
		context.put(BITMAP_FONT, font);
		disposables.add(font);
		
		shapeRenderer = new ShapeRenderer();
		context.put(SHAPE_RENDERER, shapeRenderer);
		disposables.add(shapeRenderer);

		collisionContext = new CollisionContext();
		context.put(CollisionContext.COLLISION, collisionContext);

		Player p = new Player(16, 16, 5);
		addGameObject(p);
		
		for (int i = 0; i < 1000; i++) {
			Block b = new Block(MathUtils.random(750) + 50, MathUtils.random(550) + 50, 5, 5);
			addGameObject(b);
		}
	}
	
	private void addGameObject(Object obj) {
		
		if (obj instanceof Renderable) {
			renderables.add((Renderable)obj);
		}
		
		if (obj instanceof Updateable) {
			updateables.add((Updateable)obj);
		}
		
		if (obj instanceof CollisionVisitor) {
			collisionContext.add((CollisionVisitor)obj);
		}
	}
	
	private void removeGameObject(Object obj) {
		if (obj instanceof Renderable) {
			renderables.removeValue((Renderable)obj, true);
		}
		
		if (obj instanceof Updateable) {
			updateables.removeValue((Updateable)obj, true);
		}
		
		if (obj instanceof CollisionVisitor) {
			collisionContext.remove((CollisionVisitor)obj);
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		float deltaTime = Gdx.graphics.getDeltaTime();
	
		for (Updateable updateable : updateables) {
			updateable.update(deltaTime, context);
		}
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		batch.begin();

		for (Renderable renderable : renderables) {
			renderable.render(context);
		}
		
		shapeRenderer.end();
		batch.end();
		
	}
	
	@Override
	public void dispose() {
		for (Disposable disposable : disposables) {
			disposable.dispose();
		}
	}
}
