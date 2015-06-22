package thwack.model.entities.mobs;

import thwack.Global;
import thwack.model.entities.mobs.Mob.State;
import thwack.model.entity.Entity;
import thwack.model.entity.Lifebar;
import thwack.model.entity.Movable.Direction;
import thwack.model.entity.Stateable.EntityState;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class SawRat extends Mob {
	
	public SawRat(World world, Vector2 pos, Vector2 size) {
		
		super(world, pos, size);
		
		BodyDef ratBodyDef = new BodyDef();
		ratBodyDef.type = BodyType.DynamicBody;
		ratBodyDef.position.set(pos);
		entityBody = world.createBody(ratBodyDef);
		entityBody.setUserData(this);
		entityBody.setFixedRotation(true);

		PolygonShape ratBodyShape = new PolygonShape();
		ratBodyShape.setAsBox(size.x, size.y);

		FixtureDef ratFixtureDef = new FixtureDef();
		ratFixtureDef.density = .5f;
		ratFixtureDef.shape = ratBodyShape;
		entityBody.createFixture(ratFixtureDef);
		ratFixtureDef.shape.dispose();
		
		

		lifebar = new Lifebar();
		setHealth(4);

	}
	
	@Override
	public void update(float deltaTime) {
		this.increaseDamageStateTime(deltaTime);
		this.increaseStateTime(deltaTime);
		moveLogic(3f);
		lifebar.updateTime(deltaTime);
	}
	
	@Override
	public void moveLogic(float deltaTime)
	{
		if(getStateTime() < deltaTime)
		{
			setVelocity(velocity);
		}
		else
		{
			//otherwise, pick a number between 1 to 5 to
			//determine next state.  Running is particularly likely.
			int nextState = MathUtils.random(1,5);
			//pick a random direction too
			int nextDir = MathUtils.random(1,4);
			if (nextDir == 1) this.direction = Direction.UP;
			else if (nextDir == 2) this.direction = Direction.LEFT;
			else if (nextDir == 3) this.direction = Direction.RIGHT;
			else if (nextDir == 4) this.direction = Direction.DOWN;
			
			switch (nextState)
			{
				case 1:
					//running
					setState(State.RUNNING);
					setStateTime(0);
					velocity.set(MathUtils.random(-1, 1), MathUtils.random( -1,  1));
					move(velocity);
					break;
				case 2:
					//bored
					setState(State.BORED);
					setStateTime(0);
					velocity.set(0,0);
					break;
				case 3:
					//stand still
					setState(State.STANDING);
					setStateTime(0);
					velocity.set(0,0);
					break;
				case 4:
					//bored2
					setState(State.BORED2);
					setStateTime(0);
					velocity.set(0,0);
					break;
				case 5:
					//running
					setState(State.RUNNING);
					setStateTime(0);
					velocity.set(MathUtils.random(-1, 1), MathUtils.random( -1,  1));
					break;
			}
		}
		
		setVelocity(velocity);
	}

	
	@Override
	public void dispose() {
		world.destroyBody(entityBody);		
	}

	@Override
	public void setHealth(int health) {
		this.maxHealth = health;
		this.currentHealth = health;
		lifebar.updateImage(currentHealth, maxHealth);
	}

	@Override
	public void applyHit(int damage, Entity attacker) {
		currentHealth -= damage;
		lifebar.updateImage(currentHealth, maxHealth);
		lifebar.makeVisible();
		if(currentHealth < 1) {
			setPublicState(EntityState.DESTROY);
			attacker.stats.killCount++;
			Global.DebugOutLine(attacker + " kills: " + attacker.stats.killCount);
		}
	}

	@Override
	public boolean isAlive() {
		return (currentHealth > 0);
	}

	@Override
	public int getHealth() {
		return currentHealth;
	}

	
	public void move(Vector2 velocity) {
		if (this.state == State.BORED) {
			this.velocity.set(0, 0);
		} else {
			this.velocity.set(velocity.nor());

			float pi = (float)Math.PI;
			float angle = velocity.angleRad();
			while(angle < 0) {
				angle += 2*pi;
			}
			angle = angle % (2*pi);

			if (angle > pi * 0.25 && angle < pi * 0.75) {
				this.direction = Direction.UP;
			} else if (angle > pi * 1.25 && angle < pi * 1.75) {
				this.direction = Direction.DOWN;
			} else if (angle > pi * 0.75 && angle < pi * 1.25) {
				this.direction = Direction.LEFT;
			} else {
				this.direction = Direction.RIGHT;
			}

			if (velocity.isZero() && state == State.RUNNING) {
				this.velocity.set(0, 0);
				setVelocity(velocity);
				this.state = State.STANDING;
			}
		}
	}


}
