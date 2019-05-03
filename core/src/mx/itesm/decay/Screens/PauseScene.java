package mx.itesm.decay.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.decay.Generators.GenericButton;
import mx.itesm.decay.Generators.GenericScreen;

public class PauseScene extends Stage {
    public PauseScene(Viewport view, Batch batch) {
        super(view, batch);
        Pixmap pixmap= new Pixmap((int)(GenericScreen.WIDTH*0.7f),(int)(GenericScreen.HEIGHT*0.8f), Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f,0.1f,0.1f,0.65f);
        pixmap.fillRectangle(0,0,pixmap.getWidth(),pixmap.getHeight());
        Texture textureRectangle= new Texture(pixmap);
        pixmap.dispose();
        Image imgRectangle= new Image(textureRectangle);
        imgRectangle.setPosition(0.15f*GenericScreen.WIDTH,0.1f*GenericScreen.WIDTH);
        this.addActor(imgRectangle);


        Texture backBtn= new Texture("menu/cd-button-back.png");
        backButton= new GenericButton(backBtn);
    }
}
