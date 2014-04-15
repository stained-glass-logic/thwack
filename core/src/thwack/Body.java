package thwack;

import java.util.Map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Body {
	void update(float deltaTime, Map<String, Object> context);
	void render(ShapeRenderer shapeRenderer);		
}
