package mx.itesm.decay.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {

    private float speed;
    private float x;
    private float y;
    private boolean visible;
    private boolean izquierda;

    private Texture bulletTexture;
    private Sprite bulletSprite;

    public Bullet(float x, float y, boolean izquierda){
        this.x = x;
        this.y = y;

        bulletTexture = new Texture("Turret/bullet.png");
        bulletSprite = new Sprite(bulletTexture);
        bulletSprite.setPosition(x, y);
        this.izquierda = izquierda;
        visible = true;
        if (izquierda){
            speed = -1;
        }
        else {
            speed = 1;
        }
    }

    public void render(SpriteBatch batch){
        bulletSprite.draw(batch);
    }

    public void update(){
        x += speed;
        bulletSprite.setX(x + speed);
        if (x > 300 || x < -300){
            visible = false;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Sprite getBulletSprite(){
        return bulletSprite;
    }

}
