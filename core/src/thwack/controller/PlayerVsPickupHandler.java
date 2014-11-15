package thwack.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import thwack.model.Player;

/**
 * @author Daniel Holderbaum
 */
public class PlayerVsPickupHandler extends ContactHandler<Player, Pickup> {
 
	@Override
	public void beginContact(Contact contact, Player entityA, Fixture fixtureA, Pickup entityB, Fixture fixtureB) {
		// TODO Auto-generated method stub
 
	}
 
	@Override
	public void endContact(Contact contact, Player entityA, Fixture fixtureA, Pickup entityB, Fixture fixtureB) {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void preSolve(Contact contact, Manifold oldManifold, Player entityA, Fixture fixtureA, Pickup entityB, Fixture fixtureB) {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse, Player entityA, Fixture fixtureA, Pickup entityB, Fixture fixtureB) {
		// TODO Auto-generated method stub
	}
 
}