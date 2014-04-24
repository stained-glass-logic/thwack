package thwack.controller;

import java.util.Map;

import thwack.model.Player;
import thwack.model.Updateable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PlayerController implements Updateable, InputProcessor {
	
	private final Camera camera;
	private Player player;
	private Vector2 direction = new Vector2(0.0f, 0.0f);
	
	public PlayerController(Camera camera) {
		this.camera = camera;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void update(float deltaTime, Map<String, Object> context) {
		direction.set(0, 0);
		 
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			direction.add(0, 1);
		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			direction.add(0, -1);
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			direction.add(-1, 0);
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			direction.add(1, 0);
		}

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			v = camera.unproject(v);
			
			direction.set(v.x, v.y);
			direction.sub(player.getCenter());
			
			player.move(direction);
		}

		player.move(direction);
		
	}
	

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
