package mx.itesm.decay;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.itesm.decay.Screens.Dev.Test;
import mx.itesm.decay.Screens.LoadingScreen;
import mx.itesm.decay.Screens.Screens;
import mx.itesm.decay.Screens.TestLevel1Screen;
import mx.itesm.decay.Screens.TestScreen;
import mx.itesm.decay.Screens.Menu.Home;



public class Decay extends Game{
    public static float PPM = 100;

    private final AssetManager assetManager;

    public Decay(){
        assetManager = new AssetManager();
    }

    @Override
    public void create(){
        setScreen(new LoadingScreen(this, Screens.HOME));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

}


