package mx.itesm.decay.Screens.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Generators.GenericButton;
import mx.itesm.decay.Generators.GenericScreen;

public class Settings extends GenericScreen {
    private final Decay game;
    // main textures
    private Texture settingsBackground;
    private Texture settingsBuildings;
    private Texture settingsMonitor;
    private Texture settingsTitle;
    // buttons
    private GenericButton buttonBack;
    // stage
    private Stage sceneSettings;

    public Settings(Decay game) {
        this.game = game;
    }

    @Override
    public void show() {
        // create stage
        sceneSettings = new Stage(view);

        loadSettings();

        //we pass the input control to the scene
        Gdx.input.setInputProcessor(sceneSettings);
        Gdx.input.setCatchBackKey(false);
    }

    private void loadSettings() {
        //background
        settingsBackground = new Texture("backgrounds/cd-simple-background.png");
        settingsBuildings = new Texture("menu/cd-menu-buildings.png");
        settingsMonitor = new Texture("menu/cd-settings-monitor.png");
        settingsTitle = new Texture("menu/cd-settings-title.png");

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

        sceneSettings.addActor(buttonBack.getImageButton());
    }


    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(settingsBackground, 0, 0);
        batch.draw(settingsBuildings, -640, 0);
        batch.draw(settingsTitle, WIDTH/2 - settingsTitle.getWidth()/2, HEIGHT - settingsTitle.getHeight() * 1.5f);
        batch.draw(settingsMonitor, WIDTH/2 - settingsMonitor.getWidth()/2, HEIGHT/2 - settingsMonitor.getHeight()/2);

        batch.end();

        sceneSettings.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        settingsBackground.dispose();
        settingsBuildings.dispose();
        settingsMonitor.dispose();
        settingsTitle.dispose();
        sceneSettings.dispose();
        batch.dispose();
    }
}
