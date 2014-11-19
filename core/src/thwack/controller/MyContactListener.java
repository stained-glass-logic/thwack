package thwack.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import thwack.model.*;

/**
 * This class will determine the correct {@link ContactHandler} for a given
 * {@link Contact} callback and delegate it and the extracted {@link Entity}s to
 * the handler. If there was no handler specified, then this listener won't do
 * anything. The standard Box2D collision behaviour will still take place.
 *
 * @author Daniel Holderbaum
 */
public class MyContactListener implements ContactListener {

	private Array<ContactHandlerInformation> handlerInformation = new Array<ContactHandlerInformation>();

	public MyContactListener() {
		handlerInformation.add(new ContactHandlerInformation(Player.class, Pickup.class, new PlayerVsPickupHandler()));
		handlerInformation.add(new ContactHandlerInformation(Rat.class, Wall.class, new RatVsWallHandler()));
		handlerInformation.add(new ContactHandlerInformation(Rat.class, Weapon.class, new RatVsWeaponHandler()));
		// more registered handlers...
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		Body bodyA = fixtureA.getBody();
		Body bodyB = fixtureB.getBody();

		Entity entityA = (Entity) bodyA.getUserData();
		Entity entityB = (Entity) bodyB.getUserData();

		if (entityA != null && entityB != null) {
			Class<? extends Entity> classA = entityA.getClass();
			Class<? extends Entity> classB = entityB.getClass();

			ResolvedHandlerInformation handlerInformation = resolveContactHandlerInformation(classA, classB);

			if (handlerInformation == null) {
				return;
			}

			if (!handlerInformation.reverse) {
				handlerInformation.contactHandler.beginContact(contact, entityA, fixtureA, entityB, fixtureB);
			} else {
				handlerInformation.contactHandler.beginContact(contact, entityB, fixtureB, entityA, fixtureA);
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		if (fixtureA == null || fixtureB == null) {
			return;
		}

		Body bodyA = fixtureA.getBody();
		Body bodyB = contact.getFixtureB().getBody();

		Entity entityA = (Entity) bodyA.getUserData();
		Entity entityB = (Entity) bodyB.getUserData();

		if (entityA != null && entityB != null) {
			Class<? extends Entity> classA = entityA.getClass();
			Class<? extends Entity> classB = entityB.getClass();

			ResolvedHandlerInformation handlerInformation = resolveContactHandlerInformation(classA, classB);

			if (handlerInformation == null) {
				return;
			}

			if (!handlerInformation.reverse) {
				handlerInformation.contactHandler.endContact(contact, entityA, fixtureA, entityB, fixtureB);
			} else {
				handlerInformation.contactHandler.endContact(contact, entityB, fixtureB, entityA, fixtureA);
			}
		}

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		Body bodyA = fixtureA.getBody();
		Body bodyB = contact.getFixtureB().getBody();

		Entity entityA = (Entity) bodyA.getUserData();
		Entity entityB = (Entity) bodyB.getUserData();

		if (entityA != null && entityB != null) {
			Class<? extends Entity> classA = entityA.getClass();
			Class<? extends Entity> classB = entityB.getClass();

			ResolvedHandlerInformation handlerInformation = resolveContactHandlerInformation(classA, classB);

			if (handlerInformation == null) {
				return;
			}

			if (!handlerInformation.reverse) {
				handlerInformation.contactHandler.preSolve(contact, oldManifold, entityA, fixtureA, entityB, fixtureB);
			} else {
				handlerInformation.contactHandler.preSolve(contact, oldManifold, entityB, fixtureB, entityA, fixtureA);
			}
		}
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		Body bodyA = fixtureA.getBody();
		Body bodyB = contact.getFixtureB().getBody();

		Entity entityA = (Entity) bodyA.getUserData();
		Entity entityB = (Entity) bodyB.getUserData();

		if (entityA != null && entityB != null) {
			Class<? extends Entity> classA = entityA.getClass();
			Class<? extends Entity> classB = entityB.getClass();

			ResolvedHandlerInformation handlerInformation = resolveContactHandlerInformation(classA, classB);

			if (handlerInformation == null) {
				return;
			}

			if (!handlerInformation.reverse) {
				handlerInformation.contactHandler.postSolve(contact, impulse, entityA, fixtureA, entityB, fixtureB);
			} else {
				handlerInformation.contactHandler.postSolve(contact, impulse, entityB, fixtureB, entityA, fixtureA);
			}
		}
	}

	private ResolvedHandlerInformation resolveContactHandlerInformation(Class<? extends Entity> classA, Class<? extends Entity> classB) {
		for (ContactHandlerInformation information : handlerInformation) {
			if (ClassReflection.isAssignableFrom(information.classA, classA) && ClassReflection.isAssignableFrom(information.classB, classB)) {
				ResolvedHandlerInformation resolvedInformation = new ResolvedHandlerInformation();
				resolvedInformation.contactHandler = information.contactHandler;
				resolvedInformation.reverse = false;
				return resolvedInformation;
			}
			if (ClassReflection.isAssignableFrom(information.classA, classB) && ClassReflection.isAssignableFrom(information.classB, classA)) {
				ResolvedHandlerInformation resolvedInformation = new ResolvedHandlerInformation();
				resolvedInformation.contactHandler = information.contactHandler;
				resolvedInformation.reverse = true;
				return resolvedInformation;
			}
		}

		return null;
	}

}