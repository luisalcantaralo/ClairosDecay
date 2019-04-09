package mx.itesm.decay.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Display.Text;
import mx.itesm.decay.Generators.GenericScreen;

public class IntroScreen extends GenericScreen {
    private final Decay game;

    float timer;
    String message;
    Text text;
    float x;
    float y;
    Texture texture;
    public IntroScreen(Decay game, String message, float time, float x, float y){
        this.game = game;
        timer = time;
        this.message = message;

        this.x = x;

        this.y = y;
    }
    @Override
    public void show() {
        texture = new Texture("misc/cd-mission-chinatown.png");
    }

    @Override
    public void render(float delta) {
        float time = Gdx.graphics.getDeltaTime();
        timer += time;
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texture,0,0);
        if(timer > 8){
            game.setScreen(new TestScreen(game));
        }
        batch.end();

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
