package thwack.model.entity;

public interface Updateable extends Timeable {
	
	void update(float deltaTime);
	boolean active();
	void setActive(boolean isActive);
}
