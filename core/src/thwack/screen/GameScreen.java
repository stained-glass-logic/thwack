package thwack.screen;

import thwack.Constants;
import thwack.Global;
import thwack.controller.MyContactListener;
import thwack.model.entities.mobs.Aimer;
import thwack.model.entities.mobs.Rat;
import thwack.model.entities.player.Player;
import thwack.model.entities.projectiles.Projectile;
import thwack.model.entities.worldobjects.Wall;
import thwack.model.entity.Entity;
import thwack.model.entity.Stateable;
import thwack.model.entity.Stateable.EntityState;
import thwack.model.entity.Updateable;
import thwack.sound.MusicPlayer;
import thwack.sound.SoundPlayer;
import thwack.view.AimerRenderer;
import thwack.view.MinimapRenderer;
import thwack.view.PlayerRenderer;
import thwack.view.RatRenderer;

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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

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
    private Array<Aimer> aimers = new Array<Aimer>();
    public static Array<Projectile> projectiles = new Array<Projectile>();
    
    private Player player;

    private PlayerRenderer playerRenderer;

    // my attempt to add a bodydef
    private BodyDef[] wallsBodyDef = new BodyDef[4];
    private Body[] wallsBody = new Body[4];

    private int width = 4 / 2;
    private int height = 29 / 2;

    private FixtureDef[] fixtureDef = new FixtureDef[4];

    private RatRenderer ratRenderer;
    private AimerRenderer aimerRenderer;
    private Wall wall; // dummy to resolve wall collisions

    //private float unitWidth, unitHeight;
    
    private MinimapRenderer minimapRenderer;
    SpriteBatch guiBatch;
    OrthographicCamera cameraMinimap;


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
        guiBatch = new SpriteBatch();
        cameraMinimap = new OrthographicCamera(w,h);
        cameraMinimap.zoom = 16.0f;       

        tiledMap = new TmxMapLoader().load("DemoMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 16f,
                Global.batch);

        int mapWidth = tiledMap.getProperties().get("width", Integer.class);
        //int mapHeight = tiledMap.getProperties().get("height", Integer.class);

        Global.font = new BitmapFont();

        Global.shapeRenderer = new ShapeRenderer();

        wall = new Wall();

        //the walls
        createWalls(mapWidth, width, height);

        player = new Player(world);

        Vector2 ratPos = new Vector2(6f, 25f);
        Vector2 ratSize = new Vector2(.5f, .5f);
        for (int count = 0; count < 0; count++) {
            Rat rat = new Rat(world, ratPos, ratSize);
            rats.add(rat);
            updateables.add(rat);
        }
        
        for (int count = 0; count <4; count++) {
            Aimer aimer = new Aimer(world, ratPos, ratSize);
            aimers.add(aimer);
            updateables.add(aimer);
        }

        // and the player object code
        playerRenderer = new PlayerRenderer(Global.batch, Global.shapeRenderer);
        ratRenderer = new RatRenderer(Global.batch, Global.shapeRenderer);
        aimerRenderer = new AimerRenderer(Global.batch, Global.shapeRenderer);
        
        updateables.add(player);
        
        //and the minimap
        minimapRenderer = new MinimapRenderer(guiBatch, Global.shapeRenderer, aimers, player, tiledMap);
       
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

        float x = player.getBody().getPosition().x;

        float y = player.getBody().getPosition().y - (11 / 32);

       x = Math.max(x, 10);
       y = (float) Math.max(y, 9.8);

       x = Math.min(x, 46);
       y = (float) Math.min(y, 30.41);

        camera.position.set(x, y, 0);

        camera.update();
        tiledMapRenderer.render();

        Global.batch.begin();
        playerRenderer.render(player);

        for (int index = 0; index < rats.size; index++) {
            if (rats.get(index).isAlive()) {
                ratRenderer.render(rats.get(index));
            }
        }
        
        for (int index = 0; index < aimers.size; index++)
        {
        	if (aimers.get(index).isAlive())
        	{
        		aimerRenderer.render(aimers.get(index));
        	}
        }

        Global.batch.end();
        
        //minimap
        cameraMinimap.update();
        guiBatch.setProjectionMatrix(cameraMinimap.combined);
        guiBatch.begin();
        minimapRenderer.render();
        guiBatch.end();

        float deltaTime = Gdx.graphics.getDeltaTime();
        
        debugRenderer.render(world, camera.combined);

        Array<Body> bodies2 = new Array<Body>();
		world.getBodies(bodies2);
		
		
		for(Body body : bodies2)
		{
			if(body.getUserData() instanceof Updateable)
			{
				Updateable u = (Updateable) body.getUserData();
				if(u.active())
					u.update(deltaTime);
			}
		}
        
        
        world.step(deltaTime, 8, 4);
        
       
        
        //Only destroy stuff after world.step();
        if(!world.isLocked())
		{
        	Array<Body> bodies = new Array<Body>();
    		world.getBodies(bodies);
    		
    		
			for(Body body : bodies)
			{
//				if(body.getUserData() instanceof Updateable)
//				{
//					Updateable u = (Updateable) body.getUserData();
//					if(u.active())
//						u.update(deltaTime);
//				}
				
				if(body.getUserData() instanceof Stateable)
				{
					Stateable e = (Stateable) body.getUserData();
					if(e.getPublicState() == EntityState.DESTROY)
					{
						if(body.getUserData() instanceof Entity)
						{
							Entity e2 = (Entity) body.getUserData();
							e2.dispose();
						}	
					}
				}
			}
		}
       
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
