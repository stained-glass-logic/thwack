package thwack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ThwackGame extends ApplicationAdapter {
	
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	int tileSize = 25;
	
	TextureRegion region;
	
	Vector2 location = new Vector2(0, 0);
	
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 400, 300);
		
		batch = new SpriteBatch();
		img = new Texture(Gdx.files.internal("baldguy.gif"));
		
		region = new TextureRegion(img, 0, 0, 20, 25);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			location.y += 200 * Gdx.graphics.getDeltaTime();
		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			location.y -= 200 * Gdx.graphics.getDeltaTime();
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			location.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			location.x += 200 * Gdx.graphics.getDeltaTime();
		}
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(region, location.x, location.y);
		
		batch.end();
	}
	
	@Override
	public void dispose() {
		if (img != null) {
			img.dispose();
		}
	}
	
	
}
