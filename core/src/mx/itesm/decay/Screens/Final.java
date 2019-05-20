package mx.itesm.decay.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Generators.GenericButton;
import mx.itesm.decay.Generators.GenericScreen;

public class Final extends GenericScreen {
    private Decay game;
    Stage scene;

    public Final(Decay game){
        this.game = game;
    }

    @Override
    public void show() {
        scene = new Stage(view);
        createButtons();
        Gdx.input.setInputProcessor(scene);

    }

    private void createButtons() {
        //Message
        Texture gameOver = new Texture("misc/first-level-completed.png");
        Image imageGameOver = new Image(gameOver);
        imageGameOver.setPosition(GenericScreen.WIDTH/2 - gameOver.getWidth()/2, GenericScreen.HEIGHT/2 - gameOver.getHeight()/2);

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

        scene.addActor(imageGameOver);
        scene.addActor(buttonMenu.getImageButton());

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        batch.dispose();
        scene.dispose();

    }
}
