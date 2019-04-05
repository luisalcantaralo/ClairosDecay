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

import mx.itesm.decay.Screens.TestScreen;


public class Clairo extends Sprite {

    // Clairo's State
    public enum State { IDLE, WALKING, RUNNING, DEAD, JUMPING, SHOOTING}
    public State currentState;
    public State previousState;

    // Box2d
    public World world;
    public Body body;

    // Animations
    private Animation<TextureRegion> clairoStand;
    private Animation<TextureRegion> clairoRun;
    private Animation<TextureRegion> clairoJump;
    private Animation<TextureRegion> clairoWalk;
    private Animation<TextureRegion> clairoShoot;


    // Animation Details
    public float timer;
    public boolean isRunningRight;
    public boolean disableControls = false;
    public boolean isShooting = false;

    private final TestScreen screen;

    public Clairo(TestScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.JUMPING;
        previousState = State.IDLE;
        isRunningRight = true;
        timer = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 13; i++)
            frames.add(new TextureRegion(new Texture("Characters/Detective/Idle/Detective_Idle.png"), i * 288, 0, 288, 288));
        clairoStand = new Animation(0.1f, frames);

        frames.clear();

        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(new Texture("Characters/Detective/Run/Detective_Run.png"), i * 426, 110, 426, 292));
        }
        clairoRun = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

         for(int i = 3; i < 13; i++)
             frames.add(new TextureRegion(new Texture("Characters/Detective/Jump/Detective_Jump.png"), i * 426, 40, 426, 362));
        clairoJump = new Animation(0.1f, frames);

        frames.clear();

         for(int i = 1; i < 10; i++)
             frames.add(new TextureRegion(new Texture("Characters/Detective/Shoot/Detective_IdleDraw.png"), i * 288, 50, 234, 268));
        clairoShoot = new Animation(0.1f, frames);

        setBounds(400,200,150, 150);

        defineClairo();

        setRegion(new TextureRegion(new Texture("Characters/DetectiveRun.png")));

    }

    public void update(float dt){
        updateMovement(dt);
        updateState();
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));

    }

    private void updateState() {
        if (body.getLinearVelocity().y != 0) {
            currentState = State.JUMPING;
        }
        else if (body.getLinearVelocity().x != 0) {
            currentState = State.RUNNING;
        }
        else if (body.getLinearVelocity().y == 0 && body.getLinearVelocity().x == 0 && !isShooting) {
            currentState = State.IDLE;
        }
    }

    private void updateMovement(float dt) {
        if(!disableControls){
            if (Gdx.input.isKeyPressed(Input.Keys.UP)){
                if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                    isRunningRight = true;
                    body.applyLinearImpulse(new Vector2(9999999f, 9999999f), body.getWorldCenter(), true);
                }
                else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                    isRunningRight = false;
                    body.applyLinearImpulse(new Vector2(-9999999f, 9999999f), body.getWorldCenter(), true);
                }
                else{
                    body.applyLinearImpulse(new Vector2(0, 9999999f), body.getWorldCenter(), true);
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ){
                isRunningRight = true;

                body.applyLinearImpulse(new Vector2(9999999f, 0), body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ){
                isRunningRight = false;
                body.applyLinearImpulse(new Vector2(-9999999f, 0), body.getWorldCenter(), true);
            }
        }
    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;
        timer += dt;
        switch (currentState){
            case IDLE:
                region = clairoStand.getKeyFrame(timer, true);
                break;
            case WALKING:
                region = clairoWalk.getKeyFrame(timer, true);
                break;
            case RUNNING:
                region = clairoRun.getKeyFrame(timer, true);
                break;
            case JUMPING:
                region = clairoJump.getKeyFrame(timer, true);
                break;
            case SHOOTING:
                region = clairoShoot.getKeyFrame(timer, false);
                break;
            default:
                region = clairoRun.getKeyFrame(timer, true);
                break;
        }

        if(isRunningRight && region.isFlipX()){
            region.flip(true,false);
        }
        if(!isRunningRight && !region.isFlipX()){
            region.flip(true,false);
        }
        return region;
    }

    // Box2d initialization
    public void defineClairo() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/2, getHeight()/2);
        FixtureDef fix = new FixtureDef();
        fix.shape = shape;
        Fixture fixture = body.createFixture(fix);
    }

    public void draw(SpriteBatch batch){
        super.draw(batch);
    }
}
