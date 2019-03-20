package mx.itesm.decay.Characters;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;


public class Clairo extends Sprite {

    // Clairo's State
    public enum State { IDLE, WALKING, RUNNING}
    public State currentState;
    public State previousState;

    // Box2d
    public World world;
    public Body body;



    public Clairo(float x, float y, World world) {


    }





}
