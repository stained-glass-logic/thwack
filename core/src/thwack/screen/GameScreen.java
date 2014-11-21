package thwack.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * User: rnentjes
 * Date: 19-11-14
 * Time: 12:25
 */
public class GameScreen extends ScreenAdapter {

    private TiledMap tiledMap;
    private Box2DDebugRenderer debugRenderer;
    private World world = new World(new Vector2(0, 0), false);
    private OrthographicCamera camera;

    private float unitWidth, unitHeight;

    public GameScreen(TiledMap map) {
        this.tiledMap = map;

        camera = new OrthographicCamera();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
        this.unitWidth = 1024f / 32f;
        this.unitHeight = width / 1024f * (float)height;

        camera.setToOrtho(false, unitWidth, unitHeight);
    }
}
