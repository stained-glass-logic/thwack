package thwack.model.entities.projectiles;

import thwack.model.entity.Entity;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class FireBall extends Projectile{

	public FireBall(World world, Entity shooter)
	{
		this.shooter = shooter;
		this.world = world;
		BodyDef projectileDef = new BodyDef();
		projectileDef.type = BodyType.DynamicBody;
		projectileDef.position.set(shooter.getBody().getPosition());
		
		entityBody = world.createBody(projectileDef);
		entityBody.setUserData(this);
		//projectileBody.setFixedRotation(true);
		
		CircleShape fireBallShape = new CircleShape();
		fireBallShape.setRadius(radius);
		
		FixtureDef projectileFixtureDef = new FixtureDef();
		projectileFixtureDef.shape = fireBallShape;
		projectileFixtureDef.isSensor = true;
		
		entityBody.createFixture(projectileFixtureDef);
		projectileFixtureDef.shape.dispose();
	}
	
	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void dispose() {
		world.destroyBody(entityBody);
	}

}
