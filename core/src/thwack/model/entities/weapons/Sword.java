package thwack.model.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import thwack.model.entities.player.Player;

public class Sword extends Weapon {

	private static float HALF_PI = (float) Math.PI / 2f;
    private static float MAX_SIZE = 1f;
    
	private float startAngle = 0;
    private float currentAngle = 0;
    private float size = 1;
    private Vector2 offset = new Vector2();
    
    
	public Sword(World world, Player player)
	{
		this.world = world;
		this.weilder = player;
		this.damage = 1;
		
		BodyDef swordDef = new BodyDef();
        swordDef.type = BodyDef.BodyType.KinematicBody;

        entityBody = world.createBody(swordDef);
        entityBody.setUserData(this);
        entityBody.setFixedRotation(true);

        PolygonShape swordShape = new PolygonShape();
        swordShape.setAsBox(1,.4f, new Vector2(1f, 0.1f), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = .5f;
        fixtureDef.isSensor = true;
        fixtureDef.shape = swordShape;
        entityBody.createFixture(fixtureDef);
        swordShape.dispose();
	}
	
	@Override
	public void update(float deltaTime) {
		if (attacking) {
            attackStart += deltaTime;
            entityBody.setTransform(offset.cpy().add(weilder.getBody().getPosition()), currentAngle);
            PolygonShape shape = (PolygonShape)entityBody.getFixtureList().get(0).getShape();

           	//tweaked this to get the speed feeling right --radish
            size += 5 * deltaTime;

            shape.setAsBox(size, 0.5f);

            if (size >= MAX_SIZE) {
                attacking = false;
            }
        }

		entityBody.setActive(attacking);
	}

	@Override
	public boolean active() {
		return attacking;
	}

	@Override
	public void setActive(boolean isActive) {
		this.attacking = isActive;
	}

	@Override
	public void dispose() {
		world.destroyBody(entityBody);
	}

	@Override
	public void beginAttack() {
		attacking = true;
        attackStart = 0f;

        offset.set(0,0);
        startAngle = HALF_PI; //- HALF_PI / 2f;
        switch(((Player) this.weilder).direction) {
            case UP:
                offset.set(0f, 1.0f);
                break;
            case LEFT:
                startAngle += HALF_PI;
                offset.set(-1.0f, 0f);
                break;
            case DOWN:
                startAngle += 2 * HALF_PI;
                offset.set(0.0f, -1.0f);
                break;
            case RIGHT:
                startAngle += 3 * HALF_PI;
                offset.set(1.0f, 0f);
                break;
        }

        currentAngle = startAngle;
        // get location from player and slash away
        entityBody.setTransform(offset.cpy().add(weilder.getBody().getPosition()), startAngle);
        size = 0.25f;
	}

}
