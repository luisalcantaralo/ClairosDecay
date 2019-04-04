package mx.itesm.decay.Screens.Dev;

import com.badlogic.gdx.graphics.Texture;

import mx.itesm.decay.Screens.GenericScreen;

public class Test extends GenericScreen {

    private Texture sampleBackground;
    private Texture sampleBackgroundBack;
    private Texture sampleLogo;
    float menuBuildingsY;
    float sourceX = 200;

    @Override
    public void show() {
        sampleBackground = new Texture("menu/cd-menu-buildings.png");
        sampleBackgroundBack = new Texture("menu/cd-menu-background.png");
        sampleLogo = new Texture("UI/simple-screen.png");
        sampleBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

    }

    @Override
    public void render(float delta) {
        deleteScreen();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        sourceX += 0.20f;

        menuBuildingsY = sampleBackground.getHeight() - HEIGHT - HEIGHT/10;
        batch.draw(sampleBackgroundBack, 0, 0);
        batch.draw(sampleLogo, (WIDTH/2 - sampleLogo.getWidth()/2), (HEIGHT/2 - sampleLogo.getHeight()/2) * 1.20f);
        batch.draw(sampleBackground, 0, menuBuildingsY, (int)sourceX, 0, (int)WIDTH, (int)HEIGHT);
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
        sampleBackground.dispose();
    }
}
