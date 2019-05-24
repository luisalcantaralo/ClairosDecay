package mx.itesm.decay.Characters;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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


public class Clairo extends Sprite {

    // Clairo's State
    public enum State { IDLE, DEAD, RUNNING, CLIMBING, JUMPING, FALLING, SHOOTING, PUSHING}
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
    private Animation<TextureRegion> clairoPushing;



    // Animation Details
    public float timer;
    public boolean isRunningRight;
    public boolean disableControls = false;
    public boolean isShooting = false;
    public boolean canClimb = false;

    // Movement
    public boolean rightKeyPressed;
    public boolean leftKeyPressed;
    public boolean upKeyPressed;
    public boolean canJump;

    public boolean touchingBox;
    public boolean pushingBox;
    public boolean vfx;

    public boolean transform;


    public Music running;
    public Music jumping;

    //private final TestScreen screen;

    public Clairo(World world, float startPositionX, float startPositionY) {
        this.world = world;
        touchingBox = false;
        currentState = State.JUMPING;
        previousState = State.IDLE;
        isRunningRight = true;
        timer = 0;
        canJump = true;
        transform = false;

        if(Decay.prefs.getString("vfx").equals("ON") || Decay.prefs.getString("vfx") == null) {
            vfx = true;
        }
        else {
            vfx = false;
        }

        Array<TextureRegion> frames = new Array<TextureRegion>();

        if(Decay.prefs.getString("choice") == null) Decay.prefs.putString("choice", "clairo");
        if(Decay.prefs.getString("choice").equals("clairo")) {
            for (int i = 0; i < 13; i++)
                frames.add(new TextureRegion(new Texture("Characters/Detective/Idle/Detective_Idle.png"), i * 288, 0, 288, 288));

            clairoStand = new Animation(0.1f, frames);

            frames.clear();

            for (int i = 0; i < 6; i++) {
                frames.add(new TextureRegion(new Texture("Characters/Detective/Run/Detective_Run.png"), i * 426, 110, 426, 292));
            }
            clairoRun = new Animation<TextureRegion>(0.1f, frames);

            frames.clear();

            for (int i = 3; i < 13; i++)
                frames.add(new TextureRegion(new Texture("Characters/Detective/Jump/Detective_Jump.png"), i * 426, 40, 426, 362));
            clairoJump = new Animation(0.1f, frames);

            frames.clear();

            for (int i = 0; i < 6; i++)
                frames.add(new TextureRegion(new Texture("Characters/Detective/Detective_Climb.png"), i * 284, 80, 284, 178));
            clairoClimb = new Animation(0.1f, frames);

            frames.clear();

            for (int i = 0; i < 6; i++)
                frames.add(new TextureRegion(new Texture("Characters/Detective/Push/Detective_Push.png"), i * 348, 50, 348, 350));
            clairoPushing = new Animation(0.1f, frames);

            frames.clear();

            for (int i = 1; i < 10; i++)
                frames.add(new TextureRegion(new Texture("Characters/Detective/Shoot/Detective_IdleDraw.png"), i * 288, 50, 234, 268));
            clairoShoot = new Animation(0.1f, frames);
        }
        else if(Decay.prefs.getString("choice").equals("kase")){

            for (int i = 0; i < 4; i++)
                frames.add(new TextureRegion(new Texture("Characters/GirlDetective/idle/GirlDetective_Idle.png"), i * 426, 0, 426, 403));


            clairoStand = new Animation(0.1f, frames);

            frames.clear();

            for (int i = 0; i < 8; i++) {
                frames.add(new TextureRegion(new Texture("Characters/GirlDetective/run/GirlDetective_Run.png"), i * 426, 0, 426, 402));
            }
            clairoRun = new Animation<TextureRegion>(0.1f, frames);

            frames.clear();

            for (int i = 0; i < 7; i++)
                frames.add(new TextureRegion(new Texture("Characters/GirlDetective/jump/GirlDetective_Jump.png"), i * 426, 0, 426, 403));
            clairoJump = new Animation(0.1f, frames);

            frames.clear();

            for (int i = 0; i < 6; i++)
                frames.add(new TextureRegion(new Texture("Characters/GirlDetective/climb/GirlDetective_Climb.png"), i * 426, 0, 426, 403));
            clairoClimb = new Animation(0.1f, frames);

            frames.clear();

            for (int i = 0; i < 16; i++)
                frames.add(new TextureRegion(new Texture("Characters/GirlDetective/push/DetectiveGirl-Push.png"), i * 426, 0, 426, 402));
            clairoPushing = new Animation(0.1f, frames);



        }
        setBounds(startPositionX,startPositionY,25, 25);

        defineClairo(startPositionX, startPositionY);

        setRegion(new TextureRegion(new Texture("Characters/Detective/DetectiveRun.png")));


        running = Gdx.audio.newMusic(Gdx.files.internal("SFX/Running.mp3"));
        running.setVolume(0.2f);
        jumping = Gdx.audio.newMusic(Gdx.files.internal("SFX/Propulsor.mp3"));
        jumping.setVolume(0.2f);

    }

    public void update(float dt){
        updateState(dt);
        updateMovement();
        setPosition((body.getPosition().x)-getWidth()/2, (body.getPosition().y)-getHeight()/2);
        setRegion(getFrame(dt));

    }

    public float getClairoX(){
        return body.getPosition().x;
    }
    public float getClairoY(){
        return body.getPosition().y;
    }

    private void updateState(float dt) {

        if(body.getPosition().y < 0){
            jumping.stop();
            running.stop();
            currentState = State.DEAD;
        }
        else{
            if (body.getLinearVelocity().y > 0 && dt < 0.5 && !canClimb && !touchingBox) {
                if(Decay.prefs.getString("vfx").equals("ON")) {
                    jumping.setVolume(0.1f);
                    jumping.play();
                }
                currentState = State.JUMPING;
            }
            else if (body.getLinearVelocity().y > 0 && dt < 0.5 && canClimb) {
                jumping.stop();
                running.stop();
                currentState = State.CLIMBING;
            }
            else if (body.getLinearVelocity().y < 0 && !canClimb && !touchingBox) {
                if(Decay.prefs.getString("vfx").equals("ON")) {
                    jumping.setVolume(0.1f);
                    jumping.play();
                }
                currentState = State.FALLING;
            }
            else if (body.getLinearVelocity().y < 0 && canClimb ) {
                currentState = State.CLIMBING;
            }
            else if (body.getLinearVelocity().x != 0 && !pushingBox && (body.getLinearVelocity().x < -50 || body.getLinearVelocity().x > 50)) {
                if(Decay.prefs.getString("vfx").equals("ON")) {
                    running.setVolume(0.1f);
                    running.play();
                }
                currentState = State.RUNNING;

            }
            else if (body.getLinearVelocity().x != 0 && pushingBox && Math.abs(body.getLinearVelocity().x) > 2) {
                jumping.stop();
                running.stop();
                currentState = State.PUSHING;
            }
            else if (body.getLinearVelocity().y == 0 && body.getLinearVelocity().x == 0 && !isShooting && currentState != State.CLIMBING) {
                jumping.stop();
                running.stop();
                currentState = State.IDLE;
            }
        }

    }

    private void updateMovement() {
        Vector2 clairoWorldCenter = body.getWorldCenter();

        if (!disableControls) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP) && currentState == State.CLIMBING) {
                body.setLinearVelocity(new Vector2(0, 5f));
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && currentState == State.IDLE) {
                body.applyLinearImpulse(new Vector2(0, 5f), body.getWorldCenter(), true);
            } else if (upKeyPressed && currentState != State.JUMPING && currentState != State.FALLING) {

                body.applyLinearImpulse(new Vector2(0, 100f), body.getWorldCenter(), true);
            }


            if (currentState == State.RUNNING) {
                if (rightKeyPressed) {
                    isRunningRight = true;

                    body.applyLinearImpulse(new Vector2(10f, 0), body.getWorldCenter(), true);
                }
                if (leftKeyPressed) {
                    isRunningRight = false;
                    body.applyLinearImpulse(new Vector2(-10f, 0), body.getWorldCenter(), true);
                }
            } else if (currentState == State.JUMPING) {
                if (rightKeyPressed) {
                    isRunningRight = true;

                    body.applyLinearImpulse(new Vector2(10f, 0), body.getWorldCenter(), true);
                }
                if (leftKeyPressed) {
                    isRunningRight = false;
                    body.applyLinearImpulse(new Vector2(-10f, 0), body.getWorldCenter(), true);
                }
            } else if (currentState == State.FALLING) {

                if (rightKeyPressed) {
                    isRunningRight = true;

                    body.applyLinearImpulse(new Vector2(10f, -15f), body.getWorldCenter(), true);
                } else if (leftKeyPressed) {
                    isRunningRight = false;
                    body.applyLinearImpulse(new Vector2(-10f, -15f), body.getWorldCenter(), true);
                } else {
                    body.applyLinearImpulse(new Vector2(0, -4f), body.getWorldCenter(), true);

                }
            } else {
                if (rightKeyPressed) {
                    isRunningRight = true;

                    body.applyLinearImpulse(new Vector2(10f, 0), body.getWorldCenter(), true);
                }
                if (leftKeyPressed) {
                    isRunningRight = false;
                    body.applyLinearImpulse(new Vector2(-10f, 0), body.getWorldCenter(), true);
                }
            }

            if (!rightKeyPressed && !leftKeyPressed) {
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }
    }}



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
            case PUSHING:
                region = clairoPushing.getKeyFrame(timer,true);
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
        shape.setAsBox(getWidth()/12, getHeight()/2);
        fix = new FixtureDef();
        fix.shape = shape;
        fix.filter.groupIndex = Decay.GROUP_PLAYER;
        Fixture fixture = body.createFixture(fix);
        body.setUserData("clairo");
    }

    public void reDefineClairo(float scale){
        Vector2 currentPosition = body.getPosition();
        world.destroyBody(body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition.x,currentPosition.y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/scale, getHeight()/2);
        fix = new FixtureDef();
        fix.shape = shape;
        fix.filter.groupIndex = Decay.GROUP_PLAYER;
        Fixture fixture = body.createFixture(fix);
        body.setUserData("clairo");
    }

    public void setRight(){
        rightKeyPressed = true;
        leftKeyPressed = false;
    }
    public void setLeft(){
        leftKeyPressed = true;
        rightKeyPressed = false;
    }
    public void setUpKey(){
        upKeyPressed = true;
    }
    public void setDefault(){
        leftKeyPressed = false;
        rightKeyPressed = false;
        upKeyPressed = false;
    }

    public void draw(SpriteBatch batch){
        super.draw(batch);
    }

    public void dispose(){
        getTexture().dispose();
        running.dispose();
        jumping.dispose();

    }
}
