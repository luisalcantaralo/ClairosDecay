package mx.itesm.decay.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Display.Text;

public class IntroScreen extends GenericScreen{
    private final Decay game;

    float timer;
    String message;
    Text text;
    float x;
    float y;
    public IntroScreen(Decay game, String message, float time, float x, float y){
        this.game = game;
        timer = time;
        this.message = message;

        this.x = x;

        this.y = y;
    }
    @Override
    public void show() {
        text = new Text();
    }

    @Override
    public void render(float delta) {
        float time = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        text.showText(batch, message,x, y);
        batch.end();

        if(timer > 8){
            game.setScreen(new TestScreen(game));
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

    }
}
