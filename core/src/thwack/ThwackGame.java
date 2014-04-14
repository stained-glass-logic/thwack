package thwack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class ThwackGame extends ApplicationAdapter {
	OrthographicCamera camera;
	ShapeRenderer shapeRenderer;
	
	Player p = new Player();
	Block block = new Block();
	
	Array<Block> objects = new Array<Block>();

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		shapeRenderer = new ShapeRenderer();
		
		objects.add(block);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);

		float deltaTime = Gdx.graphics.getDeltaTime();
		
		p.update(deltaTime, objects);
		
		p.render(shapeRenderer);
		block.render(shapeRenderer);
		
	}
	
	@Override
	public void dispose() {
		if (shapeRenderer != null) {
			shapeRenderer.dispose();
		}
	}
	
}
