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

import mx.itesm.decay.Decay;
import mx.itesm.decay.Screens.TestScreen;


public class Clairo extends Sprite {

    // Clairo's State
    public enum State { IDLE, WALKING, RUNNING, CLIMBING, JUMPING, FALLING, SHOOTING}
    public State currentState;
    public State previousState;

    // Box2d
    public World world;
    public Body body;
    public PolygonShape shape;
    FixtureDef fix;

    // Textures
    private Texture idleText;


    // Animations
    private Animation<TextureRegion> clairoStand;
    private Animation<TextureRegion> clairoRun;
    private Animation<TextureRegion> clairoJump;
    private Animation<TextureRegion> clairoClimb;
    private Animation<TextureRegion> clairoShoot;


    // Animation Details
    public float timer;
    public boolean isRunningRight;
    public boolean disableControls = false;
    public boolean isShooting = false;
    public boolean canClimb = false;

    //private final TestScreen screen;

    public Clairo(World world, float startPositionX, float startPositionY) {
        this.world = world;
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

        for(int i = 0; i < 6; i++)
            frames.add(new TextureRegion(new Texture("Characters/Detective/Detective_Climb.png"), i * 284, 80, 284, 178));
        clairoClimb = new Animation(0.1f, frames);

        frames.clear();

         for(int i = 1; i < 10; i++)
             frames.add(new TextureRegion(new Texture("Characters/Detective/Shoot/Detective_IdleDraw.png"), i * 288, 50, 234, 268));
        clairoShoot = new Animation(0.1f, frames);

        setBounds(startPositionX,startPositionY,25, 25);

        defineClairo(startPositionX, startPositionY);

        setRegion(new TextureRegion(new Texture("Characters/Detective/DetectiveRun.png")));

    }

    public void update(float dt){
        updateState(dt);
        updateMovement();
        setPosition((body.getPosition().x)-getWidth()/2, (body.getPosition().y)-getHeight()/2);
        setRegion(getFrame(dt));

    }

    private void updateState(float dt) {

        if (body.getLinearVelocity().y > 0 && dt < 0.5 && !canClimb) {
            currentState = State.JUMPING;
        }
        else if (body.getLinearVelocity().y > 0 && dt < 0.5 && canClimb) {
            currentState = State.CLIMBING;
        }
        else if (body.getLinearVelocity().y < 0 || (body.getLinearVelocity().y > 0 && dt > 0.5) ) {
            currentState = State.FALLING;
        }
        else if (body.getLinearVelocity().x != 0) {
            currentState = State.RUNNING;
        }
        else if (body.getLinearVelocity().y == 0 && body.getLinearVelocity().x == 0 && !isShooting) {
            currentState = State.IDLE;
        }
    }

    private void updateMovement() {

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && currentState == State.CLIMBING){
            body.setLinearVelocity(new Vector2(0, 70f));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && currentState == State.IDLE){
            body.applyLinearImpulse(new Vector2(0, 70f), body.getWorldCenter(), true);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && currentState != State.JUMPING && currentState != State.FALLING){
            isRunningRight = true;

            body.applyLinearImpulse(new Vector2(0, 200f), body.getWorldCenter(), true);
        }


        if(currentState == State.RUNNING){
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ){
                isRunningRight = true;

                body.applyLinearImpulse(new Vector2(10f, 0), body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ){
                isRunningRight = false;
                body.applyLinearImpulse(new Vector2(-10f, 0), body.getWorldCenter(), true);
            }
        }
        else if(currentState == State.JUMPING){
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ){
                isRunningRight = true;

                body.applyLinearImpulse(new Vector2(10f, 0), body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ){
                isRunningRight = false;
                body.applyLinearImpulse(new Vector2(-10f, 0), body.getWorldCenter(), true);
            }
        }

        else if(currentState == State.FALLING){

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ){
                isRunningRight = true;

                body.applyLinearImpulse(new Vector2(10f, -15f), body.getWorldCenter(), true);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ){
                isRunningRight = false;
                body.applyLinearImpulse(new Vector2(-10f, -15f), body.getWorldCenter(), true);
            }
            else{
                body.applyLinearImpulse(new Vector2(0, -4f), body.getWorldCenter(), true);

            }
        }
        else {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ){
                isRunningRight = true;

                body.applyLinearImpulse(new Vector2(10f, 0), body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ){
                isRunningRight = false;
                body.applyLinearImpulse(new Vector2(-10f, 0), body.getWorldCenter(), true);
            }
        }

        if(!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            body.setLinearVelocity(0,body.getLinearVelocity().y);
        }

    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;
        timer += dt;
        switch (currentState){
            case IDLE:
                region = clairoStand.getKeyFrame(timer, true);
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
            case FALLING:
                region = clairoJump.getKeyFrame(timer, true);
                break;
            case CLIMBING:
                region = clairoClimb.getKeyFrame(timer, true);
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
    public void defineClairo(float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/5, getHeight()/2);
        fix = new FixtureDef();
        fix.shape = shape;
        fix.filter.groupIndex = Decay.GROUP_PLAYER;
        Fixture fixture = body.createFixture(fix);
        body.setUserData("clairo");
    }

    public void reduceShapeBox(){
        body.getFixtureList().get(0).getShape().setRadius(-5);
    }

    public void returneShapeBox(){
        body.getFixtureList().get(0).getShape().setRadius(0);

    }

    public void draw(SpriteBatch batch){
        super.draw(batch);
    }
}
