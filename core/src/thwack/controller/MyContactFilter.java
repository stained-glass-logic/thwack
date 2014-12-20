package thwack.controller;

import thwack.model.entities.player.Player;
import thwack.model.entity.Entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.reflect.ClassReflection;

/**
 * This {@link ContactFilter} will already prefilter all {@link Contact}s. We
 * assume that all {@link Body}s of the given {@link Fixture}s have an
 * {@link Entity} as their user data and can be checked for necessary
 * collisions. The contact listener will not have to perform any
 * checks anymore after it and can always assume a valid collision.
 * 
 * @author Daniel Holderbaum
 */
public class MyContactFilter implements ContactFilter {
 
	private final ObjectMap<Class<? extends Entity>, Array<Class<? extends Entity>>> INFOS = new ObjectMap<Class<? extends Entity>, Array<Class<? extends Entity>>>();
 
	public MyContactFilter() {
		// player collisions
		Array<Class<? extends Entity>> playerList = new Array<Class<? extends Entity>>();
		playerList.add(Pickup.class);
		INFOS.put(Player.class, playerList);
 
		// other lists with entity collision pairs
	}
 
	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		Entity entityA = (Entity) fixtureA.getBody().getUserData();
		Entity entityB = (Entity) fixtureB.getBody().getUserData();
 
		if (entityA == null || entityB == null) {
			// we do not know what to do in this case -> no collision by default
			return false;
		}
 
		Class<? extends Entity> classA = entityA.getClass();
		Class<? extends Entity> classB = entityB.getClass();
 
		return resolve(classA, classB);
	}
 
	private boolean resolve(Class<? extends Entity> classA, Class<? extends Entity> classB) {
		for (Entry<Class<? extends Entity>, Array<Class<? extends Entity>>> entry : INFOS.entries()) {
			if (ClassReflection.isAssignableFrom(entry.key, classA)) {
				if (entry.value.contains(classB, true)) {
					return true;
				}
			}
 
			if (ClassReflection.isAssignableFrom(entry.key, classB)) {
				if (entry.value.contains(classA, true)) {
					return true;
				}
			}
		}
 
		return false;
	}
 
}