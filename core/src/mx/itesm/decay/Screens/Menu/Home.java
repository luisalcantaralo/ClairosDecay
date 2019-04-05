package mx.itesm.decay.Screens.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Screens.GenericScreen;

public class Home extends GenericScreen{
    private final Decay game;
    // main textures
    private Texture menuBuildings;
    private Texture menuBackground;
    private Texture menuLogo;
    // buttons
    private ImageButton buttonStart;
    private ImageButton buttonHelp;

    // stage
    private Stage sceneMenu;

    // floats
    float menuBuildingsY;
    float menuBuildingsX = 200;

    float timeCounter = 0;

    public Home(Decay game) {
        this.game = game;
    }

    @Override
    public void show() {
        // create stage
        sceneMenu = new Stage(view);

        loadBackground();
        loadButtons();

        //we pass the input control to the scene
        Gdx.input.setInputProcessor(sceneMenu);
        Gdx.input.setCatchBackKey(false);

    }
    public void loadBackground(){
        // creating background & logo
        menuBuildings = new Texture("menu/cd-menu-buildings.png");
        menuBackground = new Texture("menu/cd-menu-background.png");
        menuLogo = new Texture("cd-logo.png");
        // setting infinite building moving
        menuBuildings.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    public void loadButtons(){
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
                game.setScreen(new Home(game));

            }
        });

        // help button
        Texture textureButtonHelp = new Texture("menu/cd-button-help.png");
        TextureRegionDrawable trdButtonHelp = new TextureRegionDrawable(new TextureRegion(textureButtonHelp));
        buttonHelp = new ImageButton(trdButtonHelp);
        buttonHelp.setPosition(WIDTH - textureButtonHelp.getWidth()*2, HEIGHT - textureButtonHelp.getHeight()*2);
        sceneMenu.addActor(buttonHelp);

        buttonHelp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // response
                game.setScreen(new Home(game));

            }
        });
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
        menuBuildingsX += 0.20f;

        menuBuildingsY = menuBuildings.getHeight() - HEIGHT - HEIGHT/10;
        batch.draw(menuBackground, 0, 0);

        batch.draw(menuLogo, (WIDTH/2 - menuLogo.getWidth()/2), (HEIGHT/2 - menuLogo.getHeight()/2) * 1.20f);
        batch.draw(menuBuildings, 0, menuBuildingsY, (int)menuBuildingsX, 0, (int)WIDTH, (int)HEIGHT);

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
        menuBuildings.dispose();
        batch.dispose();
    }

}
