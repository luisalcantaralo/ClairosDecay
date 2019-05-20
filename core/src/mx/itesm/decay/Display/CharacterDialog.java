package mx.itesm.decay.Display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.rafaskoberg.gdx.typinglabel.TypingAdapter;
import com.rafaskoberg.gdx.typinglabel.TypingConfig;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.visible;

public class CharacterDialog {
    Skin skin;
    public Stage stage;
    TypingLabel label;
    TypingLabel labelEvent;
    TextButton  skip;
    public CharacterDialog(String text, Body body){
        adjustTypingConfigs();

        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        skin.getAtlas().getTextures().iterator().next().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        float scale = 1;
        skin.getFont("default-font").getData().setScale(scale);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        stage.addActor(table);
        table.setFillParent(true);

        label = createTypingLabel(text);

        skip = new TextButton("", skin);
        skip.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                label.skipToTheEnd();
            }
        });


        table.bottom().pad(40f);
        table.add(label).colspan(8).growX().minHeight(200f).maxHeight(200f);
        table.pack();
    }
    public void adjustTypingConfigs() {
        // Only allow two chars per frame
        TypingConfig.CHAR_LIMIT_PER_FRAME = 1;

        // Change color used by CLEARCOLOR token
        TypingConfig.DEFAULT_CLEAR_COLOR = Color.WHITE;

        // Force bitmap fonts to use color markup
        TypingConfig.FORCE_COLOR_MARKUP_BY_DEFAULT = true;
    }
    public void update(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    }

    public TypingLabel createTypingLabel(String textP) {
        // Create text with tokens
        final StringBuilder text = new StringBuilder();
        text.append(textP);

        // Create label
        final TypingLabel label = new TypingLabel(text, skin);

        // Make the label wrap to new lines, respecting the table's layout.
        label.setWrap(true);

        // Set an event listener for when the {EVENT} token is reached and for the char progression ends.
        label.setTypingListener(new TypingAdapter() {
            @Override
            public void event(String event) {
                System.out.println("Event: " + event);

                labelEvent.restart("{FASTER}{COLOR=GRAY}Event:{WAIT=0.1}{COLOR=LIME} " + event);
                labelEvent.clearActions();
                labelEvent.addAction(
                        sequence(
                                visible(true),
                                alpha(0),
                                alpha(1, 0.25f, Interpolation.pow2In),
                                delay(0.5f),
                                alpha(0, 2f, Interpolation.pow2)
                        )
                );
            }

            @Override
            public void end() {
                System.out.println("End");
            }
        });

        // Finally parse tokens in the label text.
        label.parseTokens();

        return label;
    }

}
