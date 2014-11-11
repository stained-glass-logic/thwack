package thwack.controller;

import java.util.Map;

import thwack.model.Player;
import thwack.model.Updateable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

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
		 
		if (Gdx.input.isKeyPressed(Keys.W)) {
            player.velocity.y += 1;
            player.setState(Player.State.WALKING);
        	direction.set(0, 1);
		}
		
		if (Gdx.input.isKeyPressed(Keys.S)) {
			player.velocity.y -= 1;
	        player.setState(Player.State.WALKING);    
			direction.set(0, -1);
		}
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			player.velocity.x -= 1;
			player.setState(Player.State.WALKING);
			direction.set(-1, 0);
		}
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			player.velocity.x += 1;
			player.setState(Player.State.WALKING);
			direction.set(1, 0);
		}
	
		player.move(direction);
		player.applyImpulse();
	}

	@Override
	public boolean keyDown(int keycode) {/*
		switch (keycode) {
		case Keys.J:
			player.setState(Player.State.ATTACKING);
			break;

		case Keys.W:
            player.velocity.y += 1;
            player.setState(Player.State.WALKING);
            break;
		case Keys.S:
            player.velocity.y -= 1;
            player.setState(Player.State.WALKING);
            break;
		case Keys.A:
			player.velocity.x -= 1;
			player.setState(Player.State.WALKING);
			break;
		case Keys.D:
			player.velocity.x += 1;
			player.setState(Player.State.WALKING);
			break;

		}*/
		//player.applyImpulse();
		return false;
	}

	 
    @Override
    public boolean keyUp(int keycode) {/*
            switch (keycode) {
                    case Keys.W:
                            player.velocity.y -= 1;
                            break;
                    case Keys.A:
                            player.velocity.x += 1;
                            break;
                    case Keys.S:
                            player.velocity.y += 1;
                            break;
                    case Keys.D:
                            player.velocity.x -= 1;
                            break;
            }
*/
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
