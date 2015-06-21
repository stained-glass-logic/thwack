package thwack;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * User: rnentjes
 * Date: 21-11-14
 * Time: 19:56
 */
public class Global {

    public static SpriteBatch   batch;
    public static BitmapFont    font;
    public static ShapeRenderer shapeRenderer;
    
    public static boolean verbose = true;		//whether or not to do debug output
    
    public static void DebugOut(String s)
    {
    	if (verbose == true) System.out.print(s);
    }
    
    public static void DebugOutLine(String s)
    {
    	if (verbose == true) System.out.println(s);
    }

}
