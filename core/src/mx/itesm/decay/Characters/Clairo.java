package mx.itesm.decay.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

public class Clairo extends GenericCharacter {
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> runningAnimation;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> jumpingAnimation;
    TextureRegion currentFrame;

    public enum State {walking, idle, running}
    private State state;

    private float timer;

    public Clairo(Texture texture, float x, float y, World world) {
        super(texture, x, y, world);
        walkAnimation = createAnimation(5,1, 0.25f);
        runningAnimation = createAnimation(5,2, 0.25f);
        jumpingAnimation = createAnimation(5,3, 0.25f);
        idleAnimation = createAnimation(5,3, 0.25f);
        state = State.idle;
        timer = 0;
        Gdx.input.setInputProcessor(new GameInputProcessor());

    }

    public void updateCharacter(){
        timer += Gdx.graphics.getDeltaTime();

        switch (state){
            case idle:
                currentFrame = idleAnimation.getKeyFrame(timer, true);
                break;
            case running:
                currentFrame = runningAnimation.getKeyFrame(timer, true);
                break;
            case walking:
                currentFrame = walkAnimation.getKeyFrame(timer, true);
                break;
            default:
                break;
        }
    }

    private class GameInputProcessor implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            if(keycode == Input.Keys.RIGHT){
                switch (state){
                    case idle:
                        timer = 0;
                        state = State.running;
                        break;
                }
            }
            else if(keycode == Input.Keys.LEFT){
                switch (state){
                    case running:
                        timer = 0;
                        state = State.idle;
                        break;
                }
            }
            else if (keycode == Input.Keys.SPACE){

            }
            else if (keycode==Input.Keys.BACK){
            }
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {

            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }

}
