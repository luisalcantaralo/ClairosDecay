package mx.itesm.decay.Display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Text {
    private BitmapFont font;
    GlyphLayout glyph = new GlyphLayout();

    public Text() {
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
    }

    public void showText(SpriteBatch batch, String message, float x, float y) {
        glyph.setText(font, message);
        float textWidth = glyph.width;
        font.draw(batch, glyph, x-textWidth/2, y);
    }
}