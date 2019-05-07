package mx.itesm.decay.Screens.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Display.Text;
import mx.itesm.decay.Generators.GenericButton;
import mx.itesm.decay.Generators.GenericScreen;
import mx.itesm.decay.Screens.LoadingScreen;
import mx.itesm.decay.Screens.Screens;

public class About extends GenericScreen {
    private final Decay game;
    // main textures
    private Texture aboutBackground;
    private Texture aboutBuildings;
    private Texture aboutMonitor;
    private Texture aboutInstructions;
    private Texture aboutTitle;
    // buttons
    private GenericButton buttonBack;
    private GenericButton buttonNextInstructions;
    private GenericButton buttonPreviousInstructions;

    // stage
    private Stage sceneAbout;

    private AssetManager manager;

    public About(Decay game) {
        this.game = game;
        manager = game.getAssetManager();
    }

    @Override
    public void show() {
        // create stage
        sceneAbout = new Stage(view);

        loadAbout();

        //we pass the input control to the scene
        Gdx.input.setInputProcessor(sceneAbout);
        Gdx.input.setCatchBackKey(true);

    }

    private void loadAbout() {
        //background
        aboutBackground = manager.get("backgrounds/cd-simple-background.png");
        aboutBuildings = manager.get("menu/cd-menu-buildings.png");
        aboutMonitor = manager.get("UI/simple-screen.png");
        aboutInstructions = manager.get("menu/cd-about-instructions.png");
        aboutTitle = manager.get("menu/cd-about-title.png");

        // back button
        Texture textureBack = manager.get("menu/cd-button-back.png");
        buttonBack = new GenericButton(textureBack);
        buttonBack.setPosition(buttonBack.getWidth() / 2, HEIGHT - buttonBack.getHeight() * 3/2f);
        buttonBack.getImageButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // responseS
                game.setScreen(new Home(game));

            }
        });
        // next instructions button
        buttonNextInstructions = new GenericButton(textureBack);
        buttonNextInstructions.setPosition(WIDTH/2 + aboutMonitor.getWidth()/2 + buttonNextInstructions.getWidth(), HEIGHT/2 - buttonNextInstructions.getHeight()*2);
        buttonNextInstructions.getImageButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // response
                buttonNextInstructions.remove();
                sceneAbout.addActor(buttonPreviousInstructions.getImageButton());
                aboutInstructions = manager.get("menu/cd-about-us.png");

            }
        });
            // scale and rotate our next instructions button
        buttonNextInstructions.setTransform(true);
        buttonNextInstructions.rotate(180);
        buttonNextInstructions.setScale(0.75f, 0.75f);

        buttonPreviousInstructions = new GenericButton(textureBack);
        buttonPreviousInstructions.setPosition(WIDTH/2 - aboutMonitor.getWidth()/2 - buttonPreviousInstructions.getWidth(), HEIGHT/2 - buttonPreviousInstructions.getHeight()*2.7f);
        buttonPreviousInstructions.getImageButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // response
                buttonPreviousInstructions.remove();
                sceneAbout.addActor(buttonNextInstructions.getImageButton());
                aboutInstructions = manager.get("menu/cd-about-instructions.png");

            }
        });
            // re-scaling our previous instructions button
        buttonPreviousInstructions.setTransform(true);
        buttonPreviousInstructions.setScale(0.75f, 0.75f);

        sceneAbout.addActor(buttonBack.getImageButton());
        sceneAbout.addActor(buttonNextInstructions.getImageButton());

    }


    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(aboutBackground, 0, 0);
        batch.draw(aboutBuildings, -640, 0);
        batch.draw(aboutMonitor, WIDTH/2 - aboutMonitor.getWidth()/2, HEIGHT/2 - aboutMonitor.getHeight()/2);
        batch.draw(aboutInstructions, WIDTH/2 - aboutMonitor.getWidth()/2, HEIGHT/2 - aboutMonitor.getHeight()/2);
        batch.draw(aboutTitle, WIDTH/2 - aboutTitle.getWidth()/2, HEIGHT - aboutTitle.getHeight() * 1.5f);

        batch.end();

        sceneAbout.draw();
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
        /*aboutBackground.dispose();
        aboutBuildings.dispose();
        aboutMonitor.dispose();
        aboutInstructions.dispose();
        aboutTitle.dispose();
        */
        sceneAbout.dispose();
        batch.dispose();
    }
}
