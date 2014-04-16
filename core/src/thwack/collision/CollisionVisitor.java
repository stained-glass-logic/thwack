package thwack.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public interface CollisionVisitor {
	boolean visit(Rectangle rectangle);
	boolean visit(Circle circle);
	boolean collidesWith(CollisionVisitor visitor);
}
