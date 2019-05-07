package mx.itesm.decay.Display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Text {
    private BitmapFont font;
    GlyphLayout glyph = new GlyphLayout();

    public Text() {
        font = new BitmapFont(Gdx.files.internal("fonts/dialogue.fnt"));

    }

    public void showText(SpriteBatch batch, String message, float x, float y) {
        glyph.setText(font, message);
        float textWidth = glyph.width;
        font.draw(batch, glyph, x-textWidth/2, y);
    }

    public void showTimer(SpriteBatch batch, String message, float x, float y) {
        font.getData().setScale(0.3f,0.3f);
        glyph.setText(font, message);
        float textWidth = glyph.width;
        font.draw(batch, glyph, x, y);
    }
}