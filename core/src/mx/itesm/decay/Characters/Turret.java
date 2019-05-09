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

import mx.itesm.decay.Decay;

public class Turret extends Sprite {
    // Box2d
    private World world;
    private Body body;
    private BodyDef bdef = new BodyDef();
    private Animation<TextureRegion> turretAnimation;

    FixtureDef fix;

    TextureRegion boxTexture;
    float timer;
    float startPositionX;
    float startPositionY;
    float turretSpeed = 10;
    float turretRatioXN = 15;
    float turretRatioXP = 15;

    public Turret(World world, float x, float y) {
        this.world = world;
        this.startPositionX = x;
        this.startPositionY = y;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(new Texture("Turret/turret.png"), i * 150, 0, 150, 138));
        turretAnimation = new Animation(0.1f, frames);


        boxTexture = new TextureRegion(new Texture("Turret/turret.png"), 0, 0, 150, 138);
        timer = 0;
        setBounds(startPositionX, startPositionY, 150 / 9, 138 / 9);
        defineBox(startPositionX, startPositionY);

    }

    public void update(float dt) {
        setPosition((body.getPosition().x) - getWidth() / 2, (body.getPosition().y) - getHeight() / 2);
        setRegion(getFrame(dt));

        if (body.getPosition().x <= startPositionX - turretRatioXN) {
            body.applyLinearImpulse(new Vector2(turretSpeed, 0), body.getWorldCenter(), true);
        } else if (body.getPosition().x >= startPositionX + turretRatioXP){
            body.applyLinearImpulse(new Vector2(-turretSpeed, 0), body.getWorldCenter(), true);
        }
    }

    public TextureRegion getFrame(float dt){
            TextureRegion region;
            timer += dt;

            region = turretAnimation.getKeyFrame(timer, true);

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
        body.setUserData("turret");
    }
    public void move(float dt){


    }
}
