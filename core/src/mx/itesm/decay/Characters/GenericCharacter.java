package mx.itesm.decay.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public abstract class GenericCharacter {

    private Sprite sprite;
    private float DX = 4;
    private float DY = 4;
    private float timer;
    private int NUM_COLUMNS;
    private int NUM_ROWS;

    private Texture characterSheet;
    private Animation<TextureRegion> animation;


    public GenericCharacter(Texture texture, float x, float y){
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
        characterSheet = texture;
        animation = createAnimation(4,1);
        timer = 0;

    }
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }
    // Takes a row from character sheet, add textures regions to an array and returns the created animation
    public Animation createAnimation(int numColumns, int numRow){

        int lengthRow = characterSheet.getHeight() / NUM_ROWS;
        Array<TextureRegion> regions = new Array<TextureRegion>();
        for(int i = 0; i < numColumns; i++){
            regions.add(new TextureRegion(characterSheet, i*128,(numRow-1)*lengthRow,128,128));
        }
        Animation<TextureRegion> animation = new Animation<TextureRegion>(0.25f, regions);
        return animation;
    }
    public float getTimer() {
        return timer;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
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


}
