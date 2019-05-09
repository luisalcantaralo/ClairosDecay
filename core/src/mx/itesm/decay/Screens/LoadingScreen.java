package mx.itesm.decay.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import mx.itesm.decay.Decay;
import mx.itesm.decay.Display.Text;
import mx.itesm.decay.Generators.GenericButton;
import mx.itesm.decay.Generators.GenericScreen;
import mx.itesm.decay.Screens.Menu.About;
import mx.itesm.decay.Screens.Menu.Home;
import mx.itesm.decay.Screens.Menu.Settings;

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

        Decay.prefs = Gdx.app.getPreferences("my-preferences");

        if(Decay.prefs.getString("music") == null) Decay.prefs.putString("music", "ON");

        Decay.prefs.putBoolean("sound", true);
        Decay.prefs.putString("level", "1");

        Decay.prefs.flush();

    }
    
    private void loadNextScreenResources(){
        manager = game.getAssetManager();
        progress = 0;
        
        switch(nextScreen){
            case HOME: 
                loadHomeResources();
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
        manager.load("menu/cd-menu-buildings.png", Texture.class);
        manager.load("menu/cd-menu-background.png",Texture.class);
        manager.load("cd-logo.png", Texture.class);
        manager.load("backgrounds/cd-simple-background.png", Texture.class);
        manager.load("menu/cd-menu-buildings.png", Texture.class);
        manager.load("UI/simple-screen.png", Texture.class);
        manager.load("menu/cd-about-instructions.png", Texture.class);
        manager.load("menu/cd-about-title.png", Texture.class);
        manager.load("menu/cd-settings-monitor.png", Texture.class);
        manager.load("menu/cd-settings-title.png", Texture.class);
        manager.load("menu/cd-button-start.png", Texture.class);
        manager.load("menu/cd-button-help.png", Texture.class);
        manager.load("menu/cd-button-settings.png", Texture.class);
        manager.load("menu/cd-button-back.png", Texture.class);
        manager.load("menu/cd-about-us.png", Texture.class);

        manager.load("Music/MainMenu.mp3",Music.class);


        manager.load("Items/LifeBarContainer.png",Texture.class);

        manager.load("Items/TimeBar.png",Texture.class);

    }

    private void updateAssetsLoad(){
        if (manager.update()){
            switch(nextScreen){
                case HOME:
                    game.setScreen(new Home(game));
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

        progress = ((int)(manager.getProgress()*100));
    }


    @Override
    public void show() {
        text = new Text();
        loadNextScreenResources();
        loadingTexture = new Texture("backgrounds/cd-simple-background.png");

    }

    @Override
    public void render(float delta) {
        deleteScreen();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(loadingTexture, 0, 0);
        text.showText(batch, progress + "%", WIDTH/2, HEIGHT/5);
        batch.end();


        //Update load
        updateAssetsLoad();

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
