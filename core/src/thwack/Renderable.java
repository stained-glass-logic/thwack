package thwack;

import java.util.Map;

public interface Renderable {
	void render(float deltaTime, Map<String, Object> context);		
}
