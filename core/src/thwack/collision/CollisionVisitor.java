package thwack.collision;

import com.badlogic.gdx.math.Rectangle;

public interface CollisionVisitor {
	
	boolean visit(Rectangle rectangle);
	
	boolean collidesWith(CollisionVisitor visitor);
	
}
