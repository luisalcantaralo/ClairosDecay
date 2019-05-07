package mx.itesm.decay.Screens.Maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
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
import mx.itesm.decay.Config.MapConverter;
import mx.itesm.decay.Decay;
import mx.itesm.decay.Generators.GenericScreen;

import mx.itesm.decay.Generators.PauseScene;
import mx.itesm.decay.Screens.GameStates;
import mx.itesm.decay.Screens.LoadingScreen;
import mx.itesm.decay.Screens.Menu.Home;
import mx.itesm.decay.Screens.Screens;

public class FirstLevel extends GenericScreen {

    private Decay game;
    private final AssetManager manager;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private Clairo clairo;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Texture background;
    private Texture healthBarC;
    private Texture healthBar;

    //HUD
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    // El HUD lo manejamos con una escena (opcional)
    private Stage sceneHUD;

    private Texture pauseButton;
    private GameStates state;
    private PauseScene pauseScene;


    public FirstLevel(Decay game){
        super(5);
        this.game = game;
        state = GameStates.PLAYING;
        manager = game.getAssetManager();
    }

    @Override
    public void show() {

        loadMap();
        setPhysics();
        clairo = new Clairo(world, 100,95);
        background = new Texture("backgrounds/cd-map-01-background.png");
        createHUD();
        Gdx.input.setInputProcessor(sceneHUD);
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void createHUD() {
        camaraHUD = new OrthographicCamera(GenericScreen.WIDTH,GenericScreen.WIDTH);
        camaraHUD.position.set(GenericScreen.WIDTH/2, GenericScreen.HEIGHT/2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(GenericScreen.WIDTH,GenericScreen.HEIGHT, camaraHUD);

        // HUD
        //Barra de Vida
        healthBarC = manager.get("Items/LifeBarContainer.png");
        healthBar = manager.get("Items/TimeBar.png");
        Image imgHealthBarC = new Image(healthBarC);
        Image imgHeathBar = new Image(healthBar);
        imgHealthBarC.setPosition(GenericScreen.WIDTH*0.05f - imgHealthBarC.getImageWidth(), GenericScreen.HEIGHT*0.9f - imgHealthBarC.getImageHeight());
        imgHeathBar.setPosition(GenericScreen.WIDTH*0.055f - imgHeathBar.getImageWidth(), GenericScreen.HEIGHT*0.91f - imgHeathBar.getImageHeight());
        //MOVEMENT BUTTONS
        Texture rightTexture= new Texture("UI/ButtonRight.png");
        TextureRegionDrawable trdRightButton= new TextureRegionDrawable(new TextureRegion(rightTexture));
        ImageButton rightButton= new ImageButton(trdRightButton);
        rightButton.setPosition(rightButton.getWidth()*1.5f+100,rightButton.getHeight()/2);


        Texture leftTexture= new Texture("UI/ButtonLeft.png");
        TextureRegionDrawable trdLeftButton= new TextureRegionDrawable(new TextureRegion(leftTexture));
        ImageButton leftButton= new ImageButton(trdLeftButton);
        leftButton.setPosition(leftButton.getWidth()-rightButton.getWidth()/2,leftButton.getHeight()/2);

        Texture jumpTexture= new Texture("UI/AButton.png");
        TextureRegionDrawable trdJumpButton= new TextureRegionDrawable(new TextureRegion(jumpTexture));
        ImageButton jumpButton= new ImageButton(trdJumpButton);
        jumpButton.setPosition(GenericScreen.WIDTH-jumpButton.getWidth()*2,jumpButton.getHeight()/2);

        // PAUSE
        pauseButton= new Texture("menu/cd-button-back.png");
        TextureRegionDrawable trdPauseButton = new TextureRegionDrawable(new TextureRegion(pauseButton));
        ImageButton pauseButtonImage = new ImageButton(trdPauseButton);
        pauseButtonImage.setPosition(GenericScreen.WIDTH - pauseButtonImage.getWidth()*2, GenericScreen.HEIGHT - pauseButtonImage.getHeight()*2);
        pauseButtonImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // response
                clairo.disableControls=true;
                state= GameStates.PAUSE;
                if (state==GameStates.PAUSE) {
                    // Activar escenaPausa y pasarle el control
                    pauseScene = new FirstLevel.PauseScene(vistaHUD, batch, game);
                    Gdx.input.setInputProcessor(pauseScene);
                }
            }
        });

        sceneHUD = new Stage(vistaHUD);
        Gdx.input.setInputProcessor(sceneHUD);
        sceneHUD.addActor(pauseButtonImage);
        sceneHUD.addActor(rightButton);
        sceneHUD.addActor(leftButton);
        sceneHUD.addActor(jumpButton);
        sceneHUD.addActor(imgHealthBarC);
        sceneHUD.addActor(imgHeathBar);
        createCollisionListener();
    }

    private void setPhysics() {
        Box2D.init();
        world = new World(new Vector2(0, -100.81f), true);


        MapConverter.createBodies(map, world);
        MapConverter.createStairs(map, world);
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

            if(state==GameStates.PLAYING){
                Gdx.gl.glClearColor(1,1,1,0);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                world.step(delta, 6,2);

                clairo.update(time);

                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                batch.draw(background,0,0, background.getWidth(), background.getHeight());
                batch.end();

                mapRenderer.setView(camera);
                mapRenderer.render();
                batch.begin();
                clairo.draw(batch);
                batch.end();
                b2dr.render(world, camera.combined);
                updateCamera();
                mapRenderer.setView(camera);
                mapRenderer.render();

                batch.begin();
                clairo.draw(batch);
                batch.end();
                b2dr.render(world, camera.combined);
                batch.setProjectionMatrix(camaraHUD.combined);
                sceneHUD.draw();
            }
        if(state==GameStates.PAUSE){
            pauseScene.draw();}
        updateCamera();
    }


    private void updateCamera() {
        float xCamera = clairo.getX();
        float yCamera = clairo.getY()+20;
        float tileSize = map.getProperties().get("tilewidth", Integer.class);
        float mapWidth = (map.getProperties().get("width", Integer.class) * tileSize) / SCALE;
        float mapHeight = (map.getProperties().get("height", Integer.class) * tileSize) / SCALE;

        System.out.println(mapWidth);
        System.out.println(mapHeight);
        System.out.println(background.getWidth());
        if(xCamera < SCALED_WIDTH/2){
            xCamera = SCALED_WIDTH/2;
        }else if(xCamera > mapWidth - SCALED_WIDTH/2){
            xCamera = mapWidth - SCALED_WIDTH/2;

        }
        if(yCamera < SCALED_HEIGHT/2){
            yCamera = SCALED_HEIGHT/2;
        }else if(yCamera > mapHeight - SCALED_WIDTH/2){
            yCamera = mapHeight - SCALED_WIDTH/2;

        }


        camera.position.x = xCamera;
        camera.position.y = yCamera;
        camera.update();
    }

    private void createCollisionListener() {
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if(fixtureB.getBody().getUserData().equals("clairo") && fixtureA.getBody().getUserData().equals("stair")){
                    Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
                    clairo.canClimb = true;
                }

            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if(fixtureB.getBody().getUserData().equals("clairo") && fixtureA.getBody().getUserData().equals("stair")){
                    Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
                    clairo.canClimb = false;
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }

        });
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

    private class PauseScene extends Stage {
        private final Decay game;

        public PauseScene(Viewport view, Batch batch, final Decay game) {
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
                    Gdx.input.setInputProcessor(new ProcesadorEntrada());
                    clairo.disableControls=false;
                }
            });
            Texture menuBtn= new Texture("menu/cd-about-title.png");
            TextureRegionDrawable trdMenu = new TextureRegionDrawable(new TextureRegion(menuBtn));
            ImageButton menuButton = new ImageButton(trdMenu);
            menuButton.setPosition(imgRectangle.getWidth()/2,imgRectangle.getHeight()*0.5f);
            menuButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    // response
                    game.setScreen(new LoadingScreen(game,Screens.HOME));
                }
            });
            this.addActor(menuButton);
            this.addActor(backButton);
        }
    }

    private class ProcesadorEntrada implements InputProcessor {


        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 v3 = new Vector3(screenX,screenY,0);
            camaraHUD.unproject(v3);
            // Left button
            if(v3.x >48 && v3.x<144 && v3.y >48 &&v3.y<144 ){
                Gdx.app.log("Izquierda" ,"direccion");
                clairo.setLeft();
            }
            // Right button
            else if(v3.x>244 && v3.x<339 && v3.y>48 && v3.y<144){
                Gdx.app.log("Derecha" ,"direccion");

                clairo.setRight();
                }
            else if (v3.x >1086 && v3.x< 1188 && v3.y>48 && v3.y<144 ){
                Gdx.app.log("Arriba" ,"direccion");
                clairo.setUpKey();
                }
            else if (v3.x >GenericScreen.WIDTH - 172 && v3.x <GenericScreen.WIDTH && v3.y >GenericScreen.HEIGHT - 172 && v3.y<GenericScreen.HEIGHT){
                // response
                clairo.disableControls=true;
                state= GameStates.PAUSE;
                if (state==GameStates.PAUSE) {
                    // Activar escenaPausa y pasarle el control
                    pauseScene = new FirstLevel.PauseScene(vistaHUD, batch, game);
                    Gdx.input.setInputProcessor(pauseScene);
            }}
            else{
                clairo.setDefault();

            }
            return false;
            }


        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            clairo.setDefault();
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }

}

