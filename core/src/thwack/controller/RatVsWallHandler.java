package thwack.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import thwack.model.Rat;
import thwack.model.Wall;

/**
 * @author Daniel Holderbaum
 */
public class RatVsWallHandler extends ContactHandler<Rat, Wall> {

	@Override
	public void beginContact(Contact contact, Rat rat, Fixture fixtureA, Wall entityB, Fixture fixtureB) {

	}

	@Override
	public void endContact(Contact contact, Rat entityA, Fixture fixtureA, Wall entityB, Fixture fixtureB) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold, Rat entityA, Fixture fixtureA, Wall entityB, Fixture fixtureB) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse, Rat entityA, Fixture fixtureA, Wall entityB, Fixture fixtureB) {

	}
}