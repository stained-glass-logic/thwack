package thwack.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import thwack.model.Mob;
import thwack.model.Wall;

/**
 * @author Daniel Holderbaum
 */
public class MobVsWallHandler extends ContactHandler<Mob, Wall> {
	private Vector2 velocity = new Vector2(0,0);
	@Override
	public void beginContact(Contact contact, Mob mob, Fixture fixtureA, Wall entityB, Fixture fixtureB) {

			mob.setVelocity(velocity);
			mob.setStateTime(0f);
			mob.mobMoveLogic(0f);
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