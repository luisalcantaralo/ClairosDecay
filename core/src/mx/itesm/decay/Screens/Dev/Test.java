package mx.itesm.decay.Screens.Dev;

import com.badlogic.gdx.graphics.Texture;

import mx.itesm.decay.Screens.GenericScreen;

public class Test extends GenericScreen {

    private Texture sampleBackground;

    @Override
    public void show() {
        sampleBackground = new Texture("tilesets/cd-grey-tilesheet.png");
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(sampleBackground, 0, 0);
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
