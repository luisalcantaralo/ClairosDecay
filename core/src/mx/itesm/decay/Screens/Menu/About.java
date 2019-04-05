package mx.itesm.decay.Screens.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Generators.GenericButton;
import mx.itesm.decay.Screens.GenericScreen;

public class About extends GenericScreen {
    private final Decay game;
    // main textures
    private Texture aboutBackground;
    // buttons
    private GenericButton buttonBack;

    // stage
    private Stage sceneAbout;

    public About(Decay game) {
        this.game = game;
    }

    @Override
    public void show() {
        // create stage
        sceneAbout = new Stage(view);

        loadAbout();

        //we pass the input control to the scene
        Gdx.input.setInputProcessor(sceneAbout);
        Gdx.input.setCatchBackKey(false);
    }

    private void loadAbout() {
        //background
        aboutBackground = new Texture("backgrounds/cd-simple-background.png");

        // back button
        buttonBack = new GenericButton("menu/cd-button-back.png");
        buttonBack.setPosition(buttonBack.getWidth(), HEIGHT - buttonBack.getHeight() * 2);
        buttonBack.getImageButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // response
                game.setScreen(new Home(game));

            }
        });

        sceneAbout.addActor(buttonBack.getImageButton());
    }


    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(aboutBackground, 0, 0);

        batch.end();

        sceneAbout.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
