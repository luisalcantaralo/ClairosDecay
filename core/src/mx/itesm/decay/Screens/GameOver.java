package mx.itesm.decay.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Display.Text;
import mx.itesm.decay.Generators.GenericButton;
import mx.itesm.decay.Generators.GenericScreen;
import mx.itesm.decay.Screens.Maps.FirstLevel;
import mx.itesm.decay.Screens.Menu.Settings;

public class GameOver extends GenericScreen {

    private Screens screens;
    private Decay game;
    private Texture backgroundTexture;
    Stage scene;

    public  GameOver(Decay game, Screens screens){
        this.game = game;
        this.screens = screens;
    }

    @Override
    public void show() {
        backgroundTexture = new Texture("misc/game-over.png");
        scene = new Stage(view);
        createButtons();
        Gdx.input.setInputProcessor(scene);

    }

    private void createButtons() {
        //BackToMenuButton
        Texture menuTexture = new Texture("menu/cd-back-to-menu-button.png");
        GenericButton buttonMenu = new GenericButton(menuTexture);
        buttonMenu.setPosition(GenericScreen.WIDTH/2 - buttonMenu.getWidth()/2, GenericScreen.HEIGHT/4 - buttonMenu.getHeight()/2);
        buttonMenu.getImageButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // response
                game.setScreen(new LoadingScreen(game,Screens.HOME));
            }
        });

        //BackToLevel
        Texture levelTexture = new Texture("menu/cd-retry-button.png");
        GenericButton buttonLevel = new GenericButton(levelTexture);
        buttonLevel.setPosition(GenericScreen.WIDTH/2 - buttonLevel.getWidth()/2, GenericScreen.HEIGHT/2.5f - buttonLevel.getHeight()/2);
        buttonLevel.getImageButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // response
                game.setScreen(new FirstLevel(game));

            }
        });


        scene.addActor(buttonMenu.getImageButton());
        scene.addActor(buttonLevel.getImageButton());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture,WIDTH/2-170,HEIGHT/2);
        batch.end();
        scene.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
