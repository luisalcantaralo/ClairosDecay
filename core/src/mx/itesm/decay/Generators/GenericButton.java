package mx.itesm.decay.Generators;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Screens.Menu.Home;

public class GenericButton {

    private ImageButton button;

    public GenericButton(String path){
        Texture textureButton = new Texture(path);
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
}
