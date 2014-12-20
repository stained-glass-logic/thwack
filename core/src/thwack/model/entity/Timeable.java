package thwack.model.entity;

public interface Timeable {

	/**
	 * @param deltaTime increase time since last pass
	 */
	public void increaseStateTime(float deltaTime);
	
	/**
	 * @return stateTime velocity of the object
	 */
	public float getStateTime();
	
	/**
	 * @return stateTime set the stateTime of the object
	 */
	public void setStateTime(float stateTime);
}
