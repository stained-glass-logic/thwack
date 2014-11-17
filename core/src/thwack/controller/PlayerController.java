package thwack.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import thwack.model.Player;
import thwack.model.Updateable;

import java.util.Map;

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

		if(player.getState() != Player.State.ATTACKING){
		if (Gdx.input.isKeyPressed(Keys.W)) {
			direction.add(0, 1);
            player.velocity.y += 1;
            player.setState(Player.State.WALKING);
		}

		if (Gdx.input.isKeyPressed(Keys.S)) {
			player.velocity.y -= 1;
	        player.setState(Player.State.WALKING);
			direction.add(0, -1);
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			player.velocity.x -= 1;
			player.setState(Player.State.WALKING);
			direction.add(-1, 0);
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			player.velocity.x += 1;
			player.setState(Player.State.WALKING);
			direction.add(1, 0);
		}
		}
		if (Gdx.input.isKeyPressed(Keys.J)){
			direction.set(0,0);
			player.setState(Player.State.ATTACKING);
		}

		player.move(direction);
		player.applyImpulse();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}


    @Override
    public boolean keyUp(int keycode) {
		player.applyImpulse();

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
