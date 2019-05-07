package mx.itesm.decay.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Generators.GenericScreen;
import mx.itesm.decay.Screens.Maps.FirstLevel;

public class Splash extends GenericScreen {
    private final Decay game;
    Texture logo;
    float timer;
    float time;
    float alpha =  1;

    public Splash(Decay game){
        this.game = game;
    }

    @Override
    public void show() {
        logo = new Texture("misc/company-logo.png");
    }

    @Override
    public void render(float delta) {
        time = Gdx.graphics.getDeltaTime();
        timer += time;
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setColor(1,1,1,alpha);
        batch.draw(logo,GenericScreen.WIDTH/2 - logo.getWidth()/2,GenericScreen.HEIGHT/2 - logo.getHeight()/2);
        alpha -= Gdx.graphics.getDeltaTime()/2;
        if(timer > 2){
            game.setScreen(new LoadingScreen(game, Screens.HOME));
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
