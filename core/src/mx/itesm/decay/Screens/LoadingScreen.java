package mx.itesm.decay.Screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Display.Text;
import mx.itesm.decay.Generators.GenericButton;
import mx.itesm.decay.Generators.GenericScreen;

public class LoadingScreen extends GenericScreen{

    // Loading Animation
    private static final float TIME_BETWEEN_FRAMES = 0.05f;
    private Sprite loadingSprite;
    private float timerAnimation = TIME_BETWEEN_FRAMES;

    // AssetManager
    private AssetManager manager;

    private Decay game;
    private Screens nextScreen;

    private int progress;
    private Text text;

    private Texture loadingTexture;


    public LoadingScreen(Decay game, Screens nextScreen){
        this.game = game;
        this.nextScreen=nextScreen;
    }
    
    private void loadNextScreenResources(){
        manager = game.getAssetManager();
        progress = 0;
        
        switch(nextScreen){
            case HOME: 
                loadHomeResources();
                break;
            case ABOUT:
                loadAboutResources();
                break;
            case SETTINGS:
                loadSettingsResources();
                break;
            case LEVEL_ONE:
                loadLevel1Resources();
                break;
            case LEVEL_TWO:
                loadLevel2Resources();
                break;
            case LEVEL_THREE:
                loadLevel3Resources();
                break;
        }
    }

    private void loadLevel3Resources() {
    }

    private void loadLevel2Resources() {
        
    }

    private void loadLevel1Resources() {
        
    }

    private void loadSettingsResources() {
        
    }

    private void loadAboutResources() {
        
    }

    private void loadHomeResources() {
        
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {

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
