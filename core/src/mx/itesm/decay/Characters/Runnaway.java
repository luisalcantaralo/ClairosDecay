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


public class Runnaway extends Sprite {

    public enum State { IDLE,  RUNNING}
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
    private Animation<TextureRegion> standing;
    private Animation<TextureRegion> running;

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

    public boolean transform;


    //private final TestScreen screen;

    public Runnaway(World world, float startPositionX, float startPositionY) {
        this.world = world;
        touchingBox = false;
        currentState = State.IDLE;
        previousState = State.IDLE;
        isRunningRight = true;
        timer = 0;
        canJump = true;
        transform = false;
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 6; i++)
            frames.add(new TextureRegion(new Texture("Characters/Runnaway/Runnaway_Running/Runnaway.png"), i * 71, 0, 71, 67));
        running = new Animation(0.1f, frames);

        frames.clear();

        for(int i = 0; i < 56; i++)
            frames.add(new TextureRegion(new Texture("Characters/Runnaway/Runnaway_Running/Runnaway.png"), i * 284, 0, 284, 268));
        standing = new Animation(0.1f, frames);

        setBounds(startPositionX,startPositionY,25, 25);

        defineRunnaway(startPositionX, startPositionY);

        setRegion(new TextureRegion(new Texture("Characters/Runnaway/Runnaway_Running/Runnaway.png")));




    }

    public void update(float dt){
        updateMovement();
        setPosition((body.getPosition().x)-getWidth()/2, (body.getPosition().y)-getHeight()/2);
        setRegion(getFrame(dt));

    }



    private void updateMovement() {


    }


    public TextureRegion getFrame(float dt){
        TextureRegion region;
        timer += dt;
        switch (currentState){
            case IDLE:
                region = standing.getKeyFrame(timer, true);
                break;
            case RUNNING:
                region = running.getKeyFrame(timer, true);
                break;
            default:
                region = standing.getKeyFrame(timer, true);
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
    public void defineRunnaway(float x, float y) {

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
        body.setUserData("runnaway");
    }


    public void draw(SpriteBatch batch){
        super.draw(batch);
    }
}
