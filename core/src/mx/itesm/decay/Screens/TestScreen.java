package mx.itesm.decay.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.decay.Characters.Clairo;
import mx.itesm.decay.Characters.FatGuy;
import mx.itesm.decay.Characters.Turret;
import mx.itesm.decay.Config.MapConverter;
import mx.itesm.decay.Display.Text;

public class TestScreen extends GenericScreen{

    private Texture texture;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Texture background;
    private Texture graffiti;
    Clairo clairo;
    Turret turret;
    FatGuy fatGuy;


    Text text;

    // Box2d
    BodyDef bodyDef;
    Body body;

    // Map
    private TiledMap map;
    private OrthoCachedTiledMapRenderer mapRenderer;

    // Conversation
    boolean talkBegin = false;
    float talkTimer = 0;

    public TestScreen(){
        super();
    }
    @Override
    public void show() {
        camera = new OrthographicCamera(WIDTH , HEIGHT );
        camera.position.set(WIDTH/2 , HEIGHT/2, 0);
        camera.update();

        view = new StretchViewport(WIDTH , HEIGHT, camera);
        batch = new SpriteBatch();

        // Box 2d
        world = new World(new Vector2(0,-100000f), true);
        b2dr = new Box2DDebugRenderer();
        configureBodies();
        clairo = new Clairo(this);
        turret = new Turret(this, 150, 150);
        fatGuy = new FatGuy(this);
        text = new Text();
        background = new Texture("menu/cd-menu-background.png");
        graffiti= new Texture("misc/cd-graffiti-ros.png");
        loadMap();
    }

    private void loadMap() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("maps/Level1.tmx", TiledMap.class);
        manager.finishLoading();

        map = manager.get("maps/Level1.tmx");

        mapRenderer = new OrthoCachedTiledMapRenderer(map);
        MapConverter.crearCuerpos(map, world);
    }
    private void configureBodies() {


        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 10));

        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth, 100.0f);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();

    }


    @Override
    public void render(float delta) {
        float time = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(clairo.getX() >= 883) talkBegin = true;
        updateCamera();
        clairo.update(time);
        turret.update(time);
        fatGuy.update(time);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if(clairo.getX() > WIDTH/2) batch.draw(background, clairo.getX()-WIDTH/2, 0);
        else  batch.draw(background, 0, 0);


        batch.end();
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();
        batch.draw(graffiti,2400,300);
        turret.draw(batch);
        fatGuy.draw(batch);
        clairo.draw(batch);
        if(talkBegin) talk(delta);
        batch.end();

        world.step(1/60f, 6,2);

    }

    private void talk(float dt) {
        talkTimer += dt;
        clairo.disableControls = true;

        if(talkTimer < 3){
            text.showText(batch, "Hey, have you seen a bug around here?", 950, clairo.getY()+200);
        }

        if(talkTimer > 3 && talkTimer < 6){
            text.showText(batch, "Yeah, she just ran passed me, \nif you hurry you might catch her up.", 1300, 300);

        }
        if(talkTimer > 6 && talkTimer < 10){
            text.showText(batch, "These bugs are getting out of hand, \nyou ought to control the situation before panic\n overtakes the city.", 1300, 350);

        }
        if(talkTimer > 10 && talkTimer < 12){
            text.showText(batch, "We're doing the best we can sir.", 950, 300);
        }
        if(talkTimer > 12){
            talkBegin = false;
            clairo.disableControls = false;
        }
    }

    private void updateCamera() {
        float xCamara = clairo.getX();
        float dx = 0;

        float mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);

        if(clairo.getX() < WIDTH/2){
            xCamara = WIDTH/2;
        }
        else if(clairo.getX() > mapWidth-WIDTH/2){
            xCamara = mapWidth-WIDTH/2;
        }
        camera.position.x = xCamara;
        camera.update();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public World getWorld() {
        return world;
    }




}
