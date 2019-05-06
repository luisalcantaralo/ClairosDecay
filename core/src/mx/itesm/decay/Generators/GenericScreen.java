package mx.itesm.decay.Generators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.decay.Decay;

public abstract class GenericScreen implements Screen
{
    // Atributos disponibles en todas las clases del proyecto
    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;

    // Atributos disponibles solo en las subclases
    // Todas las pantallas tienen una cámara y una view
    protected OrthographicCamera camera;
    protected Viewport view;
    // Todas las pantallas dibujan algo :)
    protected SpriteBatch batch;

    public GenericScreen() {
        // Crea la cámara con las dimensiones del mundo
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        // En el centro de la pantalla
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();
        // La view que escala los elementos gráficos
        view = new StretchViewport(WIDTH, HEIGHT, camera);
        // El objeto que administra los trazos gráficos
        batch = new SpriteBatch();
    }
    public GenericScreen(float scale) {
        // Crea la cámara con las dimensiones del mundo
        camera = new OrthographicCamera(WIDTH/scale, HEIGHT/scale);
        // En el centro de la pantalla
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();
        // La view que escala los elementos gráficos
        view = new StretchViewport(WIDTH/scale, HEIGHT/scale, camera);
        // El objeto que administra los trazos gráficos
        batch = new SpriteBatch();
    }

    // Borra la pantalla con fondo negro
    protected void deleteScreen() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    // Borra la pantalla con el color RGB (r,g,b)
    protected void deleteScreen(float r, float g, float b) {
        Gdx.gl.glClearColor(r,g,b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
    }

    @Override
    public void hide() {
        // Libera los recursos asignados por cada pantalla
        // Las subclases están obligadas a sobrescribir el método dispose()
        dispose();
    }

}