package mx.itesm.decay.Screens;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.decay.Generators.GenericScreen;
import mx.itesm.decay.Characters.Clairo;
import mx.itesm.decay.Characters.Enemy;
import mx.itesm.decay.Characters.FatGuy;
import mx.itesm.decay.Characters.Turret;
import mx.itesm.decay.Config.MapConverter;
import mx.itesm.decay.Decay;
import mx.itesm.decay.Display.Text;
import mx.itesm.decay.Generators.GenericScreen;

public class TestScreen extends GenericScreen {

    private Decay game;

    private Texture texture;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Texture background;
    private Texture graffiti;
    private Texture healthBar;
    private Array<Turret> arrTurret;
    Clairo clairo;
    Turret turret;
    FatGuy fatGuy;
    Enemy enemy;
    Turret turret1;
    Turret turret2;
    Turret turret3;
    Turret turret4;


    Text text;

    // Box2d
    BodyDef bodyDef;
    Body body;

    // Map
    private TiledMap map;
    private OrthoCachedTiledMapRenderer mapRenderer;

    // Conversation
    boolean talkBegin = false;
    boolean talkEnemyOne = false;
    float talkTimer = 0;
    float talkEnemy = 0;
    int minutes=3;
    float timer=60;


    public TestScreen(Decay game){
        super();
        this.game = game;
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
        turret1 = new Turret(this,1500,600);
        turret2= new Turret(this,2240,600);
        turret3= new Turret(this, 2500,600);
        turret4= new Turret(this, 2760,600);
        fatGuy = new FatGuy(this);
        enemy = new Enemy(this, 3000, 300);
        text = new Text();
        background = new Texture("menu/cd-menu-background.png");
        graffiti= new Texture("misc/cd-graffiti-ros.png");
        healthBar= new Texture("misc/cd-life-bar.png");
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
        if(clairo.getX() >= 2500) talkEnemyOne = true;

        updateCamera();
        clairo.update(time);
        turret1.update(time);
        turret2.update(time);
        turret3.update(time);
        turret4.update(time);
        fatGuy.update(time);
        enemy.update(time);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if(clairo.getX() > WIDTH/2) batch.draw(background, clairo.getX()-WIDTH/2, 0);
        else  batch.draw(background, 0, 0);
        batch.end();
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();
        batch.draw(graffiti,2400,300);
        if(clairo.getX() > WIDTH/2) batch.draw(healthBar, clairo.getX()-WIDTH/2, HEIGHT-healthBar.getHeight());
        else  batch.draw(healthBar, 0, HEIGHT-healthBar.getHeight());
        turret1.draw(batch);
        turret2.draw(batch);
        turret3.draw(batch);
        turret4.draw(batch);
        fatGuy.draw(batch);
        clairo.draw(batch);
        enemy.draw(batch);
        if(talkBegin) talk(delta);
        if(talkEnemyOne) talkOne(delta);
        if(clairo.getX() > WIDTH/2) text.showText(batch,countDown(delta), healthBar.getWidth()/2+clairo.getX()-WIDTH/2, HEIGHT-healthBar.getHeight()/3);
        else  text.showText(batch,countDown(delta), healthBar.getWidth()/2, HEIGHT-healthBar.getHeight()/3);
        batch.end();

        world.step(1/60f, 6,2);

    }

    private String countDown(float delta) {
        if((timer-delta)<0){
            minutes--;
            timer=60;
        }

        timer-=delta;
        return minutes+":"+(int)timer;
    }

    private void talkOne(float dt) {
        talkEnemy += dt;
        clairo.disableControls = true;
        if(talkEnemy < 1){
            text.showText(batch, "Stop right there!", clairo.getX()+40, clairo.getY()+200);
        }

        if(talkEnemy > 1 && talkEnemy < 6){
            enemy.isRunning = true;
            clairo.disableControls = true;
            clairo.body.applyLinearImpulse(new Vector2(9999999f, 0), enemy.body.getWorldCenter(), true);
            enemy.body.applyLinearImpulse(new Vector2(9999999f, 0), enemy.body.getWorldCenter(), true);
        }
        if(talkEnemy > 2){
            clairo.disableControls = false;
            enemy.setPosition(3300,enemy.getY());
        }
        if(talkEnemy > 6 && talkEnemy < 7){
            enemy.isRunning = false;
            enemy.surrender = true;
            enemy.timer = 0;
        }
        if(talkEnemy > 6){
            clairo.isShooting = true;
            clairo.currentState = Clairo.State.SHOOTING;
        }

        if(talkEnemy > 6 && talkEnemy < 10){
            text.showText(batch, "Don't move or I'll shoot!", clairo.getX()+100, clairo.getY()+200);
        }
        if(talkEnemy > 10 && talkEnemy < 13){
            text.showText(batch, "People must know the truth.", enemy.getX()+300, enemy.getY()+200);
        }
        if(talkEnemy > 11.5){
            game.setScreen(new BlackScreen("TO BE CONTINUED", 4, WIDTH/2, HEIGHT/2));
        }

    }

    private void talk(float dt) {
        talkTimer += dt;
        clairo.disableControls = true;

        if(talkTimer < 3){
            text.showText(batch, "Hey, have you seen a bug around here?", 950, clairo.getY()+200);
        }

        if(talkTimer > 3 && talkTimer < 6){
            text.showText(batch, "Yeah, she just ran passed me,\nif you hurry you might catch her up.", 1300, 300);

        }
        if(talkTimer > 6 && talkTimer < 10){
            text.showText(batch, "These bugs are getting out of hand,\nyou ought to control the situation before panic\novertakes the city.", 1300, 350);

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
