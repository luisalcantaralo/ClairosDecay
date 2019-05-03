package mx.itesm.decay.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Generators.GenericButton;
import mx.itesm.decay.Generators.GenericScreen;
import mx.itesm.decay.Screens.Menu.Settings;

public class PauseScene extends Stage {
    private final Decay game;

    public PauseScene(Viewport view, Batch batch, Decay game) {
        super(view, batch);
        this.game=game;
        Pixmap pixmap= new Pixmap((int)(GenericScreen.WIDTH*0.7f),(int)(GenericScreen.HEIGHT*0.8f), Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f,0.1f,0.1f,0.65f);
        pixmap.fillRectangle(0,0,pixmap.getWidth(),pixmap.getHeight());
        Texture textureRectangle= new Texture(pixmap);
        pixmap.dispose();
        Image imgRectangle= new Image(textureRectangle);
        imgRectangle.setPosition(0.15f*GenericScreen.WIDTH,0.1f*GenericScreen.WIDTH);
        this.addActor(imgRectangle);

        final Decay game2= game;
        Texture backBtn= new Texture("menu/cd-button-back.png");
        GenericButton backButton= new GenericButton(backBtn);
        backButton.setPosition(GenericScreen.WIDTH/2-backBtn.getWidth()/2,GenericScreen.HEIGHT*0.2f);
        backButton.getImageButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // response
                game2.setScreen(new LoadingScreen(game2, Screens.HOME));

            }
        });
    }
}
