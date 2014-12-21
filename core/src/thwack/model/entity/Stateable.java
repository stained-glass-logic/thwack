package thwack.model.entity;

public abstract class Stateable {
	
	public EntityState trigger = EntityState.ACTIVE;
	
	public enum EntityState {
		ACTIVE, // ENTITY IS ACTIVE
		DESTROY, // FLAG FOR DESTROY
		TRIGGERED; //EVENT TRIGGERED
	}
	
	public EntityState getPublicState()
	{
		return trigger; 
	}
	
	public void setPublicState(EntityState trigger)
	{
		this.trigger = trigger;
	}
	
}
