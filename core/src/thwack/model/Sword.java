package thwack.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * User: rnentjes
 * Date: 19-11-14
 * Time: 12:35
 */
public class Sword extends Weapon implements Updateable {

    private static float HALF_PI = (float) Math.PI / 2f;
    private static float MAX_SIZE = 1f;

    private Body swordBody;
    private Player player;

    private float startAngle = 0;
    private float currentAngle = 0;
    private float size = 1;
    private Vector2 offset = new Vector2();

    public Sword(World world, Player player) {
        this.player = player;

        BodyDef swordDef = new BodyDef();
        swordDef.type = BodyDef.BodyType.KinematicBody;

        swordBody = world.createBody(swordDef);
        swordBody.setUserData(this);
        swordBody.setFixedRotation(true);

        PolygonShape swordShape = new PolygonShape();
        swordShape.setAsBox(1,.2f, new Vector2(1f, 0.1f), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = .5f;
        fixtureDef.isSensor = true;
        fixtureDef.shape = swordShape;
        swordBody.createFixture(fixtureDef);
        //fixtureDef.shape.dispose();
        swordShape.dispose();
    }

    @Override
    public void beginAttack() {
        attacking = true;
        attackStart = 0f;

        offset.set(0,0);
        startAngle = HALF_PI; //- HALF_PI / 2f;
        switch(player.getDirection()) {
            case UP:
                offset.set(0.65f, 1.75f);
                break;
            case LEFT:
                startAngle += HALF_PI;
                offset.set(-0.4f, 0f);
                break;
            case DOWN:
                startAngle += 2 * HALF_PI;
                offset.set(0.65f, -0.5f);
                break;
            case RIGHT:
                startAngle += 3 * HALF_PI;
                offset.set(1.7f, 0f);
                break;
        }

        currentAngle = startAngle;
        // get location from player and slash away
        swordBody.setTransform(offset.cpy().add(player.position), startAngle);
        size = 0.25f;
    }

    public void update(float deltaTime) {
        if (attacking) {
            attackStart += deltaTime;
            //currentAngle += deltaTime * HALF_PI * 4f;
            swordBody.setTransform(offset.cpy().add(player.position), currentAngle);
            PolygonShape shape = (PolygonShape)swordBody.getFixtureList().get(0).getShape();

           	//tweaked this to get the speed feeling right --radish
            size += 5 * deltaTime;

            shape.setAsBox(size, 0.5f);

            if (size > MAX_SIZE) {
                attacking = false;
            }
        }

        swordBody.setActive(attacking);
    }

    public void render(SpriteBatch batch) {

    }

    @Override
    public boolean active() {
        return attacking;
    }

}
