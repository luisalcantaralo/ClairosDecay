package mx.itesm.decay.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Generators.GenericScreen;
import mx.itesm.decay.Screens.Maps.FirstLevel;

public class Splash implements Screen {

    public static final int WIDTH = 1280;
    public static final int HEIGHT= 720;

    private final Decay game;
    private OrthographicCamera camera;
    private Viewport view;
    private SpriteBatch batch;

    private Texture textFondo;

    float timer;
    float time;
    float alpha =  1;



    public Splash(Decay game) {
        this.game = game;
    }

    @Override
    public void show() {

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH/2, HEIGHT/2, 0);
        camera.update();

        view  =new StretchViewport(WIDTH, HEIGHT, camera);
        batch = new SpriteBatch();

        textFondo = new Texture("misc/company-logo.png");




    }

    @Override
    public void render(float delta) {
        time = Gdx.graphics.getDeltaTime();
        timer += time;
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Borra completo
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //Dibujes
        batch.setColor(1,1,1,alpha);
        batch.draw(textFondo,WIDTH/2 - textFondo.getWidth()/2,HEIGHT/2-textFondo.getHeight()/2);
        alpha -= Gdx.graphics.getDeltaTime()/2;
        if(timer > 2){
            game.setScreen(new LoadingScreen(game, Screens.HOME));
        }
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
