package thwack.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import thwack.model.Mob;
import thwack.model.Weapon;
import thwack.sound.SoundPlayer;

/**
 * @author Daniel Holderbaum
 */
public class MobVsWeaponHandler extends ContactHandler<Mob, Weapon> {

	@Override
	public void beginContact(Contact contact, Mob entityA, Fixture fixtureA, Weapon entityB, Fixture fixtureB) {
		if (entityB.isAttacking()) {
			entityA.tookDamage(entityB.getPosition());
		if(contact.isTouching()){
			//System.out.println((contact.getFixtureA().getBody().getPosition().x + contact.getFixtureB().getBody().getPosition().x) / 2 + ", " + (contact.getFixtureA().getBody().getPosition().y + contact.getFixtureB().getBody().getPosition().y) / 2);
			Mob.setLastContactx((contact.getFixtureA().getBody().getPosition().x + contact.getFixtureB().getBody().getPosition().x) / 2);
			Mob.setLastContacty((contact.getFixtureA().getBody().getPosition().y + contact.getFixtureB().getBody().getPosition().y) / 2);
		}
		}
		System.out.println(Mob.getLastContactx() + ", " + Mob.getLastContacty());
		SoundPlayer.SLAP.play();
	}

	@Override
	public void endContact(Contact contact, Mob entityA, Fixture fixtureA, Weapon entityB, Fixture fixtureB) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold, Mob entityA, Fixture fixtureA, Weapon entityB, Fixture fixtureB) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse, Mob entityA, Fixture fixtureA, Weapon entityB, Fixture fixtureB) {

	}

}
