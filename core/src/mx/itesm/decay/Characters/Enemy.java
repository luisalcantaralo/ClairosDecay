package mx.itesm.decay.Characters;




import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;

import mx.itesm.decay.Screens.TestScreen;


public class Enemy extends Sprite {

    // Box2d
    public World world;
    public Body body;

    // Animations
    private Animation<TextureRegion> enemyRun;
    private TextureRegion enemyIdleRight;
    private Animation<TextureRegion> enemySurrender;
    private Animation<TextureRegion> enemyIdleLeft;


    // Animation Details
    public float timer;
    public boolean isRunning;
    public boolean disableControls = false;

    private final TestScreen screen;

    public Enemy(TestScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        isRunning = false;
        timer = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 6; i++)
            frames.add(new TextureRegion(new Texture("Characters/Runnaway/Runnaway_Running/Runnaway.png"), i * 71, 0, 71, 67));
        enemyRun = new Animation(0.1f, frames);

        frames.clear();


        enemyIdleRight = new TextureRegion(new Texture("Characters/Runnaway/Runnaway_Stop/Runnaway_Stop.png"), 0,0, 284, 268);

        frames.clear();


        setBounds(x,y,200, 200);

        defineEnemy();

        setRegion(new TextureRegion(new Texture("Turret/turret.png")));

    }

    public void update(float dt){
        setRegion(getFrame(dt));
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
    }


    public TextureRegion getFrame(float dt){
        TextureRegion region;
        timer += dt;

        if(!isRunning){
            region = enemyIdleRight;
        }
        else region = enemyRun.getKeyFrame(timer, true);

        return region;
    }

    // Box2d initialization
    public void defineEnemy() {
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

