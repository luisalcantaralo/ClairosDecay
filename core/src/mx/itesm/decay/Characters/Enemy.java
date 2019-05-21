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

    public enum State {
        SHOT, DEAD, PATROLLING, DRAW, SHOOTING, RUNNING
    }

    // Box2d
    private World world;
    private Body body;
    private BodyDef bdef = new BodyDef();
    private float timerBullet;


    FixtureDef fix;

    TextureRegion boxTexture;
    float startPositionX;
    float startPositionY;
    float enemySpeed = 5;
    float enemyRatioXP = 10;

    public State currentState;
    public State previousState;

    // Textures
    private Texture idleText;


    // Animations
    private Animation<TextureRegion> enemyPatrolling;
    private Animation<TextureRegion> enemyRunning;
    private Animation<TextureRegion> enemyPushing;


    // Animation Details
    public float timer;
    public boolean isRunningRight;
    public boolean isLeft;
    public boolean isTouching = false;

    // Movement

    public Enemy(World world, float x, float y) {
        this.world = world;
        this.startPositionX = x;
        this.startPositionY = y;

        currentState = State.PATROLLING;
        previousState = State.RUNNING;
        isLeft = true;
        isRunningRight = true;
        timer = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 16; i++)
            frames.add(new TextureRegion(new Texture("Characters/Enemy/Walking/Enemy_Walking.png"), i * 426+120, 80, 426-220, 332));
        enemyPatrolling = new Animation(0.1f, frames);


        frames.clear();


        for (int i = 0; i < 11; i++)
            frames.add(new TextureRegion(new Texture("Characters/Enemy/Pushing/Enemy Push.png"), i * 348, 0, 348-20, 402+40));
        enemyPushing= new Animation(0.1f, frames);

        frames.clear();



        timer = 0;
        setBounds(startPositionX, startPositionY, 170/11, 300 / 11);
        defineBox(startPositionX, startPositionY);


    }


    public void update(float dt) {
        setPosition((body.getPosition().x) - getWidth() / 2, (body.getPosition().y) - getHeight() / 2);
        setRegion(getFrame(dt));

        if (currentState == State.PATROLLING) {
            if (isRunningRight) {

                if(body.getPosition().x < startPositionX + enemyRatioXP){
                    body.setLinearVelocity(new Vector2(enemySpeed, 0));
                }

                if (body.getPosition().x >= (startPositionX + enemyRatioXP)){
                    isRunningRight=false;
                }


            }else{

                if(body.getPosition().x > startPositionX){
                    body.setLinearVelocity(new Vector2(-enemySpeed, 0));
                }


                if (body.getPosition().x <= (startPositionX - enemyRatioXP)){
                    isRunningRight=true;
                }
            }
        } else  if (currentState == State.SHOOTING) {

            System.out.println(currentState);
            if (isLeft) {
                isRunningRight = false;
<<<<<<< HEAD
                //body.setLinearVelocity(new Vector2(-5*enemySpeed, 0));

            }else{
                isRunningRight = true;
                //body.setLinearVelocity(new Vector2(5*enemySpeed, 0));
=======
                //body.setLinearVelocity(new Vector2(-2*enemySpeed, 0));

            }else{
                isRunningRight = true;
                //body.setLinearVelocity(new Vector2(2*enemySpeed, 0));
>>>>>>> 25a81d203723439ea8a4b27c40b1f096eb6ddaea

            }
        }

    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;
        timer += dt;

        switch (currentState){
            case SHOOTING:
                region = enemyPushing.getKeyFrame(timer, true);
                break;
            case PATROLLING:
                region = enemyPatrolling.getKeyFrame(timer, true);
                break;
            default:
                region = enemyPatrolling.getKeyFrame(timer, true);

        }

        if(isRunningRight && region.isFlipX()){
            region.flip(true,false);
        }

        if(!isRunningRight && !region.isFlipX()){
            region.flip(true,false);
        }

        return region;

    }

    public void defineBox(float x, float y){

        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/6, getHeight()/2);
        fix = new FixtureDef();
        fix.shape = shape;
        fix.friction = 0;
        Fixture fixture = body.createFixture(fix);
        body.setUserData("enemy");
    }


    public Bullet shoot(boolean izquierda, int speed){
        Bullet b = new Bullet(body.getPosition().x+5,body.getPosition().y, izquierda, speed);
        return b;
    }

    public float getTimerBullet() {
        return timerBullet;
    }

    public void setTimerBullet(float timerBullet) {
        this.timerBullet = timerBullet;
    }

}