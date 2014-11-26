package thwack.model;

import thwack.model.Entity.DamageState;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;

public class Rat extends Mob implements Updateable, Disposable {

	private World world;

	public Body ratBody;
	private BodyDef ratBodyDef;
	private FixtureDef ratFixtureDef;
	private Direction direction = Direction.UP;
	private State state = State.BORED;

	public Vector2 velocity = new Vector2(0, 0).limit(getSpeed());
    private Vector2 lastPosition = new Vector2(0,0);

	private Boolean stateSwitched = false;

	public Rat(World world, Vector2 pos, Vector2 size) {
		super();
		this.world = world;
		this.setSpeed(10f);
		this.ratBodyDef = new BodyDef();
		this.ratBodyDef.type = BodyType.DynamicBody;
		this.ratBodyDef.position.set(pos);
		ratBody = world.createBody(ratBodyDef);
		ratBody.setUserData(this);
		ratBody.setFixedRotation(true);

		PolygonShape ratBodyShape = new PolygonShape();
		ratBodyShape.setAsBox(size.x, size.y);

		ratFixtureDef = new FixtureDef();
		ratFixtureDef.density = .5f;
		ratFixtureDef.shape = ratBodyShape;
		ratBody.createFixture(ratFixtureDef);
		ratFixtureDef.shape.dispose();
		
		hp = 3;
	}

	@Override
	public void dispose() {
		world.destroyBody(ratBody);
	}

	@Override
	public void update(float deltaTime) {
		ratLogic(3f);
		increaseStateTime(deltaTime);
	}

	private void ratLogic(float time) {

		//change state randomly every time seconds
		if (getStateTime() < time) {
			//if the state doesn't need to change yet, then
			//just try to move
			move(velocity);
			applyImpulse();

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
					applyImpulse();
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



	}

	public void applyImpulse() {
		Vector2 targetVelocity = new Vector2(velocity).nor().scl(getSpeed());
		Vector2 impulse = new Vector2(targetVelocity);
		ratBody.setLinearVelocity(impulse);
	}

	public boolean isMoving() {
        Vector2 vel = lastPosition.cpy().sub(ratBody.getPosition());

        return vel.len2() > 0.001f;
	}

	public void move(Vector2 velocity) {
		if (this.state == State.BORED) {
			this.velocity.set(0, 0);
		} else {
			this.velocity.set(velocity.nor());
			//this.state = State.RUNNING;

			float pi = (float)Math.PI;
			float angle = velocity.getAngleRad();
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

			if (velocity.isZero(0.01f) && state == State.RUNNING) {
				this.velocity.set(0, 0);
				this.state = State.STANDING;
			}
		}
	}
	
	public void tookDamage(Vector2 fromPos) {
		setDamageState(DamageState.PHYSICAL);
		setDamageStateTime(0);
		//maybe we should do knockback here? For now, stop the rat.
		//sorry to override - didn't want the rat to stop moving. makes them too easy to kill --radish
		velocity.set(0, 0);
		hp = hp - 1;
		if (hp < 1) died();
	}

	public void setDamageState(DamageState state) {
		if (this.damageState != state){
			damageStateTime = 0.0f;
		}
		this.damageState = state;
	}

	public void setState(State state) {
		if (this.state != state) {
			// reset the state time every time state changes
			stateTime = 0.0f;
		}
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void setPosition(float f, float g) {
		this.position.set(f, g);
	}

	public Direction getDirection() {
		return direction;
	}

	public Vector2 getPosition() {
		return position;
	}

    public void archivePosition() {
        lastPosition = ratBody.getPosition().cpy();
    }

	public boolean active() {
		return isAlive();
	}

	public DamageState getDamageState() {
		return damageState;
	}
}
