package mx.itesm.decay.Generators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Screens.GameStates;

public class PauseScene extends Stage {
    private final Decay game;
    private GameStates state;

    public PauseScene(Viewport view, Batch batch, Decay game, final Stage sceneHUD) {
        super(view, batch);
        this.game=game;
        Pixmap pixmap= new Pixmap((int)(GenericScreen.WIDTH*0.6),(int)(GenericScreen.HEIGHT*0.7f), Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f,0.1f,0.1f,0.5f);
        pixmap.fillRectangle(0,0,pixmap.getWidth(),pixmap.getHeight());
        Texture textureRectangle= new Texture(pixmap);
        pixmap.dispose();
        Image imgRectangle= new Image(textureRectangle);
        imgRectangle.setPosition(0.2f*GenericScreen.WIDTH,0.16f*GenericScreen.HEIGHT);
        this.addActor(imgRectangle);

        final Decay game2= game;
        Texture backBtn= new Texture("menu/cd-button-back.png");
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(backBtn));
        ImageButton backButton = new ImageButton(trdBack);
        backButton.setPosition(GenericScreen.WIDTH/2-backBtn.getWidth()/2,GenericScreen.HEIGHT*0.2f);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // response
                state=GameStates.PLAYING;
                dispose();
                Gdx.input.setInputProcessor(sceneHUD);
            }
        });
        this.addActor(backButton);
    }
}
