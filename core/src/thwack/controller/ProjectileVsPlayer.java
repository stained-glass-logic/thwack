package thwack.controller;

import thwack.model.entities.player.Player;
import thwack.model.entities.projectiles.Projectile;
import thwack.model.entity.Stateable.EntityState;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ProjectileVsPlayer extends ContactHandler<Projectile, Player> {

	@Override
	public void beginContact(Contact contact, Projectile entityA,
			Fixture fixtureA, Player entityB, Fixture fixtureB) {
		
		if(contact.isTouching())
		{
			entityA.setPublicState(EntityState.DESTROY);
		}
	}

	@Override
	public void endContact(Contact contact, Projectile entityA,
			Fixture fixtureA, Player entityB, Fixture fixtureB) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold,
			Projectile entityA, Fixture fixtureA, Player entityB,
			Fixture fixtureB) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse,
			Projectile entityA, Fixture fixtureA, Player entityB,
			Fixture fixtureB) {
		// TODO Auto-generated method stub
		
	}

}
