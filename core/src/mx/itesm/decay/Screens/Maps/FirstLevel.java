package mx.itesm.decay.Screens.Maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;


import mx.itesm.decay.Characters.Clairo;
import mx.itesm.decay.Config.MapConverter;
import mx.itesm.decay.Decay;
import mx.itesm.decay.Generators.GenericScreen;

public class FirstLevel extends GenericScreen {

    private Decay game;
    private final AssetManager manager;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private Clairo clairo;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Texture background;


    public FirstLevel(Decay game){
        super(5);
        this.game = game;
        manager = game.getAssetManager();
    }

    @Override
    public void show() {

        loadMap();
        setPhysics();
        clairo = new Clairo(world, 100,300);
        background = new Texture("backgrounds/cd-simple-background.png");
    }

    private void setPhysics() {
        Box2D.init();
        world = new World(new Vector2(0, -100.81f), true);


        MapConverter.createBodies(map, world);
        b2dr = new Box2DDebugRenderer();
    }



    private void loadMap() {
        AssetManager assetManager = new AssetManager();
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(
                        new InternalFileHandleResolver()));
        manager.load("maps/cd-map-02.tmx", TiledMap.class);
        manager.finishLoading(); // blocks app

        map = manager.get("maps/cd-map-02.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f/5f);
    }


    @Override
    public void render(float delta) {
        float time = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(delta, 6,2);

        clairo.update(time);

        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        batch.draw(background,clairo.getX()-200,0, background.getWidth()/2, background.getHeight()/2);
        batch.end();

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.begin();
        clairo.draw(batch);
        batch.end();

        b2dr.render(world, camera.combined);
        updateCamera();

    }


    private void updateCamera() {
        float xCamara = clairo.getX();
        float yCamera = clairo.getY();

        camera.position.x = xCamara;
        camera.position.y = yCamera;
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
        batch.dispose();
        map.dispose();
        mapRenderer.dispose();
    }
}

