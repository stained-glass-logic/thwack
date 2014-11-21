package thwack.model;

public interface Updateable {
	void update(float deltaTime);
	boolean active();
}
