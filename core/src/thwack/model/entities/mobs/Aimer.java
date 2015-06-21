package thwack.model.entities.mobs;

import thwack.Global;
import thwack.model.entity.Lifebar;
import thwack.model.entities.player.Player;
import thwack.model.entities.projectiles.FireBall;
import thwack.model.entities.projectiles.FireOrbital;
import thwack.model.entity.Entity;
import thwack.model.entity.Stateable.EntityState;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Aimer extends Mob {
	
	
	private Body target;

	private float defenseTimer = 0;
	
	public Aimer(World world, Vector2 pos, Vector2 size)
	{
		super(world, pos, size);
		
		BodyDef ratBodyDef = new BodyDef();
		ratBodyDef.type = BodyType.DynamicBody;
		ratBodyDef.position.set(new Vector2(pos));
		entityBody = world.createBody(ratBodyDef);
		entityBody.setUserData(this);
		entityBody.setFixedRotation(true);

		PolygonShape ratBodyShape = new PolygonShape();
		ratBodyShape.setAsBox(.5f, .5f);

		FixtureDef ratFixtureDef = new FixtureDef();
		ratFixtureDef.density = .5f;
		ratFixtureDef.shape = ratBodyShape;
		entityBody.createFixture(ratFixtureDef);
		ratFixtureDef.shape.dispose();

		lifebar = new Lifebar();
		setHealth(3);
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
		
		//TODO: Possibly make the player a static object in the GameScreen.java to simplify this and not have to search for it...
		if(target == null)
		{
		//gets all bodies in the world
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		
		//Finds target (only runs once)

			for(Body body : bodies)
			{
				if(body.getUserData() instanceof Player)
				{
					target = body;
					break;
				}
			}
		}
		
	
		float distanceFromTarget = entityBody.getPosition().dst(target.getPosition());

		if (getStateTime() < deltaTime) {

			if(distanceFromTarget < 4 && state != State.DEFENDING && defenseTimer > 1000)
			{
				//cast orbitals to defend the monster
				setState(State.DEFENDING);
				if(!world.isLocked())
				{
					if(this.entityBody.getJointList().size == 0)
					{
						int deg = 0;
						for(int i = 0; i < 5; i++)
						{
							new FireOrbital(world, this, deg);
							deg += 72;
						}
					}
					
				}
				
				defenseTimer = 0;
				
			}
			
			
			if(distanceFromTarget < 7)
			{
				//stop chasing target
				setState(State.STANDING);
				velocity.set(0,0);
				setVelocity(velocity);
			}
			else
			{
				setState(State.RUNNING);
				this.setSpeed(3f);
				velocity.set(target.getPosition().x - entityBody.getPosition().x, target.getPosition().y - entityBody.getPosition().y).nor().scl(Math.min(entityBody.getPosition().dst(target.getPosition().x, target.getPosition().y), 1.5f));
				setVelocity(velocity);
			}
		}
		else
		{
			if(!world.isLocked())
			{
				FireBall fb = new FireBall(world, this);
				fb.shootToward(target.getPosition().x, target.getPosition().y);
				setStateTime(0);
			}
		}
		
		defenseTimer += deltaTime;
		
		

		move(velocity);


	}
	
	@Override
	public void setVelocity(Vector2 velocity)
	{
		this.entityBody.setLinearVelocity(velocity);
	}

	@Override
	public boolean active()
	{
		return EntityState.ACTIVE == getPublicState();
	}
	
	@Override
	public void dispose() {
		world.destroyBody(entityBody);		
	}

	@Override
	public void setHealth(int health) {
		this.currentHealth = health;
		this.maxHealth = health;
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
