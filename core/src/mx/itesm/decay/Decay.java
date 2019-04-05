package mx.itesm.decay;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.itesm.decay.Screens.Dev.Test;
import mx.itesm.decay.Screens.TestScreen;

public class Decay extends Game{
    public static float PPM;
    public void create(){
        setScreen(new TestScreen());
    }
}

