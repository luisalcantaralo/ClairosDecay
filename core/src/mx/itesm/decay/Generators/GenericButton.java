package mx.itesm.decay.Generators;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GenericButton {

    private ImageButton button;
    private Texture textureButton;

    public GenericButton(String path){
        textureButton = new Texture(path);
        TextureRegionDrawable trdButton = new TextureRegionDrawable(new TextureRegion(textureButton));
        button = new ImageButton(trdButton);
    }

    public void setPosition(float x, float y){
        button.setPosition(x, y);
    }
    public ImageButton getImageButton(){
        return button;
    }
    public void remove(){
        button.remove();
    }
    public float getWidth(){
        return textureButton.getWidth();
    }
    public float getHeight(){
        return textureButton.getHeight();
    }
}
