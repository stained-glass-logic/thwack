package thwack.controller;

import thwack.model.entities.mobs.Mob;
import thwack.model.entities.worldobjects.Wall;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * @author Daniel Holderbaum
 */
public class MobVsWallHandler extends ContactHandler<Mob, Wall> {
	private Vector2 velocity = new Vector2(0,0);
	@Override
	public void beginContact(Contact contact, Mob mob, Fixture fixtureA, Wall entityB, Fixture fixtureB) {

			mob.setVelocity(velocity);
			mob.setStateTime(0f);
			mob.moveLogic(0f);
	}

	@Override
	public void endContact(Contact contact, Mob entityA, Fixture fixtureA, Wall entityB, Fixture fixtureB) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold, Mob entityA, Fixture fixtureA, Wall entityB, Fixture fixtureB) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse, Mob entityA, Fixture fixtureA, Wall entityB, Fixture fixtureB) {

	}
}