package mx.itesm.decay.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import mx.itesm.decay.Decay;

public class Box extends Sprite {
    // Box2d
    public World world;
    public Body body;
    FixtureDef fix;

    Texture boxTexture;

    float initialX;
    float initialY;

    public Box(World world, float x, float y){
        this.world = world;
        this.initialX = x;
        this.initialY = y;

        boxTexture = new Texture("Items/Box.png");

        setBounds(x,y,128/7,128/7);
        defineBox(x,y);
        setRegion(new TextureRegion(boxTexture));

    }

    public void update(){


        if(body.getLinearVelocity().y < 0 && body.getLinearVelocity().x != 0){
            body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
        }
        if(body.getPosition().y < 0){
            body.setTransform( this.initialX, this.initialY,0);
        }
        setPosition((body.getPosition().x)-getWidth()/2, (body.getPosition().y)-getHeight()/2);
        setRegion(new TextureRegion(boxTexture));
    }

    public void defineBox(float x, float y){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth(), getHeight()/2);
        fix = new FixtureDef();
        fix.shape = shape;
        fix.friction = 0;
        fix.density = 0.001f;
        Fixture fixture = body.createFixture(fix);
        body.setUserData("box");
    }
}
