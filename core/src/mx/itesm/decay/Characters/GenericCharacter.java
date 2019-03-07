package mx.itesm.decay.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GenericCharacter {

    private Sprite sprite;
    private float DX = 4;
    private float DY = 4;

    private Texture characterSheet;
    private TextureRegion[] regions;

    public Animation<TextureRegion> walkAnimation;
    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> runAnimation;

    public float timer;

    public GenericCharacter(Texture texture, float x, float y){
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
        characterSheet = texture;
        walkAnimation = createAnimation(4,1);
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }
    public Animation createAnimation(int numColumns, int numRows){

        for(int i = 0; i < numColumns; i++){
            regions[i] = new TextureRegion(characterSheet, (i+1)*(characterSheet.getWidth()/numColumns),0,128,128);
        }
        Animation<TextureRegion> animation = new Animation<TextureRegion>(1/numColumns, regions);
        timer = 0;
        return animation;
    }
    public void setDX(float DX) {
        this.DX = DX;
    }
    public void setDY(float DY) {
        this.DY = DY;
    }
}
