package thwack.model.entities.projectiles;

import thwack.model.entity.Entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class FireOrbital extends Projectile{
	
	private World world;
	private Joint joint;
	
	private float lengthofOrbital = 2f;
	public FireOrbital(World world, Entity shooter, int deg)
	{
		this.shooter = shooter;
		this.world = world;
		BodyDef projectileDef = new BodyDef();
		projectileDef.type = BodyType.DynamicBody;
		projectileDef.position.set(shooter.getBody().getPosition());
		projectileDef.active = true;
		
		entityBody = world.createBody(projectileDef);
		entityBody.setUserData(this);
		//projectileBody.setFixedRotation(true);
		
		CircleShape fireBallShape = new CircleShape();
		fireBallShape.setRadius(radius);
		
		FixtureDef projectileFixtureDef = new FixtureDef();
		projectileFixtureDef.shape = fireBallShape;
		projectileFixtureDef.isSensor = true;
		projectileFixtureDef.density = 0.0001f;
		projectileFixtureDef.restitution = 1;
		projectileFixtureDef.friction = 0;
		entityBody.createFixture(projectileFixtureDef);
		projectileFixtureDef.shape.dispose();
		
		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.bodyA = shooter.getBody();
		revoluteJointDef.bodyB = entityBody;
		revoluteJointDef.enableMotor = true;
		revoluteJointDef.collideConnected = false;
		revoluteJointDef.motorSpeed = 360f * MathUtils.degreesToRadians;
		revoluteJointDef.maxMotorTorque = 20f;
		revoluteJointDef.localAnchorB.set(entityBody.getLocalCenter().add(MathUtils.sin(deg * MathUtils.degreesToRadians)/-lengthofOrbital *3, MathUtils.cos(deg * MathUtils.degreesToRadians)/-lengthofOrbital *3));
		revoluteJointDef.localAnchorA.set(shooter.getBody().getLocalCenter());
		
		joint = world.createJoint(revoluteJointDef);
		
	}
	
	@Override
	public void update(float deltaTime) {
		
	}

	@Override
	public boolean active() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void dispose() {
		world.destroyJoint(joint);
		world.destroyBody(entityBody);
		
	}
}
