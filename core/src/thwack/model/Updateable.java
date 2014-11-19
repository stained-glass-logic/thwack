package thwack.model;

import java.util.Map;

public interface Updateable {
	void update(float deltaTime, Map<String, Object> context);
	boolean active();
}
