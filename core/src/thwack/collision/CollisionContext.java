package thwack.collision;

import thwack.model.Block;

import com.badlogic.gdx.utils.Array;

public class CollisionContext {

	public static final String COLLISION = "COLLISION";
	
	private Array<CollisionVisitor> objects = new Array<CollisionVisitor>();

	/**
	 * TODO This method, or an overloaded method, could do a bit of optimization if necessary, such as
	 * dividing collision objects into quadrants.
	 * 
	 * @return
	 */
	public Array<CollisionVisitor> getCollisionCandidates() {
		return objects;
	}
	
	public void add(CollisionVisitor... visitors) {
		objects.addAll(visitors);
	}

	public void addAll(Array<Block> blocks) {
		objects.addAll(blocks);
	}
	
	public void remove(CollisionVisitor visitor) {
		objects.removeValue(visitor, true);
	}
	
}
