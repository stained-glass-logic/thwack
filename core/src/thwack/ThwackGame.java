package thwack;

import java.util.HashMap;
import java.util.Map;

import collision.CollisionContext;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class ThwackGame extends ApplicationAdapter {
	OrthographicCamera camera;
	
	Map<String, Object> context = new HashMap<String, Object>();
	
	SpriteBatch batch;
	BitmapFont font;
	ShapeRenderer shapeRenderer;
	CollisionContext collisionContext;
	
	Player p;
	Array<Block> blocks = new Array<Block>();
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		collisionContext = new CollisionContext();
		
		context.put(CollisionContext.COLLISION, collisionContext);

		p = new Player(16, 16, 5);

		collisionContext.add(p);
		
		for (int i = 0; i < 1000; i++) {
			blocks.add(new Block(MathUtils.random(750) + 50, MathUtils.random(550) + 50, 5, 5));
		}
		
		collisionContext.addAll(blocks);
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.graphics.getFramesPerSecond();
		
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);

		float deltaTime = Gdx.graphics.getDeltaTime();
		
		p.update(deltaTime, context);
		
		p.render(shapeRenderer);
		
		for (Block block: blocks) {
			block.render(shapeRenderer);
		}
		
		batch.begin();
		font.setColor(Color.WHITE);
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 300, 300);
		batch.end();
		
	}
	
	@Override
	public void dispose() {
		if (shapeRenderer != null) {
			shapeRenderer.dispose();
		}
	}
	
}
