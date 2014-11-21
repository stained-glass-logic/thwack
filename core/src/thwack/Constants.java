package thwack;

import com.badlogic.gdx.math.Vector2;

public interface Constants {

	float PIXELS_PER_METER = 32;
	float WORLD_WIDTH_METERS = 100F;
	float WORLD_HEIGHT_METERS = 100F;

	Vector2 LEFT = new Vector2(-1, 0);
	Vector2 RIGHT = new Vector2(1, 0);
	Vector2 UP = new Vector2(0, 1);
	Vector2 DOWN = new Vector2(0, -1);
	float GLOBAL_VOLUME = 0.75f;
	boolean AUDIO_ON = true;
}
