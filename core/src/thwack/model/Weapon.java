package thwack.model;

/**
 * User: rnentjes
 * Date: 19-11-14
 * Time: 12:42
 */
public abstract class Weapon extends Entity {

    protected boolean attacking;
    protected float attackStart;

    public abstract void beginAttack();

    public boolean isAttacking() {
        return attacking;
    }
}
