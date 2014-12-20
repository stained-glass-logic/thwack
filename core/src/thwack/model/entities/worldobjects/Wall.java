package thwack.model.entities.worldobjects;

import thwack.model.entity.Entity;

public class Wall extends Entity {

	@Override
	public void dispose() {
		world.destroyBody(entityBody);
	}

}
