package mx.itesm.decay.Screens.Dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Generators.GenericScreen;

public class Test extends GenericScreen {

    private final Decay game;
    private Texture sampleBackground;
    private Texture sampleBackgroundBack;
    private Texture sampleLogo;


    private Texture sampleTest;

    private Stage sceneMenu;


    private ImageButton buttonStart;

    float menuBuildingsY;
    float sourceX = 200;
    float timeCounter = 0;

    public Test(Decay game) {
        this.game = game;
    }

    @Override
    public void show() {

        sceneMenu = new Stage(view);

        sampleTest = new Texture("menu/cd-button-help.png");

        sampleBackground = new Texture("menu/cd-menu-buildings.png");
        sampleBackgroundBack = new Texture("menu/cd-menu-background.png");
        sampleLogo = new Texture("cd-logo.png");
        sampleBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        // press start button
        Texture textureButtonStart = new Texture("menu/cd-button-start.png");
        TextureRegionDrawable trdButtonStart = new TextureRegionDrawable(new TextureRegion(textureButtonStart));
        buttonStart = new ImageButton(trdButtonStart);
        buttonStart.setPosition(WIDTH/2 - textureButtonStart.getWidth()/2, HEIGHT/10 - textureButtonStart.getHeight()/2);
        sceneMenu.addActor(buttonStart);

        buttonStart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // response
                game.setScreen(new Test(game));
            }
        });




        //we pass the input control to the scene
        Gdx.input.setInputProcessor(sceneMenu);
        Gdx.input.setCatchBackKey(false);

    }
    public void blinkButtonStart(){
        if(timeCounter > 0.75 && timeCounter < 1.5){
            buttonStart.remove();
        }else if(timeCounter > 1.5 && timeCounter < 2.25){
            sceneMenu.addActor(buttonStart);
            timeCounter = 0;
        }
    }

    @Override
    public void render(float delta) {
        timeCounter += delta;
        deleteScreen();
        blinkButtonStart();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sourceX += 0.20f;

        menuBuildingsY = sampleBackground.getHeight() - HEIGHT - HEIGHT/10;
        batch.draw(sampleBackgroundBack, 0, 0);

        batch.draw(sampleLogo, (WIDTH/2 - sampleLogo.getWidth()/2), (HEIGHT/2 - sampleLogo.getHeight()/2) * 1.20f);
        batch.draw(sampleBackground, 0, menuBuildingsY, (int)sourceX, 0, (int)WIDTH, (int)HEIGHT);

        batch.draw(sampleTest, WIDTH/2, HEIGHT/2);

        batch.end();

        sceneMenu.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        sampleBackground.dispose();
        batch.dispose();
    }
}
