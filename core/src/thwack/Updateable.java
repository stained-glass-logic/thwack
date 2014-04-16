package thwack;

import java.util.Map;

public interface Updateable {
	void update(float deltaTime, Map<String, Object> context);
}
