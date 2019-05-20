package mx.itesm.decay.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


public class FatGuy extends Sprite {
    // Box2d
    public World world;
    public Body body;

    // Animation
    private Animation<TextureRegion> fatGuyIdle;
    public float timer;


    public FatGuy(World world, float startPositionX, float startPositionY){
        this.world = world;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 6; i++)
            frames.add(new TextureRegion(new Texture("Characters/FatGuy/FatGuy.png"), i * 212, 40, 212, 160));
        fatGuyIdle = new Animation(0.1f, frames);

        setBounds(startPositionX,startPositionY, 25, 25);

        frames.clear();
        defineFatGuy();
        setRegion(new TextureRegion(new Texture("Characters/FatGuy/FatGuy.png")));
    }//
    private void defineFatGuy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/2, getHeight()/2);
        FixtureDef fix = new FixtureDef();
        fix.shape = shape;
        body.setUserData("fat");
        Fixture fixture = body.createFixture(fix);
    }

    public void update(float dt){
        setRegion(getFrame(dt));
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);

    }

    private TextureRegion getFrame(float dt) {
        TextureRegion region;
        timer += dt;
        region = fatGuyIdle.getKeyFrame(timer, true);
        return region;
    }
}
