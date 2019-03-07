package mx.itesm.decay.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class GenericCharacter {

    private Sprite sprite;
    private float DX = 4;
    private float DY = 4;
    private float timer;
    private int NUM_COLUMNS;
    private int NUM_ROWS;

    private Texture characterSheet;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;


    public GenericCharacter(Texture texture, float x, float y){
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
        characterSheet = texture;
        walkAnimation = createAnimation(4,1);
        timer = 0;

    }
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }
    // Takes a row from character sheet, divides it into texture regions and returns an animation
    public Animation createAnimation(int numColumns, int numRow){

        int lengthRow = characterSheet.getHeight() / NUM_ROWS;
        TextureRegion[] regions = new TextureRegion[numColumns];
        for(int i = 0; i < numColumns; i++){
            regions[i] = new TextureRegion(characterSheet, i*128,(numRow-1)*lengthRow,128,128);
        }
        Animation<TextureRegion> animation = new Animation<TextureRegion>(0.25f, regions);
        return animation;
    }
    public float getTimer() {
        return timer;
    }

    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    public Animation<TextureRegion> getIdleAnimation() {
        return idleAnimation;
    }

    public Animation<TextureRegion> getRunAnimation() {
        return runAnimation;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }
    public void setDX(float DX) {
        this.DX = DX;
    }
    public void setDY(float DY) {
        this.DY = DY;
    }

    public void setWalkAnimation(Animation<TextureRegion> walkAnimation) {
        this.walkAnimation = walkAnimation;
    }

    public void setIdleAnimation(Animation<TextureRegion> idleAnimation) {
        this.idleAnimation = idleAnimation;
    }

    public void setRunAnimation(Animation<TextureRegion> runAnimation) {
        this.runAnimation = runAnimation;
    }
}
