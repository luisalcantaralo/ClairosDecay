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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.decay.Characters.Clairo;
import mx.itesm.decay.Config.MapConverter;
import mx.itesm.decay.Decay;

public class TestLevel1Screen extends GenericScreen{

    // Mapa
    private TiledMap map;
    private OrthoCachedTiledMapRenderer mapRenderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Texture background;

    // Box2d
    BodyDef bodyDef;
    Body body;


    public TestLevel1Screen(Decay game){

    }

    @Override
    public void show() {
        camera = new OrthographicCamera(WIDTH , HEIGHT );
        camera.position.set(WIDTH/2 , HEIGHT/2, 0);
        camera.update();
        view = new StretchViewport(WIDTH , HEIGHT, camera);
        batch = new SpriteBatch();
        background = new Texture("fondo.jpg");
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
    }
    /*private void configureBodies() {


        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 10));

        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth, 100.0f);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();

    }*/


    @Override
    public void render(float delta) {
        float time = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        mapRenderer.setView(camera);
        mapRenderer.render();
        //world.step(1/60f, 6,2);
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
