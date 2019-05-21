package mx.itesm.decay.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.itesm.decay.Display.Text;

public class Bullet {

    private float speed;
    private float x;
    private float y;
    private float damage;

    private boolean visible;
    public boolean izquierda;

    private Texture bulletTexture;
    private Sprite bulletSprite;

    public Bullet(float x, float y, boolean izquierda, int speed, float damage, Texture bulletTexture){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;

        this.bulletTexture = bulletTexture;
        bulletSprite = new Sprite(bulletTexture);
        bulletSprite.setPosition(x, y);
        this.izquierda = izquierda;
        this.visible = true;
        if (izquierda){
            this.speed *= -1;
        }
    }

    public void render(SpriteBatch batch){
        bulletSprite.draw(batch);
    }

    public void update(){
        x += speed;
        bulletSprite.setX(x + speed);
        if (x > x+200 || x < x-200){
            visible = false;
        }
    }

    public float getX() {
        return x;
    }

    public float getDamage() {
        return damage;
    }

    public float getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
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

    public Sprite getBulletSprite() {
        return bulletSprite;
    }
}
