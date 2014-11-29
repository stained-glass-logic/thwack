package thwack.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import thwack.Constants;
import thwack.Global;
import thwack.controller.MyContactListener;
import thwack.model.Player;
import thwack.model.Rat;
import thwack.model.Updateable;
import thwack.model.Wall;
import thwack.sound.MusicPlayer;
import thwack.sound.SoundPlayer;
import thwack.view.PlayerRenderer;
import thwack.view.RatRenderer;

/**
 * User: rnentjes
 * Date: 19-11-14
 * Time: 12:25
 */
public class GameScreen extends ScreenAdapter {

    private static int NORTH 	= 0;
    private static int EAST 	= 1;
    private static int SOUTH 	= 2;
    private static int WEST 	= 3;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    private Box2DDebugRenderer debugRenderer;
    private World world = new World(new Vector2(0, 0), false);
    private OrthographicCamera camera;

    private Array<Updateable> updateables = new Array<Updateable>();
    private Array<Rat> rats = new Array<Rat>();

    private Player player;

    private PlayerRenderer playerRenderer;

    // my attempt to add a bodydef
    private BodyDef[] wallsBodyDef = new BodyDef[4];
    private Body[] wallsBody = new Body[4];

    private int width = 4 / 2;
    private int height = 29 / 2;

    private FixtureDef[] fixtureDef = new FixtureDef[4];

    private RatRenderer ratRenderer;
    private Wall wall; // dummy to resolve wall collisions

    private float unitWidth, unitHeight;

    public GameScreen(TiledMap map) {
        world = new World(new Vector2(0, 0), false);
        debugRenderer = new Box2DDebugRenderer();

        world.setContactListener(new MyContactListener());

        float w = Gdx.graphics.getWidth() / 32;
        float h = Gdx.graphics.getHeight() / 32;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        // new code for collision goes here. to be replaced by a handler class
        // later

        Global.batch = new SpriteBatch();

        tiledMap = new TmxMapLoader().load("DemoMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 16f,
                Global.batch);

        int mapWidth = tiledMap.getProperties().get("width", Integer.class);
        int mapHeight = tiledMap.getProperties().get("height", Integer.class);

        Global.font = new BitmapFont();

        Global.shapeRenderer = new ShapeRenderer();

        wall = new Wall();

        //the walls
        createWalls(mapWidth, width, height);

        player = new Player(world);

        Vector2 ratPos = new Vector2(6f, 20f);
        Vector2 ratSize = new Vector2(.5f, .5f);
        for (int count = 0; count < 20; count++) {
            Rat rat = new Rat(world, ratPos, ratSize);
            rats.add(rat);
            updateables.add(rat);
        }

        // and the player object code
        playerRenderer = new PlayerRenderer(Global.batch, Global.shapeRenderer);
        ratRenderer = new RatRenderer(Global.batch, Global.shapeRenderer);

        updateables.add(player);
    }

    @Override
    public void show() {
        MusicPlayer.GAME_RAGTIME.load();
        if (Constants.AUDIO_ON) {
            MusicPlayer.GAME_RAGTIME.play();
        }
        SoundPlayer.SLAP.load();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        MusicPlayer.GAME_RAGTIME.dispose();
        SoundPlayer.SLAP.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(camera);

        float x = player.playerBody.getPosition().x;

        float y = player.playerBody.getPosition().y - (11 / 32);

       x = Math.max(x, 10);
     y = (float) Math.max(y, 9.8);

       x = Math.min(x, 46);
       y = (float) Math.min(y, 30.41);

        camera.position.set(x, y, 0);
        System.out.println(camera.position.toString());

        camera.update();
        tiledMapRenderer.render();

        Global.batch.begin();
        playerRenderer.render(player);

        for (int index = 0; index < rats.size; index++) {
            if (rats.get(index).isAlive()) {
                ratRenderer.render(rats.get(index));
            } else {
                rats.get(index).dispose();
                rats.removeIndex(index);
            }
        }

        Global.batch.end();

        float deltaTime = Gdx.graphics.getDeltaTime();

        for (Updateable updateable : updateables) {
            if (updateable.active()) {
                updateable.update(deltaTime);
            }
        }

        debugRenderer.render(world, camera.combined);

        world.step(deltaTime, 8, 4);
    }


    private void createWalls(int mapWidth, float ww, float hh) {
        PolygonShape [] bodyShape = new PolygonShape[4];

        for (int index = 0; index < 4 ; index++) {
            wallsBodyDef[index] = new BodyDef();
            wallsBodyDef[index].type = BodyDef.BodyType.StaticBody;
            bodyShape[index] = new PolygonShape();

        }

        wallsBodyDef[WEST].position.set(2f, 20f);
        bodyShape[WEST].setAsBox(ww, hh);

        wallsBodyDef[EAST].position.set(54f, 20f);
        bodyShape[EAST].setAsBox(ww, hh);

        wallsBodyDef[SOUTH].position.set(28f, 4f);
        bodyShape[SOUTH].setAsBox(mapWidth, ww);

        wallsBodyDef[NORTH].position.set(28f, 36f);
        bodyShape[NORTH].setAsBox(mapWidth, ww);

        for (int index = 0; index < 4 ; index++) {
            wallsBody[index] = world.createBody(wallsBodyDef[index]);
            wallsBody[index].setUserData(wall);

            fixtureDef[index] = new FixtureDef();
            fixtureDef[index].shape = bodyShape[index];
            wallsBody[index].createFixture(fixtureDef[index]);
            bodyShape[index].dispose();
        }
    }

}
