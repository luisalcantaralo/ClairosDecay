package mx.itesm.decay;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.itesm.decay.Screens.Dev.Test;
import mx.itesm.decay.Screens.GameOver;
import mx.itesm.decay.Screens.LoadingScreen;
import mx.itesm.decay.Screens.Screens;
import mx.itesm.decay.Screens.Splash;
import mx.itesm.decay.Screens.TestLevel1Screen;
import mx.itesm.decay.Screens.TestScreen;
import mx.itesm.decay.Screens.Menu.Home;



public class Decay extends Game{
    public static final short GROUP_PLAYER = -1;
    public static final short GROUP_STAIRS = -2;

    public static  Preferences prefs;
    private final AssetManager assetManager;

    public Decay(){
        assetManager = new AssetManager();


    }

    @Override
    public void create(){
        setScreen(new Splash(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

}


