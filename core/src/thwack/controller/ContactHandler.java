package thwack.controller;

import thwack.model.entity.Entity;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * This class will be called by the {@link ContactListener} and will
 * handle all collisions between two types of {@link Entity}s.
 * 
 * @author Daniel Holderbaum
 */
public abstract class ContactHandler<A extends Entity, B extends Entity> {
 
	public abstract void beginContact(Contact contact, A entityA, Fixture fixtureA, B entityB, Fixture fixtureB);
 
	public abstract void endContact(Contact contact, A entityA, Fixture fixtureA, B entityB, Fixture fixtureB);
 
	public abstract void preSolve(Contact contact, Manifold oldManifold, A entityA, Fixture fixtureA, B entityB, Fixture fixtureB);
 
	public abstract void postSolve(Contact contact, ContactImpulse impulse, A entityA, Fixture fixtureA, B entityB, Fixture fixtureB);
 
}