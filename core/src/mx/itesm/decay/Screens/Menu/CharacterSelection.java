package mx.itesm.decay.Screens.Menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Generators.GenericButton;
import mx.itesm.decay.Generators.GenericScreen;

public class CharacterSelection extends GenericScreen {
    private final Decay game;
    // main textures
    private Texture settingsBackground;
    private Texture settingsBuildings;
    private Texture settingsMonitor;
    private Texture settingsTitle;
    // buttons
    private GenericButton buttonBack;
    private GenericButton buttonOnMusic;
    private GenericButton buttonOffMusic;


    // stage
    private Stage sceneSettings;

    //Loading
    private AssetManager manager;

    public CharacterSelection(Decay game) {
        this.game = game;
        manager = game.getAssetManager();
    }

    @Override
    public void show() {
        // create stage
        sceneSettings = new Stage(view);

        loadSettings();

        //we pass the input control to the scene
        Gdx.input.setInputProcessor(sceneSettings);
        Gdx.input.setCatchBackKey(true);
    }

    private void loadSettings() {
        //background
        settingsBackground = manager.get("backgrounds/cd-simple-background.png");
        settingsBuildings = manager.get("menu/cd-menu-buildings.png");
        settingsMonitor = manager.get("menu/cd-settings-monitor.png");
        settingsTitle = manager.get("menu/cd-settings-title.png");

        // back button
        Texture textureBack = manager.get("menu/cd-button-back.png");
        buttonBack = new GenericButton(textureBack);
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
        if(Decay.prefs.getString("music").equals("OFF")){
            Home.menuTheme.pause();
        }
        else {
            Home.menuTheme.play();
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(settingsBackground, 0, 0);
        batch.draw(settingsBuildings, -640, 0);
        batch.draw(settingsTitle, WIDTH/2 - settingsTitle.getWidth()/2, HEIGHT - settingsTitle.getHeight() * 1.5f);
        batch.draw(settingsMonitor, WIDTH/2 - settingsMonitor.getWidth()/2, HEIGHT/2 - settingsMonitor.getHeight()/2);

        batch.end();

        sceneSettings.draw();
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new Home(game));
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        /*
        settingsBackground.dispose();
        settingsBuildings.dispose();
        settingsMonitor.dispose();
        settingsTitle.dispose();
        */
        sceneSettings.dispose();
        batch.dispose();
    }
}