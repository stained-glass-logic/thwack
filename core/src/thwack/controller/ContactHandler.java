package thwack.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sun.xml.internal.stream.Entity;

/**
 * This class will be called by the {@link ContactListener} and will
 * handle all collisions between two types of {@link Entity}s.
 * 
 * @author Daniel Holderbaum
 */
public abstract class ContactHandler<A extends Entity, B extends Entity> {
 
	public abstract void beginContact(Contact contact, A entityA, Fixture fixtureA, B entityB, Fixture fixtureB);
 
	public abstract void endContact(Contact contact, thwack.model.Entity entityA, Fixture fixtureA, thwack.model.Entity entityB, Fixture fixtureB);
 
	public abstract void preSolve(Contact contact, Manifold oldManifold, A entityA, Fixture fixtureA, B entityB, Fixture fixtureB);
 
	public abstract void postSolve(Contact contact, ContactImpulse impulse, thwack.model.Entity entityB, Fixture fixtureA, thwack.model.Entity entityA, Fixture fixtureB);
 
}