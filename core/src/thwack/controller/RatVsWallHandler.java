package thwack.controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import thwack.model.Rat;
import thwack.model.Box2DCell;

/**
 * @author Daniel Holderbaum
 */
public class RatVsWallHandler extends ContactHandler<Rat, Box2DCell> {

	@Override
	public void beginContact(Contact contact, Rat rat, Fixture fixtureA, Box2DCell entityB, Fixture fixtureB) {
		System.out.println("Rat hit wall");
		rat.velocity.set(0,0);
	}

	@Override
	public void endContact(Contact contact, Rat entityA, Fixture fixtureA, Box2DCell entityB, Fixture fixtureB) {
		System.out.println("Rat left wall");
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold, Rat entityA, Fixture fixtureA, Box2DCell entityB, Fixture fixtureB) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse, Rat entityA, Fixture fixtureA, Box2DCell entityB, Fixture fixtureB) {

	}
}