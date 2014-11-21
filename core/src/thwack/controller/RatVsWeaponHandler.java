package thwack.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import thwack.model.Rat;
import thwack.model.Weapon;
import thwack.sound.SoundPlayer;

/**
 * @author Daniel Holderbaum
 */
public class RatVsWeaponHandler extends ContactHandler<Rat, Weapon> {

	@Override
	public void beginContact(Contact contact, Rat entityA, Fixture fixtureA, Weapon entityB, Fixture fixtureB) {
		if (entityB.isAttacking()) {
			entityA.died();
			SoundPlayer.SLAP.play();
		}
	}

	@Override
	public void endContact(Contact contact, Rat entityA, Fixture fixtureA, Weapon entityB, Fixture fixtureB) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold, Rat entityA, Fixture fixtureA, Weapon entityB, Fixture fixtureB) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse, Rat entityA, Fixture fixtureA, Weapon entityB, Fixture fixtureB) {

	}

}
