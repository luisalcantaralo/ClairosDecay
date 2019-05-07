package mx.itesm.decay.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.decay.Characters.Clairo;
import mx.itesm.decay.Decay;
import mx.itesm.decay.Generators.GenericScreen;

public class TestLevel1Screen extends GenericScreen {

    // Mapa
    private TiledMap map;
    private OrthoCachedTiledMapRenderer mapRenderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Texture background;
    private Decay game;

    // Box2d
    BodyDef bodyDef;
    Body body;

    Clairo clairo;

    //HUD
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    // El HUD lo manejamos con una escena (opcional)
    private Stage sceneHUD;
    GameStates state = GameStates.PLAYING;
    PauseScene pauseScene;




    public TestLevel1Screen(Decay game){
        this.game = game;
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
        createHUD();
        Gdx.input.setInputProcessor(sceneHUD);
    }

    private void createHUD() {
        camaraHUD = new OrthographicCamera(GenericScreen.WIDTH,GenericScreen.WIDTH);
        camaraHUD.position.set(GenericScreen.WIDTH/2, GenericScreen.HEIGHT/2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(GenericScreen.WIDTH,GenericScreen.HEIGHT, camaraHUD);

        // HUD
        Texture pauseButton = new Texture("menu/cd-button-back.png");
        TextureRegionDrawable trdPauseButton = new TextureRegionDrawable(new TextureRegion(pauseButton));
        ImageButton pauseButtonImage = new ImageButton(trdPauseButton);
        pauseButtonImage.setPosition(GenericScreen.WIDTH/6 - pauseButtonImage.getWidth()*2, GenericScreen.HEIGHT/6 - pauseButtonImage.getHeight()*2);
        pauseButtonImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // response
                clairo.disableControls=true;
                state= GameStates.PAUSE;
                if (state==GameStates.PAUSE) {
                    // Activar escenaPausa y pasarle el control
                    pauseScene = new PauseScene(vistaHUD, batch, game);
                    Gdx.input.setInputProcessor(pauseScene);
                }
            }
        });

        sceneHUD = new Stage(vistaHUD);
        Gdx.input.setInputProcessor(sceneHUD);
        sceneHUD.addActor(pauseButtonImage);
    }


    private void loadMap() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("maps/cd-map-01.tmx", TiledMap.class);
        manager.finishLoading();

        map = manager.get("maps/cd-map-01.tmx");

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

        batch.setProjectionMatrix(camaraHUD.combined);
        sceneHUD.draw();
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

    public class PauseScene extends Stage {
        private final Decay game;

        public PauseScene(Viewport view, Batch batch, Decay game) {
            super(view, batch);
            this.game=game;
            Pixmap pixmap= new Pixmap((int)(GenericScreen.WIDTH*0.6),(int)(GenericScreen.HEIGHT*0.7f), Pixmap.Format.RGBA8888);
            pixmap.setColor(0.1f,0.1f,0.1f,0.5f);
            pixmap.fillRectangle(0,0,pixmap.getWidth(),pixmap.getHeight());
            Texture textureRectangle= new Texture(pixmap);
            pixmap.dispose();
            Image imgRectangle= new Image(textureRectangle);
            imgRectangle.setPosition(0.2f*GenericScreen.WIDTH,0.16f*GenericScreen.HEIGHT);
            this.addActor(imgRectangle);

            final Decay game2= game;
            Texture backBtn= new Texture("menu/cd-button-back.png");
            TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(backBtn));
            ImageButton backButton = new ImageButton(trdBack);
            backButton.setPosition(GenericScreen.WIDTH/2-backBtn.getWidth()/2,GenericScreen.HEIGHT*0.2f);
            backButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    // response
                    state=GameStates.PLAYING;
                    pauseScene.dispose();
                    Gdx.input.setInputProcessor(sceneHUD);
                    clairo.disableControls=false;
                }
            });
            this.addActor(backButton);
        }
    }




}

