package mx.itesm.decay.Characters;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.audio.Music;

import mx.itesm.decay.Decay;


public class Enemy extends Sprite {

    // Clairo's State
    public enum State {
        SHOT, DEAD, PATROLLING, DRAW, SHOOTING, RUNNING
    }

    // Box2d
    private World world;
    private Body body;
    private BodyDef bdef = new BodyDef();


    FixtureDef fix;

    TextureRegion boxTexture;
    float startPositionX;
    float startPositionY;
    float enemySpeed = 5;
    float enemyRatioXN = 15;
    float enemyRatioXP = 15;

    public State currentState;
    public State previousState;

    // Textures
    private Texture idleText;


    // Animations
    private Animation<TextureRegion> enemyPatrolling;
    private Animation<TextureRegion> enemyRunning;
    private Animation<TextureRegion> enemyShooting;
    private Animation<TextureRegion> enemyShot;
    private Animation<TextureRegion> enemyDead;
    private Animation<TextureRegion> enemyDrawing;


    // Animation Details
    public float timer;
    public boolean isRunningRight;
    public boolean isShooting = false;

    // Movement


    //private final TestScreen screen;

    public Enemy(World world, float x, float y) {
        this.world = world;
        this.startPositionX = x;
        this.startPositionY = y;

        currentState = State.PATROLLING;
        previousState = State.RUNNING;
        isRunningRight = true;
        timer = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 16; i++)
            frames.add(new TextureRegion(new Texture("Characters/Enemy/Walking/Enemy_Walking.png"), i * 416, 0, 288, 288));
        enemyPatrolling = new Animation(0.1f, frames);

        frames.clear();

        for (int i = 0; i < 16; i++)
            frames.add(new TextureRegion(new Texture("Characters/Enemy/Shot/Shot.png"), i * 416, 0, 288, 288));
        enemyPatrolling = new Animation(0.1f, frames);

        frames.clear();

        for (int i = 0; i < 16; i++)
            frames.add(new TextureRegion(new Texture("Characters/Enemy/Dead/Dead.png"), i * 416, 0, 288, 288));
        enemyPatrolling = new Animation(0.1f, frames);

        frames.clear();

        for (int i = 0; i < 16; i++)
            frames.add(new TextureRegion(new Texture("Characters/Enemy/Draw/Enemy_IdleDraw.png"), i * 416, 0, 288, 288));
        enemyPatrolling = new Animation(0.1f, frames);

        frames.clear();


        for (int i = 0; i < 16; i++)
            frames.add(new TextureRegion(new Texture("Characters/Enemy/Walking/Shot.png"), i * 416, 0, 288, 288));
        enemyPatrolling = new Animation(0.1f, frames);

        frames.clear();


        setBounds(startPositionX, startPositionY, 25, 25);

        //boxTexture = new TextureRegion(new Texture("Turret/turret.png"), 0, 0, 150, 138);
        timer = 0;
        setBounds(startPositionX, startPositionY, 150 / 9, 138 / 9);
        defineBox(startPositionX, startPositionY);

        //defineClairo(startPositionX, startPositionY);

        // setRegion(new TextureRegion(new Texture("Characters/Detective/DetectiveRun.png")));


        //running = Gdx.audio.newMusic(Gdx.files.internal("SFX/Running.mp3"));
        //jumping = Gdx.audio.newMusic(Gdx.files.internal("SFX/Propulsor.mp3"));

    }




    public void update(float dt) {
        setPosition((body.getPosition().x) - getWidth() / 2, (body.getPosition().y) - getHeight() / 2);
        setRegion(getFrame(dt));

        if (body.getPosition().x <= startPositionX - enemyRatioXN) {
            body.applyLinearImpulse(new Vector2(enemySpeed, 0), body.getWorldCenter(), true);
        } else if (body.getPosition().x >= startPositionX + enemyRatioXP){
            body.applyLinearImpulse(new Vector2(-enemySpeed, 0), body.getWorldCenter(), true);
        }else{
            body.applyLinearImpulse(new Vector2(enemySpeed, 0), body.getWorldCenter(), true);
        }
    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;
        timer += dt;

        region = enemyPatrolling.getKeyFrame(timer, true);

        return region;

    }

    public void defineBox(float x, float y){

        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/2, getHeight()/2);
        fix = new FixtureDef();
        fix.shape = shape;
        fix.friction = 0;
        fix.density = 0.005f;
        Fixture fixture = body.createFixture(fix);
        body.setUserData("enemy");
    }

}
