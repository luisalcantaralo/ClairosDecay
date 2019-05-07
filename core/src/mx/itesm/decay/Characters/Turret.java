package mx.itesm.decay.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import mx.itesm.decay.Screens.TestScreen;

public class Turret extends Sprite {

    // Box2d
    public World world;
    public Body body;

    // Animation
    private Animation<TextureRegion> turretIdle;
    public float timer;
    private float timerMovement = 0;
    private float midPoint;
    private float dx = 1;
    private float range;


    public Turret(World world, float x, float y){

        this.world = world;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 6; i++)
            frames.add(new TextureRegion(new Texture("Turret/turret.png"), i * 150, 0, 150, 138));
        turretIdle = new Animation(0.1f, frames);

        setBounds(x,y, 100, 100);
        dx = 100;
        midPoint = x;
        frames.clear();
        defineTurret();
        setRegion(new TextureRegion(new Texture("Turret/turret.png")));
    }

    private void defineTurret() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/4, getHeight()/4);
        FixtureDef fix = new FixtureDef();
        fix.shape = shape;
        Fixture fixture = body.createFixture(fix);
    }

    public void update(float dt){
        setRegion(getFrame(dt));
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);

    }

    private TextureRegion getFrame(float dt) {
        TextureRegion region;
        timer += dt;
        region = turretIdle.getKeyFrame(timer, true);
        return region;
    }

    public void defineRange(float x){
        this.range = x;
    }

    public void updateMovement(float dt){

        if(body.getPosition().x > 2500 || body.getPosition().x < 1500){
            dx *= -1;
            body.applyLinearImpulse(new Vector2(9999999f*dx, 0), body.getWorldCenter(), true);

        }
        else {
            body.applyLinearImpulse(new Vector2(9999999f*dx, 0), body.getWorldCenter(), true);

        }

    }
}
