package thwack.model.entities.player;

import thwack.Constants;
import thwack.model.entities.weapons.Sword;
import thwack.model.entities.weapons.Weapon;
import thwack.model.entity.Damageable;
import thwack.model.entity.Entity;
import thwack.model.entity.EntityStats;
import thwack.model.entity.Movable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Movable implements Damageable {

	public State state = State.STANDING;
	public int health;
	public float damageStateTime = 0f;
	public boolean isActive = true;
	
	private Weapon weapon;
	
	public enum State {
		STANDING,
		WALKING,
		ATTACKING,
		BORED,
		RUNNING,
		TAKINGDAMAGE,
		DEFENDING,
	}

	
	public Player(World world)
	{
		this.world = world;
		this.stats = new EntityStats();
		
		BodyDef playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyType.DynamicBody;
		playerBodyDef.position.set(5,20);
		this.entityBody = world.createBody(playerBodyDef);
		entityBody.setUserData(this);
		entityBody.setFixedRotation(true);
		entityBody.setLinearDamping(9f);

		PolygonShape playerBodyShape = new PolygonShape();
		playerBodyShape.setAsBox(.5f, .5f);

		FixtureDef playerFixtureDef = new FixtureDef();
		playerFixtureDef.density= 1.0f;
		playerFixtureDef.shape = playerBodyShape;

		this.entityBody.createFixture(playerFixtureDef);
		playerFixtureDef.shape.dispose();
		
		setHealth(3);
		weapon = new Sword(world, this);
	}
	
	@Override
	public void update(float deltaTime) {
		Vector2 direction = new Vector2(0.0f, 0.0f);
		
		if(this.state != State.ATTACKING){
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				direction.add(0, 1);
				velocity.y += 1;
				setState(State.WALKING);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				velocity.y -= 1;
				setState(State.WALKING);
				direction.add(0, -1);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				velocity.x -= 1;
				setState(State.WALKING);
				direction.add(-1, 0);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				velocity.x += 1;
				setState(State.WALKING);
				direction.add(1, 0);
			}
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.J) && this.state != State.ATTACKING){
			direction.set(0,0);
			setState(State.ATTACKING);
		}
		
		move(direction);
		setVelocity(velocity);

	}
	
	@Override
	public void setVelocity(Vector2 velocity)
	{
		Vector2 targetVelocity = new Vector2(velocity).nor().scl(.75f * 15);
		Vector2 impulse = new Vector2(targetVelocity);
		this.entityBody.setLinearVelocity(impulse);
	}

	@Override
	public boolean active() {
		return isAlive();
	}

	@Override
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public void setStateTime(float stateTime) {
		this.stateTime += stateTime;
	}

	@Override
	public void dispose() {
		world.destroyBody(entityBody);
	}

	@Override
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public void applyHit(int damage, Entity weapon) {
		this.health -= damage;
		
	}

	@Override
	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void increaseDamageStateTime(float deltaTime) {
		this.damageStateTime += deltaTime;
	}

	@Override
	public float getDamageStateTime() {
		return damageStateTime;
	}
	
	public void setState(State state) {
		if (this.state != state) {
			// reset the state time every time state changes
			stateTime = 0.0f;

			if (state == State.ATTACKING) {
				 weapon.beginAttack();
			}
		}
		this.state = state;
	}

	public void move(Vector2 x) {
		if (this.state == State.ATTACKING) {
			this.velocity.set(0, 0);
		} else {
			if (x.isZero(0.01f)) {
				this.velocity.set(0, 0);
				this.state = State.STANDING;
			} else {
				this.velocity.set(x.nor());
				this.state = State.WALKING;

				float epsilon = 0.5f;
				if (x.isCollinear(Constants.UP, epsilon)) {
					this.direction = Direction.UP;
				} else if (x.isCollinear(Constants.DOWN, epsilon)) {
					this.direction = Direction.DOWN;
				} else if (x.isCollinear(Constants.LEFT, epsilon)) {
					this.direction = Direction.LEFT;
				} else if (x.isCollinear(Constants.RIGHT, epsilon)) {
					this.direction = Direction.RIGHT;
				}
			}
		}
	}
	
	
	@Override
	public void setDamageStateTime(float time)
	{
		damageStateTime = time;
	}
	
}
