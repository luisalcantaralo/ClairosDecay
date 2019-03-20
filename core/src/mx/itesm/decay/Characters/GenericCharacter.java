package mx.itesm.decay.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public abstract class GenericCharacter extends Sprite{

    // Images, specs, etc.
    private Sprite sprite;
    private int NUM_COLUMNS;
    private int NUM_ROWS;

    // Distances, time, velocity, density, shape
    private float DX = 4;
    private float DY = 4;

    // Box2d
    private World world;
    private Body body;
    private BodyDef bdef;

    // Animation
    private Texture characterSheet;
    private Animation<TextureRegion> idleAnimation;


    public GenericCharacter(Texture texture, float x, float y, World world){
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
        characterSheet = texture;
        this.world = world;
        bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.position.set(x,y);
        body = this.world.createBody(bdef);

    }
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    // Takes a row from character sheet, add textures regions to an array and returns the created animation
    public Animation createAnimation(int numColumns, int numRow, float time){
        int lengthRow = characterSheet.getHeight() / NUM_ROWS;
        Array<TextureRegion> regions = new Array<TextureRegion>();
        for(int i = 0; i < numColumns; i++){
            regions.add(new TextureRegion(characterSheet, i*128,(numRow-1)*lengthRow,128,128));
        }
        Animation<TextureRegion> animation = new Animation<TextureRegion>(time, regions);
        return animation;
    }

    public Animation<TextureRegion> getIdleAnimation() {
        return idleAnimation;
    }
    public void setDX(float DX) {
        this.DX = DX;
    }
    public void setDY(float DY) {
        this.DY = DY;
    }


}
